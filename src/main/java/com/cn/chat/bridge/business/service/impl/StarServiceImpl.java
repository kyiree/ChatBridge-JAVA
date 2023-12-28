package com.cn.chat.bridge.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cn.chat.bridge.business.repository.StarRepository;
import com.cn.chat.bridge.business.repository.entity.Star;
import com.cn.chat.bridge.business.request.StarDialogueRequest;
import com.cn.chat.bridge.business.service.StarService;
import com.cn.chat.bridge.business.vo.UserStarDetailVo;
import com.cn.chat.bridge.business.vo.UserStarListVo;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.utils.AuthUtils;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.user.request.StarPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StarServiceImpl implements StarService {

    private final StarRepository repository;

    @Override
    public IdVo starDialogue(StarDialogueRequest request) {
        Star addStar = Star.create4Add(request, AuthUtils.getCurrentLoginId());
        repository.save(addStar);
        return IdVo.create(addStar.getId());
    }

    @Override
    public PageVo<UserStarListVo> getUserStarPage(StarPageRequest request) {
        Long currentLoginId = AuthUtils.getCurrentLoginId();
        IPage<Star> starIPage = repository.findPage(request, currentLoginId);
        List<Star> stars = starIPage.getRecords();
        if (CollectionUtils.isEmpty(stars)) {
            return PageVo.empty();
        }
        List<UserStarListVo> starListVos = stars.stream().map(Star::convert2ListVo).toList();
        return PageVo.create(starIPage.getTotal(), starListVos);
    }

    @Override
    public UserStarDetailVo getUserStarDetail(Long id) {

        Star byUserIdAndId = repository.getByUserIdAndId(AuthUtils.getCurrentLoginId(), id);
        BusinessException.assertNotNull(byUserIdAndId);

        return byUserIdAndId.convert2DetailVo();

    }

    @Override
    public void deleteById(Long id) {
        repository.deleteByIdAndUserId(id, AuthUtils.getCurrentLoginId());
    }
}
