package com.cn.chat.bridge.business.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.chat.bridge.admin.request.UpdateUserRequest;
import com.cn.chat.bridge.admin.service.impl.AliUploadServiceImpl;
import com.cn.chat.bridge.auth.repository.UserRepository;
import com.cn.chat.bridge.auth.repository.entity.User;
import com.cn.chat.bridge.auth.request.UserPageRequest;
import com.cn.chat.bridge.auth.vo.UserInfoVo;
import com.cn.chat.bridge.auth.vo.UserListVo;
import com.cn.chat.bridge.auth.vo.UserTotalVo;
import com.cn.chat.bridge.business.repository.OrderRepository;
import com.cn.chat.bridge.business.service.UserService;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.constant.FileEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.utils.CryptUtils;
import com.cn.chat.bridge.common.vo.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AliUploadServiceImpl aliUploadServiceImpl;

    private final OrderRepository orderRepository;
    private final UserRepository repository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserAvatar(MultipartFile file) {
        String uri = aliUploadServiceImpl.uploadFile(file, FileEnum.AVATAR.getDec(), null, true);
        repository.updateAvatarById(uri, AuthUtils.getCurrentLoginId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserName(String username) {
        repository.updateUserNameById(username, AuthUtils.getCurrentLoginId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wechatBindEmail(String email, String password) {

        // 获取当前登录的用户ID
        Long currentLoginId = AuthUtils.getCurrentLoginId();
        User currentUser = repository.getById(currentLoginId);
        if (StringUtils.isNotBlank(currentUser.getEmail())) {
            throw BusinessException.create(CodeEnum.EMAIL_BIND_ERR);
        }

        User user = repository.getByEmail(CryptUtils.encryptSm4(email));
        if (Objects.isNull(user) || !Objects.equals(user.getPassword(), password)) {
            throw BusinessException.create(CodeEnum.EMAIL_LOGIN_PWD_ERR);
        }

        // 打赏记录
        orderRepository.updateUserIdByUserId(user.getId(), currentLoginId);

        // 删除缓存数据信息
        StpUtil.logout(user.getId());

        // 删除原有邮箱账号
        repository.removeById(user.getId());

        // 重新分配小程序账号数据
        repository.updateEmailAndPwdAndTypeAndFrequencyById(currentLoginId, email, CryptUtils.encryptSm4(password), user.getType(),
                user.getFrequency() + currentUser.getFrequency());
    }


    @Override
    public UserInfoVo getCurrentUserInfo() {

        User user = repository.getById(AuthUtils.getCurrentLoginId());
        BusinessException.assertNotNull(user);

        return user.convert2UserInfoVo();

    }

    @Override
    public UserTotalVo getTotalUsers() {
        return UserTotalVo.create4Total(repository.count());
    }

    @Override
    public PageVo<UserListVo> pageList(UserPageRequest request) {
        IPage<User> userPage = repository.findPage(request);
        List<User> users = userPage.getRecords();
        if (CollectionUtils.isEmpty(users)) {
            return PageVo.empty();
        }
        List<UserListVo> userListVos = users
                .stream()
                .map(User::convert2UserListVo)
                .toList();
        return PageVo.create(userPage.getTotal(), userListVos);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UpdateUserRequest request) {
        User old = repository.getById(request.getUserId());
        BusinessException.assertNotNull(old);

        User update = User.create4Update(old, request);
        repository.updateById(update);
    }

    @Override
    public void minusFrequency(Long requiredFrequency, Long userId) {
        User user = repository.getById(userId);
        if (Objects.isNull(user) || user.getFrequency() < requiredFrequency) {
            throw BusinessException.create(CodeEnum.FREQUENCY_EMPTY);
        }

        repository.updateFrequencyMinusById(userId, requiredFrequency);
    }

    @Override
    public void plusFrequency(Long requiredFrequency, Long userId) {
        User user = repository.getById(userId);
        BusinessException.assertNotNull(user);
        repository.updateFrequencyPlusById(userId, requiredFrequency);
    }

}
