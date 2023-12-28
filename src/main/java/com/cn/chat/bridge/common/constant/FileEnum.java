package com.cn.chat.bridge.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文件夹名
 *
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum FileEnum {

    //头像
    AVATAR("avatar"),
    //绘图
    PAINTING("painting"),
    //视频
    AUDIO("audio");
    String dec;

}
