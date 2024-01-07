package com.cn.chat.bridge.gpt.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.gpt.repository.entity.Session;
import com.cn.chat.bridge.gpt.request.SessionPageRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionMapper extends BaseMapper<Session> {

    IPage<Session> findPage(IPage<Session> page, @Param("request") SessionPageRequest request, @Param("userId") Long userId);
}
