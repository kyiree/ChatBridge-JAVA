package com.cn.chat.bridge.user.repository;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.common.utils.CryptUtils;
import com.cn.chat.bridge.user.constant.UserTypeEnum;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.user.repository.mapper.UserMapper;
import com.cn.chat.bridge.user.request.UserPageRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends ServiceImpl<UserMapper, User> {

    public IPage<User> findPage(UserPageRequest request) {
        return baseMapper.findPage(new Page<>(request.getCurrent(), request.getSize()), request);
    }

    public User getByEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return null;
        }
        return baseMapper.selectOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getEmail, email));
    }

    public void updatePwdByUserId(Long id, String password) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .set(User::getPassword, password)
                .eq(User::getId, id)
        );
    }

    public void updateAvatarById(String avatar, Long id) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .set(User::getAvatar, avatar)
                .eq(User::getId, id)
        );
    }

    public void updateUserNameById(String userName, Long id) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .set(User::getUserName, userName)
                .eq(User::getId, id)
        );
    }

    public void updateEmailAndPwdAndTypeAndFrequencyById(Long id, String email, String password,
                                                         UserTypeEnum type, Long frequency) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .set(User::getEmail, email)
                .set(User::getPassword, password)
                .set(User::getType, type)
                .set(User::getFrequency, frequency)
                .eq(User::getId, id)
        );
    }

    public void updateFrequencyMinusById(Long id, Long frequency) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .eq(User::getId, id)
                .setSql("frequency = frequency -" + frequency)
        );
    }

    public void updateFrequencyPlusById(Long id, Long frequency) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .eq(User::getId, id)
                .setSql("frequency = frequency +" + frequency)
        );
    }

    public void updateIsSignInAndPlusFrequencyById(Long id, Integer isSignIn, Long frequency) {
        baseMapper.update(null, Wrappers.lambdaUpdate(User.class)
                .eq(User::getId, id)
                .set(User::getIsSignIn, isSignIn)
                .setSql("frequency = frequency +" + frequency)
        );
    }

}
