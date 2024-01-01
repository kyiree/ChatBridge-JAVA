package com.cn.chat.bridge.business.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.business.repository.entity.Personality;
import com.cn.chat.bridge.business.repository.mapper.PersonalityMapper;
import org.springframework.stereotype.Repository;

@Repository
public class PersonalityRepository extends ServiceImpl<PersonalityMapper, Personality> {

    public Personality getByUserId(Long userId) {
        return baseMapper.selectOne(Wrappers.lambdaQuery(Personality.class)
                .eq(Personality::getUserId, userId));
    }
}
