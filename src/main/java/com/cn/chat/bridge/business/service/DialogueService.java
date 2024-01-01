package com.cn.chat.bridge.business.service;

import com.cn.chat.bridge.business.request.AddDialogueRequest;
import com.cn.chat.bridge.common.dto.IdDto;


public interface DialogueService {

    IdDto addDialogue(AddDialogueRequest request);
}
