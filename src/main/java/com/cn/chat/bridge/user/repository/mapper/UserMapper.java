package com.cn.chat.bridge.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.user.repository.entity.User;
import com.cn.chat.bridge.user.request.UserPageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage<User> findPage(IPage<User> page, @Param("request")UserPageRequest request);
}
