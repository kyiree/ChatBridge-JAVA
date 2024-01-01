package com.cn.chat.bridge.auth.controller;


import com.cn.chat.bridge.auth.request.UpdatePersonalityRequest;
import com.cn.chat.bridge.auth.request.UpdateUserNameRequest;
import com.cn.chat.bridge.auth.request.WeChatBindRequest;
import com.cn.chat.bridge.auth.vo.UserInfoVo;
import com.cn.chat.bridge.business.service.StarService;
import com.cn.chat.bridge.business.service.UserService;
import com.cn.chat.bridge.business.vo.PersonalityConfigStructureVo;
import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import com.cn.chat.bridge.gpt.service.GptService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final StarService starService;

    private final GptService gptService;

    /**
     * 获取用户个性GPT配置
     *
     * @return the result
     */
    @GetMapping(value = "/personality/get/config")
    public ResponseVo<PersonalityConfigStructureVo> getPersonalityGptConfig() {
        return ResponseVo.success(gptService.getPersonalityConfig());
    }

    /**
     * 设置用户个性GPT配置
     *
     * @return the result
     */
    @PostMapping("/personality/put/config")
    public ResponseVo<BaseVo> updatePersonalityGptConfig(@RequestBody @Valid UpdatePersonalityRequest request) {
        gptService.updatePersonalityConfig(request);
        return ResponseVo.success();
    }


    /**
     * 微信绑定邮箱
     *
     * @return the result
     */
    @PostMapping("/wechat/bind")
    public ResponseVo<BaseVo> wechatBindEmail(@RequestBody @Valid WeChatBindRequest request) {
        userService.wechatBindEmail(request.getEmail(), request.getPassword());
        return ResponseVo.success();
    }


    /**
     * 当前用户信息结果。
     *
     * @return the result
     */
    @PostMapping(value = "/current/info")
    public ResponseVo<UserInfoVo> currentUserInfo() {

        return ResponseVo.success(userService.getCurrentUserInfo());

    }


    /**
     * 用户更新头像
     *
     * @param avatar the avatar
     * @return the result
     */
    @PostMapping(value = "/upload/avatar")
    public ResponseVo<BaseVo> userUploadAvatar(@NotNull MultipartFile avatar) {
        userService.editUserAvatar(avatar);
        return ResponseVo.success();
    }

    /**
     * 修改用户昵称
     *
     * @return the result
     */
    @PostMapping("/upload/username")
    public ResponseVo<BaseVo> userUpdateName(@RequestBody @Valid UpdateUserNameRequest request) {
        userService.editUserName(request.getUserName());
        return ResponseVo.success();
    }
}
