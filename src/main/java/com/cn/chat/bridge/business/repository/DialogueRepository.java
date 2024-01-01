package com.cn.chat.bridge.business.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.business.repository.entity.Dialogue;
import com.cn.chat.bridge.business.repository.mapper.DialogueMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class DialogueRepository extends ServiceImpl<DialogueMapper, Dialogue> {

    public Boolean existByUuid(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return true;
        }
        return baseMapper.exists(Wrappers.lambdaQuery(Dialogue.class)
                .eq(Dialogue::getUuid, uuid)
        );
    }
}
