package com.cn.chat.bridge.business.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.auth.request.StarPageRequest;
import com.cn.chat.bridge.business.repository.entity.Star;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StarMapper extends BaseMapper<Star> {

    IPage<Star> findPage(IPage<Star> page, @Param("request") StarPageRequest request, @Param("userId") Long userId);

}
