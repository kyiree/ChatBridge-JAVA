package com.cn.chat.bridge.business.service.impl;

import com.cn.chat.bridge.business.repository.DialogueRepository;
import com.cn.chat.bridge.business.repository.entity.Dialogue;
import com.cn.chat.bridge.business.request.AddDialogueRequest;
import com.cn.chat.bridge.business.service.DialogueService;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.constant.ObjectEnum;
import com.cn.chat.bridge.common.dto.IdDto;
import com.cn.chat.bridge.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogueServiceImpl implements DialogueService {

    private final DialogueRepository repository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IdDto addDialogue(AddDialogueRequest request) {
        BusinessException.assertTrue(!repository.existByUuid(request.getUuid()), CodeEnum.COMMON_EXIST, ObjectEnum.DIALOGUE.getMsg());

        Dialogue add = Dialogue.create4Add(request);

        repository.save(add);
        return IdDto.create(add.getId(), add.getUuid());
    }
}
