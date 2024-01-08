package com.cn.chat.bridge.business.controller;

import com.cn.chat.bridge.business.request.OrderPageRequest;
import com.cn.chat.bridge.business.request.ProductPageRequest;
import com.cn.chat.bridge.business.service.PayService;
import com.cn.chat.bridge.business.vo.AlipayPayCodeVo;
import com.cn.chat.bridge.business.vo.OrderListVo;
import com.cn.chat.bridge.business.vo.OrderStatusVo;
import com.cn.chat.bridge.business.vo.ProductListVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 交易性接口
 *
 */
@Slf4j
@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
public class PayController {


    private final PayService payService;


    /**
     * 生成支付宝支付二维码
     *
     * @param productId the product id
     * @return the result
     */
    @PostMapping("/alipay/pay/{productId}")
    public ResponseVo<AlipayPayCodeVo> alipayPayQrCode(@PathVariable Long productId) {
        return ResponseVo.success(payService.generatePayQrCode(productId));
    }

    /**
     * 获取商品列表
     *
     * @return the product list
     */
    @GetMapping(value = "/product/page")
    public ResponseVo<PageVo<ProductListVo>> productPage(@ModelAttribute @Valid ProductPageRequest request) {
        return ResponseVo.success(payService.productPage(request));
    }

    /**
     * 获取我的打赏订单记录
     *
     * @return the product list
     */
    @GetMapping(value = "/orders/page")
    public ResponseVo<PageVo<OrderListVo>> ordersPage(@ModelAttribute @Valid OrderPageRequest request) {
        return ResponseVo.success(payService.getCurrentUserOrderPage(request));
    }


    /**
     * 支付宝支付状态查询
     *
     * @return the result
     */
    @PostMapping("/alipay/status/{orderNum}")
    public ResponseVo<OrderStatusVo> alipayIsSucceed(@PathVariable String orderNum) {
        return ResponseVo.success(payService.paymentStatus(orderNum));
    }


}
