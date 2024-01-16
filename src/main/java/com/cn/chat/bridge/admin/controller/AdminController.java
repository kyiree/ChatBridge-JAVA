package com.cn.chat.bridge.admin.controller;

import com.cn.chat.bridge.admin.request.ServerConfigRequest;
import com.cn.chat.bridge.admin.request.TerminalConfigRequest;
import com.cn.chat.bridge.admin.request.UpdateAnnouncementRequest;
import com.cn.chat.bridge.admin.request.UpdateUserRequest;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.auth.request.UserPageRequest;
import com.cn.chat.bridge.auth.vo.UserListVo;
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
     * 更新服务器配置参数
     *
     * @return the bot configuration
     */
    @PutMapping("/server/config")
    public ResponseVo<BaseVo> updateServerConfig(@RequestBody @Valid ServerConfigRequest request) {
        systemService.updateServerConfig(request);
        return ResponseVo.success();
    }

    /**
     * 获取服务器配置
     *
     * @return the bot configuration
     */
    @GetMapping("/server/config")
    public ResponseVo<ServerConfigVo> getServerConfig() {
        return ResponseVo.success(systemService.getServerConfig());
    }

    /**
     * 更新终端配置
     */
    @PutMapping("/server/terminal")
    public ResponseVo<BaseVo> updateTerminalConfig(@RequestBody @Valid TerminalConfigRequest request) {
        systemService.updateTerminal(request);
        return ResponseVo.success();
    }

    /**
     * 更新终端配置
     */
    @GetMapping("/server/terminal")
    public ResponseVo<ControlStructureVo> getTerminalConfig() {
        return ResponseVo.success(systemService.getTerminal());
    }

    /**
     * 分页获取用户信息
     */
    @GetMapping("/user/page")
    public ResponseVo<PageVo<UserListVo>> getUserPages(@ModelAttribute @Valid UserPageRequest request) {

        return ResponseVo.success(userService.pageList(request));
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
     */
    @PostMapping("/product")
    public ResponseVo<IdVo> addProduct(@RequestBody @Valid AddProductRequest request) {
        return ResponseVo.success(payService.addProduct(request));
    }

    /**
     * 分页获取产品
     *
     * @return
     */
    @GetMapping(value = "/product/page")
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
    @DeleteMapping("/product/{id}")
    public ResponseVo<BaseVo> productsDelete(@PathVariable Long id) {
        payService.deleteProductById(id);
        return ResponseVo.success();
    }

    /**
     * 修改用户信息
     *
     * @return the bot configuration
     */
    @PutMapping("/user/{id}")
    public ResponseVo<BaseVo> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        userService.update(id, request);
        return ResponseVo.success();
    }

    /**
     * 更新公告
     *
     * @return the result
     */
    @PutMapping("announcement")
    public ResponseVo<BaseVo> updateAnnouncement(@RequestBody @Valid UpdateAnnouncementRequest request) {
        systemService.updateAnnouncement(request);
        return ResponseVo.success();
    }
}
