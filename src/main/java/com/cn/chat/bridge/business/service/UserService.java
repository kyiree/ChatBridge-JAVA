package com.cn.chat.bridge.business.service;

import com.cn.chat.bridge.admin.request.UpdateUserRequest;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.user.request.UserPageRequest;
import com.cn.chat.bridge.user.vo.UserInfoVo;
import com.cn.chat.bridge.user.vo.UserListVo;
import com.cn.chat.bridge.user.vo.UserTotalVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 雨纷纷旧故里草木深
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
public interface UserService {


    /**
     * 修改用户头像
     *
     * @param file the file
     */
    void editUserAvatar(MultipartFile file);


    /**
     * 编辑用户名。
     *
     * @param username the username
     */
    void editUserName(String username);


    /**
     * 获取当前登陆用户信息
     *
     * @return the current user info
     */
    UserInfoVo getCurrentUserInfo();


    /**
     * 绑定邮箱
     */
    void wechatBindEmail(String email, String password);


    /**
     * 获取用户数据分页 （可搜索）
     */
    PageVo<UserListVo> pageList(UserPageRequest request);

    /**
     * 修改用户信息
     */
    void update(UpdateUserRequest dto);

    /**
     * 获取平台总人数
     */
    UserTotalVo getTotalUsers();

    /**
     * 减少使用次数
     *
     * @param requiredFrequency
     * @param userId
     */
    void minusFrequency(Long requiredFrequency, Long userId);

    /**
     * 增加使用次数
     *
     * @param requiredFrequency
     * @param userId
     */
    void plusFrequency(Long requiredFrequency, Long userId);
}
