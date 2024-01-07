package com.cn.chat.bridge.gpt.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.gpt.repository.entity.Session;
import com.cn.chat.bridge.gpt.repository.mapper.SessionMapper;
import com.cn.chat.bridge.gpt.request.SessionPageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepository extends ServiceImpl<SessionMapper, Session> {

    public IPage<Session> findPage(SessionPageRequest request, Long userId) {
        return baseMapper.findPage(new Page<>(request.getCurrent(), request.getSize()), request, userId);
    }
}
