package com.cn.chat.bridge.common.vo;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class PageVo<T> implements BaseVo {

    private Long total;

    private List<T> rows;

    public static <T> PageVo<T> empty() {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setTotal(0L);
        pageVo.setRows(Collections.emptyList());
        return pageVo;
    }

    public static <T> PageVo<T> create(Long total, List<T> rows) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setTotal(total);
        pageVo.setRows(rows);
        return pageVo;
    }
}
