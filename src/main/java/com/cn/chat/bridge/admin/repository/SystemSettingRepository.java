package com.cn.chat.bridge.admin.repository;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.chat.bridge.admin.constant.SystemPropertyTypeEnum;
import com.cn.chat.bridge.admin.repository.entity.SystemSetting;
import com.cn.chat.bridge.admin.repository.mapper.SystemSettingMapper;
import com.cn.chat.bridge.common.utils.JsonUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SystemSettingRepository extends ServiceImpl<SystemSettingMapper, SystemSetting> {

    @SuppressWarnings("unchecked")
    public <T> T getByType(SystemPropertyTypeEnum type) {

        List<SystemSetting> systemSettings = baseMapper.selectList(Wrappers.lambdaQuery(SystemSetting.class)
                .eq(SystemSetting::getType, type));

        if (CollectionUtils.isEmpty(systemSettings)) {
            return null;
        }

        Map<String, Object> fieldMap = new HashMap<>(systemSettings.size());
        systemSettings.forEach(s -> fieldMap.put(s.getName(), s.getValue()));
        return (T) JsonUtils.translate(fieldMap, type.cls);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateByType(SystemPropertyTypeEnum type, Object object, Long userId) {
        // 先删除再插入
        baseMapper.delete(Wrappers.lambdaQuery(SystemSetting.class).eq(SystemSetting::getType, type));

        Map<String, Object> fieldMap = JsonUtils.toMap(object);

        List<SystemSetting> systemSettings = fieldMap.entrySet()
                .stream()
                .map(e -> SystemSetting.create4Add(type, e.getKey(), JsonUtils.toJson(e.getValue()), userId))
                .toList();

        saveBatch(systemSettings);
    }
}
