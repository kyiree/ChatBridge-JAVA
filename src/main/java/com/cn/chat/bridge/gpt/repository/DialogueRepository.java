package com.cn.chat.bridge.gpt.repository;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.gpt.repository.entity.Dialogue;
import com.cn.chat.bridge.gpt.repository.mapper.DialogueMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DialogueRepository extends ServiceImpl<DialogueMapper, Dialogue> {

    public List<Dialogue> getBySessionId(String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return Collections.emptyList();
        }

        return baseMapper.selectList(Wrappers.lambdaQuery(Dialogue.class)
                .eq(Dialogue::getSessionId, sessionId)
                .orderByAsc(Dialogue::getId)
        );
    }

}
