package com.cn.chat.bridge.user.controller;


import com.cn.chat.bridge.business.service.StarService;
import com.cn.chat.bridge.business.service.UserService;
import com.cn.chat.bridge.business.vo.PersonalityConfigStructureVo;
import com.cn.chat.bridge.business.vo.UserStarDetailVo;
import com.cn.chat.bridge.business.vo.UserStarListVo;
import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import com.cn.chat.bridge.user.request.UpdatePersonalityRequest;
import com.cn.chat.bridge.gpt.service.GptService;
import com.cn.chat.bridge.user.request.StarPageRequest;
import com.cn.chat.bridge.user.request.WeChatBindRequest;
import com.cn.chat.bridge.business.request.StarDialogueRequest;
import com.cn.chat.bridge.user.request.UpdateUserNameRequest;
import com.cn.chat.bridge.user.vo.UserInfoVo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 小程序 (用户操作性接口) 非公开
 *
 */
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


    /**
     * 分页获取收藏
     *
     * @return the result
     */
    @GetMapping("/star/page")
    public ResponseVo<PageVo<UserStarListVo>> getStarPage(@ModelAttribute @Valid StarPageRequest request) {
        return ResponseVo.success(starService.getUserStarPage(request));
    }


    /**
     * 删除指定收藏
     *
     * @param id the id
     * @return the result
     */
    @PostMapping("/star/delete/{id}")
    public ResponseVo<BaseVo> deleteStarById(@PathVariable Long id) {
        starService.deleteById(id);
        return ResponseVo.success();
    }


    /**
     * 查看指定收藏
     *
     * @param starId the star id
     * @return the result
     */
    @GetMapping(value = "/stat/get/data")
    public ResponseVo<UserStarDetailVo> getStarDetailById(@RequestParam Long starId) {
        return ResponseVo.success(starService.getUserStarDetail(starId));
    }


    /**
     * 添加收藏
     *
     * @return the result
     */
    @PostMapping("/stat/put/data")
    public ResponseVo<IdVo> putStarDialogue(@RequestBody @Valid StarDialogueRequest request) {
        return ResponseVo.success(starService.starDialogue(request));
    }
}
