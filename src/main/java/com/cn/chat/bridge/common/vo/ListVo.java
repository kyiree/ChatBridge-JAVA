package com.cn.chat.bridge.common.vo;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class ListVo<T> implements BaseVo {

    private Long total;

    private List<T> rows;

    public static <T> ListVo<T> empty() {
        ListVo<T> pageVo = new ListVo<>();
        pageVo.setTotal(0L);
        pageVo.setRows(Collections.emptyList());
        return pageVo;
    }

    public static <T> ListVo<T> create(Long total, List<T> rows) {
        ListVo<T> pageVo = new ListVo<>();
        pageVo.setTotal(total);
        pageVo.setRows(rows);
        return pageVo;
    }
}
