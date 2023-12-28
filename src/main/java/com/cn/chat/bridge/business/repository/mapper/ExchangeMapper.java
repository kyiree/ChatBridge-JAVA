package com.cn.chat.bridge.business.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.chat.bridge.admin.request.ExchangePageRequest;
import com.cn.chat.bridge.business.repository.entity.Exchange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExchangeMapper extends BaseMapper<Exchange> {

    IPage<Exchange> findPage(IPage<Exchange> page, @Param("request") ExchangePageRequest request);

}
