package com.cn.chat.bridge.business.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.admin.request.ExchangePageRequest;
import com.cn.chat.bridge.business.repository.entity.Exchange;
import com.cn.chat.bridge.business.repository.mapper.ExchangeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ExchangeRepository extends ServiceImpl<ExchangeMapper, Exchange> {

    public IPage<Exchange> findPage(ExchangePageRequest request) {
        return baseMapper.findPage(new Page<>(request.getCurrent(), request.getSize()), request);
    }

    public List<Exchange> getByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return Collections.emptyList();
        }

        return baseMapper.selectList(Wrappers.lambdaQuery(Exchange.class)
                .eq(Exchange::getCode, code)
        );
    }
}
