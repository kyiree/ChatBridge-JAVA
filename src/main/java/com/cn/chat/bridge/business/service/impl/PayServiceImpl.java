package com.cn.chat.bridge.business.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.chat.bridge.admin.dto.AliPayConfigDto;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.admin.service.impl.IdGeneratorServiceImpl;
import com.cn.chat.bridge.business.constant.OrderStatusEnum;
import com.cn.chat.bridge.business.dto.AlipayCacheStructureDto;
import com.cn.chat.bridge.business.listener.UnpaidOrderQueue;
import com.cn.chat.bridge.business.repository.OrderRepository;
import com.cn.chat.bridge.business.repository.ProductRepository;
import com.cn.chat.bridge.business.repository.entity.Order;
import com.cn.chat.bridge.business.repository.entity.Product;
import com.cn.chat.bridge.business.request.AddProductRequest;
import com.cn.chat.bridge.business.request.OrderPageRequest;
import com.cn.chat.bridge.business.request.ProductPageRequest;
import com.cn.chat.bridge.business.service.PayService;
import com.cn.chat.bridge.business.service.UserService;
import com.cn.chat.bridge.business.vo.AlipayPayCodeVo;
import com.cn.chat.bridge.business.vo.OrderListVo;
import com.cn.chat.bridge.business.vo.OrderStatusVo;
import com.cn.chat.bridge.business.vo.ProductListVo;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.constant.OrderConstant;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.service.ICacheService;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.utils.QRCodeGenerator;
import com.cn.chat.bridge.common.utils.RedisLockHelper;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {

    private final ProductRepository productRepository;

    private final ICacheService cacheService;

    private final RedisLockHelper lockHelper;

    private final IdGeneratorServiceImpl idGeneratorServiceImpl;

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final SystemService systemService;

    private final UnpaidOrderQueue unpaidOrderQueue;


    /**
     * 得到产品列表
     *
     */
    @Override
    public PageVo<ProductListVo> productPage(ProductPageRequest request) {
        IPage<Product> productIPage = productRepository.findPage(request);
        List<Product> products = productIPage.getRecords();
        if (CollectionUtils.isEmpty(products)) {
            return PageVo.empty();
        }

        List<ProductListVo> productListVos = products.stream().map(Product::convert2ListVo).toList();
        return PageVo.create(productIPage.getTotal(), productListVos);
    }

    /**
     * 得到当前用户订单页面
     */
    @Override
    public PageVo<OrderListVo> getCurrentUserOrderPage(OrderPageRequest request) {
        final Long userId = AuthUtils.getCurrentLoginId();
        return getOrdersPage(userId, request);
    }

    /**
     * 得到用户订单页面
     */
    @Override
    public PageVo<OrderListVo> getUserOrderPage(OrderPageRequest request) {
        return getOrdersPage(null, request);
    }

    /**
     * 得到订单页面
     *
     * @param userId 用户id
     */
    private PageVo<OrderListVo> getOrdersPage(Long userId, OrderPageRequest request) {
        IPage<Order> orderIPage = orderRepository.findPage(userId, request);
        List<Order> orders = orderIPage.getRecords();
        if (CollectionUtils.isEmpty(orders)) {
            return PageVo.empty();
        }

        List<OrderListVo> orderListVos = orders.stream().map(Order::convert2ListVo).toList();
        return PageVo.create(orderIPage.getTotal(), orderListVos);
    }

    /**
     * 删除产品通过id
     *
     * @param id id
     */
    @Override
    public void deleteProductById(Long id) {
        productRepository.removeById(id);
    }


    /**
     * 货架上产品
     *
     */
    @Override
    public IdVo shelvesProduct(AddProductRequest request) {
        Product add = Product.create4Add(request);
        productRepository.save(add);
        return IdVo.create(add.getId());
    }

    @Override
    public PageVo<ProductListVo> productPages(ProductPageRequest request) {
        return productPage(request);
    }

    /**
     * 生成二维码支付
     *
     * @param productId 产品id
     * @return {@link AlipayPayCodeVo}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AlipayPayCodeVo generatePayQrCode(Long productId) {
        final String timestamp = String.valueOf(System.currentTimeMillis());
        // 当前登录用户ID
        final Long currentLoginId = AuthUtils.getCurrentLoginId();
        // 锁前缀
        final String lockPrefix = "ORDER_USER:" + currentLoginId;
        // 上锁
        final boolean lock = lockHelper.lock(lockPrefix, timestamp);

        try {
            if (!lock) {
                throw BusinessException.create(CodeEnum.PLACE_AN_ORDER_REPEATEDLY_ERR);
            }
            final String key = OrderConstant.ORDER_PAY + currentLoginId + productId;
            if (cacheService.existsKey(key)) {
                AlipayCacheStructureDto cache = cacheService.getFromCache4Object(key, AlipayCacheStructureDto.class);
                // 生成BASE64图片给前端
                return AlipayPayCodeVo.create(cache.getOrderNum(), cache.getProductName(), cache.getCreatedTime(),
                        cache.getProductPrice(), QRCodeGenerator.generateQRCode(cache.getUrl()));
            }

            // 商品是否存在
            Product product = productRepository.getById(productId);
            BusinessException.assertNotNull(product, CodeEnum.PRODUCT_NULL_ERR);

            // 生成单号，保存订单
            String orderNum = idGeneratorServiceImpl.getOrderNo();
            Order order = Order.create4Add(orderNum, product, currentLoginId);
            orderRepository.save(order);

            AliPayConfigDto aliPayConfigDto = systemService.getAliPayConfig();
            // 装载配置
            AlipayConfig alipayConfig = new AlipayConfig();
            alipayConfig.setServerUrl("https://openapi.alipay.com/gateway.do");
            alipayConfig.setFormat("json");
            alipayConfig.setCharset("UTF8");
            alipayConfig.setSignType("RSA2");
            alipayConfig.setAppId(aliPayConfigDto.getAppId());
            alipayConfig.setAlipayPublicKey(aliPayConfigDto.getPublicKey());
            alipayConfig.setPrivateKey(aliPayConfigDto.fetchPrivateKey());
            // 构建支付宝订单
            AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
            // 预构建请求
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(orderNum);
            // 支付金额
            model.setTotalAmount(String.valueOf(product.getPrice()));
            // 商品名称
            model.setSubject(product.getName());
            // 5分钟过期
            model.setTimeoutExpress("5m");
            request.setBizModel(model);
            // 支付宝回调地址
            request.setNotifyUrl(aliPayConfigDto.getDomain() + "/public/callback/order");
            AlipayTradePrecreateResponse response = alipayClient.execute(request);
            // 是否构建成功？ 构建成功则 创建二维码
            if (response.isSuccess()) {
                AlipayCacheStructureDto cache = AlipayCacheStructureDto.create(response.getQrCode(), order.getCreatedTime(), orderNum, product.getPrice(), product.getName());

                // 缓存订单数据
                cacheService.addToCache4Object(key, cache, 300L);
                // 添加至 待支付 队列中
                unpaidOrderQueue.addOrder(orderNum);
                // 生成BASE64图片给前端
                // 返回base64编码支付二维码图片
                return AlipayPayCodeVo.create(cache.getOrderNum(), cache.getProductName(), cache.getCreatedTime(), cache.getProductPrice(), QRCodeGenerator.generateQRCode(cache.getUrl()));
            } else {
                log.error("创建订单失败 订单号:{}, 错误信息：{}", orderNum, response.getBody());
                throw new AlipayApiException();
            }
        } catch (IOException | AlipayApiException | WriterException e) {
            throw BusinessException.create(CodeEnum.BUILD_FAILED_PAY_ERR);
        } finally {
            lockHelper.unlock(lockPrefix, timestamp);
        }

    }

    /**
     * 支付宝回调
     *
     * @param request 请求
     * @return {@link String}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String alipayPullback(final HttpServletRequest request) {
        AliPayConfigDto aliPayConfig = systemService.getAliPayConfig();
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            StringBuilder valueStr = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                valueStr.append((i == values.length - 1) ? values[i] : values[i] + ",");
            }
            params.put(name, valueStr.toString());
        }
        // 调用SDK验证签名
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getPublicKey(), "UTF8", "RSA2");
        } catch (AlipayApiException e) {

            throw new RuntimeException(e);
        }
        // 验证成功
        if (signVerified) {

            String tradeStatus = request.getParameter("trade_status");
            log.info("回调结果:{}", tradeStatus);
            // 支付成功
            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                final String outTradeNo = request.getParameter("out_trade_no");
                final Order order = orderRepository.getByOrderNum(outTradeNo);
                if (Objects.nonNull(order)) {
                    orderRepository.updateStateAndPayTimeByOrderNum(outTradeNo, 1, LocalDateTime.now());
                    userService.plusFrequency(order.getFrequency(), order.getUserId());

                    cacheService.removeFromCache4Object(OrderConstant.ORDER_PAY + order.getUserId().toString() + order.getProductId());
                }

                return "success";
            }
        } else {
            log.error("支付失败");
            return "fail";
        }
        return "fail";
    }


    /**
     * 付款状态
     *
     * @return {@link String}
     */
    @Override
    public OrderStatusVo paymentStatus(String orderNum) {
        Order orderByOrderNum = orderRepository.getByOrderNum(orderNum);

        if (Objects.nonNull(orderByOrderNum)) {
            if (Objects.equals(orderByOrderNum.getState(), 0)) {
                return OrderStatusVo.create(OrderStatusEnum.BE_PAID);
            } else if (Objects.equals(orderByOrderNum.getState(), 1)) {
                return OrderStatusVo.create(OrderStatusEnum.PAID);
            } else {
                return OrderStatusVo.create(OrderStatusEnum.IS_CLOSED);
            }
        } else {
            return OrderStatusVo.create(OrderStatusEnum.IS_CLOSED);
        }
    }
}
