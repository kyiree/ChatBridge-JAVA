package com.cn.chat.bridge.business.service;

import com.cn.chat.bridge.business.request.AddProductRequest;
import com.cn.chat.bridge.business.request.OrderPageRequest;
import com.cn.chat.bridge.business.request.ProductPageRequest;
import com.cn.chat.bridge.business.vo.AlipayPayCodeVo;
import com.cn.chat.bridge.business.vo.OrderListVo;
import com.cn.chat.bridge.business.vo.OrderStatusVo;
import com.cn.chat.bridge.business.vo.ProductListVo;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import jakarta.servlet.http.HttpServletRequest;

public interface PayService {

    /**
     * 获取产品列表。
     *
     * @return the product list
     */
    PageVo<ProductListVo> productPage(ProductPageRequest request);


    /**
     * 获取当前用户订单页。
     *
     * @return the current user order page
     */
    PageVo<OrderListVo> getCurrentUserOrderPage(OrderPageRequest request);


    /**
     * 获取枫叶订单数据
     *
     * @return the current user order page
     */
    PageVo<OrderListVo> getUserOrderPage(OrderPageRequest request);

    /**
     * 根据ID删除产品
     *
     * @param id the id
     */
    void deleteProductById(Long id);


    /**
     * 上架产品
     *
     */
    IdVo addProduct(AddProductRequest request);

    /**
     * 分页获取产品数据
     */
    PageVo<ProductListVo> productPages(ProductPageRequest request);


    /**
     * 生成支付宝支付二维码
     *
     * @param productId the product id
     * @return the alipay pay code vo
     */
    AlipayPayCodeVo generatePayQrCode(final Long productId);


    /**
     * 支付宝回调
     *
     * @param request the request
     * @return the string
     */
    String alipayPullback(final HttpServletRequest request);


    /**
     * Payment status string.
     *
     * @return the string
     */
    OrderStatusVo paymentStatus(String orderNum);
}
