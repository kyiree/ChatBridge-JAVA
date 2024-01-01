package com.cn.chat.bridge.business.controller;


import com.cn.chat.bridge.auth.request.StarPageRequest;
import com.cn.chat.bridge.business.request.StarDialogueRequest;
import com.cn.chat.bridge.business.service.StarService;
import com.cn.chat.bridge.business.vo.UserStarDetailVo;
import com.cn.chat.bridge.business.vo.UserStarListVo;
import com.cn.chat.bridge.common.vo.BaseVo;
import com.cn.chat.bridge.common.vo.IdVo;
import com.cn.chat.bridge.common.vo.PageVo;
import com.cn.chat.bridge.common.vo.ResponseVo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/star")
@RequiredArgsConstructor
public class StarController {

    private final StarService starService;

    /**
     * 分页获取收藏
     *
     * @return the result
     */
    @GetMapping("/star/page")
    public ResponseVo<PageVo<UserStarListVo>> getStarPage(@ModelAttribute @Valid StarPageRequest request) {
        return ResponseVo.success(starService.getUserStarPage(request));
    }


    /**
     * 删除指定收藏
     *
     * @param id the id
     * @return the result
     */
    @PostMapping("/star/delete/{id}")
    public ResponseVo<BaseVo> deleteStarById(@PathVariable Long id) {
        starService.deleteById(id);
        return ResponseVo.success();
    }


    /**
     * 查看指定收藏
     *
     * @param starId the star id
     * @return the result
     */
    @GetMapping(value = "/stat/get/data")
    public ResponseVo<UserStarDetailVo> getStarDetailById(@RequestParam Long starId) {
        return ResponseVo.success(starService.getUserStarDetail(starId));
    }


    /**
     * 添加收藏
     *
     */
    @PostMapping
    public ResponseVo<IdVo> putStarDialogue(@RequestBody @Valid StarDialogueRequest request) {
        return ResponseVo.success(starService.starDialogue(request));
    }
}
