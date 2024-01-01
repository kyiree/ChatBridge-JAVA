package com.cn.chat.bridge.business.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.auth.request.StarPageRequest;
import com.cn.chat.bridge.business.repository.entity.Star;
import com.cn.chat.bridge.business.repository.mapper.StarMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StarRepository extends ServiceImpl<StarMapper, Star> {

    public void updateUserIdByUserId(Long fromUserId, Long toUserId) {
        baseMapper.update(null, Wrappers.lambdaUpdate(Star.class)
                .set(Star::getUserId, toUserId)
                .eq(Star::getUserId, fromUserId)
        );
    }

    public IPage<Star> findPage(StarPageRequest request, Long userId) {
        return baseMapper.findPage(new Page<>(request.getCurrent(), request.getSize()), request, userId);
    }

    public void deleteByIdAndUserId(Long id, Long userId) {
        baseMapper.delete(Wrappers.lambdaQuery(Star.class)
                .eq(Star::getId, id)
                .eq(Star::getUserId, userId)
        );
    }

    public Star getByUserIdAndId(Long userId, Long id) {
        return baseMapper.selectOne(Wrappers.lambdaQuery(Star.class)
                .eq(Star::getId, id)
                .eq(Star::getUserId, userId)
        );
    }
}
