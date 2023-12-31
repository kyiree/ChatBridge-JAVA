package com.cn.chat.bridge.user.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cn.chat.bridge.admin.request.UpdateUserRequest;
import com.cn.chat.bridge.common.constant.DefaultUserEnum;
import com.cn.chat.bridge.common.utils.CloneUtils;
import com.cn.chat.bridge.common.utils.CryptUtils;
import com.cn.chat.bridge.user.constant.UserTypeEnum;
import com.cn.chat.bridge.user.vo.UserInfoVo;
import com.cn.chat.bridge.user.vo.UserListVo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "tb_user", autoResultMap = true)
public class User {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户昵称
     */
    private String userName;

    private String email;

    private UserTypeEnum type;

    private String password;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 剩余次数
     */
    private Long frequency;

    /**
     * 是否签到
     */
    private Integer isSignIn;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Long createdUserId;

    private Long updateUserId;

    public static User create4Add(String email, String password) {
        LocalDateTime now = LocalDateTime.now();

        User add = new User();
        add.setEmail(CryptUtils.encryptSm4(email));
        add.setType(UserTypeEnum.NORMAL);
        add.setPassword(CryptUtils.encryptSm4(password));
        add.setFrequency(5L);
        add.setCreatedTime(now);
        add.setUpdateTime(now);
        add.setCreatedUserId(DefaultUserEnum.EMPTY.getId());
        add.setUpdateUserId(DefaultUserEnum.EMPTY.getId());
        return add;
    }

    public static User create4Add(String openId) {
        LocalDateTime now = LocalDateTime.now();

        User add = new User();
        add.setType(UserTypeEnum.NORMAL);
        add.setFrequency(5L);
        add.setCreatedTime(now);
        add.setUpdateTime(now);
        return add;
    }

    public UserInfoVo convert2UserInfoVo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserName(userName);
        userInfoVo.setType(type);
        userInfoVo.setAvatar(avatar);
        userInfoVo.setFrequency(frequency);
        return userInfoVo;
    }

    public UserListVo convert2UserListVo() {
        UserListVo userListVo = new UserListVo();
        userListVo.setUserId(id);
        userListVo.setUserName(userName);
        userListVo.setCreatedTime(createdTime);
        userListVo.setFrequency(frequency);
        userListVo.setEmail(email);
        return userListVo;
    }

    public static User create4Update(User old, UpdateUserRequest request) {
        User update = CloneUtils.deepClone(old);

        update.setFrequency(request.getFrequency());
        return update;
    }
}
