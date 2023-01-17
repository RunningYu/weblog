package com.weblog.VO.requestVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/16
 */
@ApiModel(description = "个人主题")
@Data
public class VoUserTheme {
    private String userId;
    private List<String> backgroundImageList;
    private String color;
    /**
     * 是否打开 灰度页面 1-打开 0-关闭
     */
    private Integer isGrey;
    /**
     * 菜单布局 left、right
     */
    private String menuPlace;
}
