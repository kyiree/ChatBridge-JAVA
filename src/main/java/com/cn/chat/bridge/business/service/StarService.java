package com.cn.chat.bridge.business.service;

import com.cn.chat.bridge.business.request.StarDialogueRequest;
import com.cn.chat.bridge.business.vo.UserStarDetailVo;
import com.cn.chat.bridge.business.vo.UserStarListVo;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.user.request.StarPageRequest;

import java.util.List;

/**
 * 雨纷纷旧故里草木深
 *
 * @author 时间海 @github dulaiduwang003
 * @version 1.0
 */
public interface StarService {


    /**
     * 新增收藏
     *
     * @return the long
     */
    IdVo starDialogue(StarDialogueRequest request);


    /**
     * 分页获取收藏
     *
     * @return the user star vo page
     */
    PageVo<UserStarListVo> getUserStarPage(StarPageRequest request);


    /**
     * 获取指定收藏对话
     *
     * @param id the id
     * @return the user star detail
     */
    UserStarDetailVo getUserStarDetail(Long id);


    /**
     * 删除收藏
     *
     * @param id the id
     */
    void deleteById(Long id);
}
