package com.cn.chat.bridge.admin.controller;

import com.cn.chat.bridge.admin.request.*;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.auth.request.UserPageRequest;
import com.cn.chat.bridge.auth.vo.UserListVo;
import com.cn.chat.bridge.auth.vo.UserTotalVo;
import com.cn.chat.bridge.business.request.AddProductRequest;
import com.cn.chat.bridge.business.request.OrderPageRequest;
import com.cn.chat.bridge.business.request.ProductPageRequest;
import com.cn.chat.bridge.business.service.PayService;
import com.cn.chat.bridge.business.service.UserService;
import com.cn.chat.bridge.business.vo.ControlStructureVo;
import com.cn.chat.bridge.business.vo.OrderListVo;
import com.cn.chat.bridge.business.vo.ProductListVo;
import com.cn.chat.bridge.business.vo.ServerConfigVo;
import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 后台管理系统
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SystemService systemService;

    private final UserService userService;

    private final PayService payService;


    /**
     * 生成兑换码
     *
     * @return the bot configuration
     */
    @PostMapping("/exchange/build")
    public ResponseVo<BaseVo> buildExchangeCode(@RequestBody @Valid GenerateExchangeRequest request) {
        systemService.buildRedemptionCode(request);
        return ResponseVo.success();
    }


    /**
     * 更新服务器配置参数
     *
     * @return the bot configuration
     */
    @PostMapping("/server/put/config")
    public ResponseVo<BaseVo> updateServerConfig(@RequestBody @Valid ServerConfigRequest request) {
        systemService.updateServerConfig(request);
        return ResponseVo.success();
    }

    /**
     * 获取服务器配置
     *
     * @return the bot configuration
     */
    @GetMapping("/server/get/config")
    public ResponseVo<ServerConfigVo> getServerConfig() {
        return ResponseVo.success(systemService.getServerConfig());
    }


    /**
     * 更新终端
     *
     * @return the bot configuration
     */
    @PostMapping("/server/put/terminal")
    public ResponseVo<BaseVo> updateTerminalConfig(@RequestBody @Valid TerminalConfigRequest request) {
        systemService.updateTerminal(request);
        return ResponseVo.success();
    }

    /**
     * 获取终端
     *
     * @return the bot configuration
     */
    @GetMapping("/server/get/terminal")
    public ResponseVo<ControlStructureVo> getTerminalConfig() {
        return ResponseVo.success(systemService.getTerminal());
    }


    /**
     * 分页获取用户信息
     *
     */
    @GetMapping("/user/get/page")
    public ResponseVo<PageVo<UserListVo>> getUserPages(@ModelAttribute @Valid UserPageRequest request) {

        return ResponseVo.success(userService.pageList(request));
    }

    /**
     * 获取平台总人数
     */
    @GetMapping("/user/get/count")
    public ResponseVo<UserTotalVo> getPlatformCount() {
        return ResponseVo.success(userService.getTotalUsers());
    }

    /**
     * 分页获取订单信息
     *
     * @return the bot configuration
     */
    @GetMapping(value = "/orders/page")
    public ResponseVo<PageVo<OrderListVo>> getOrdersPages(@ModelAttribute @Valid OrderPageRequest request) {
        return ResponseVo.success(
                payService.getUserOrderPage(request)
        );
    }

    /**
     * 新增产品
     *
     * @return the bot configuration
     */
    @PostMapping("/product/put/data")
    public ResponseVo<IdVo> productsShelf(@RequestBody @Validated AddProductRequest request) {
        return ResponseVo.success(payService.shelvesProduct(request));
    }


    @GetMapping(value = "/product/get/page", name = "分页获取产品", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseVo<PageVo<ProductListVo>> productPages(@ModelAttribute @Valid ProductPageRequest request) {
        return ResponseVo.success(
                payService.productPages(request)
        );
    }

    /**
     * 删除产品
     *
     * @return the bot configuration
     */
    @PostMapping("/product/delete/{id}")
    public ResponseVo<BaseVo> productsDelete(@PathVariable long id) {
        payService.deleteProductById(id);
        return ResponseVo.success();
    }


    /**
     * 修改用户信息
     *
     * @return the bot configuration
     */
    @PostMapping("/user/put/data")
    public ResponseVo<BaseVo> update(@RequestBody @Valid UpdateUserRequest request) {
        userService.update(request);
        return ResponseVo.success();
    }

    /**
     * 分页获取兑换码
     *
     * @return the result
     */
    @GetMapping("/exchange/get/page")
    public ResponseVo<BaseVo> exchangeCodePage(@ModelAttribute @Valid ExchangePageRequest request) {

        return ResponseVo.success(systemService.findExchangeCodePage(request));
    }

    /**
     * 根据ID删除兑换码
     *
     * @param id the id
     * @return the result
     */
    @PostMapping(value = "/exchange/delete/{id}")
    public ResponseVo<BaseVo> deleteExchangeById(@PathVariable final Long id) {
        systemService.deleteExChangeById(id);
        return ResponseVo.success();
    }

    /**
     * 更新公告
     *
     * @return the result
     */
    @PostMapping("/put/announcement")
    public ResponseVo<BaseVo> updateAnnouncement(@RequestBody @Valid UpdateAnnouncementRequest request) {
        systemService.updateAnnouncement(request);
        return ResponseVo.success();
    }
}
