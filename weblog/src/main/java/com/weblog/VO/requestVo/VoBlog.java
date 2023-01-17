package com.weblog.VO.requestVo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
@ApiModel(description = "博文的接收体")
@Data
public class VoBlog {
    /**
     * 参数描述：
     *  title         博文主题
     *  publishImage  博文封面
     *  discription   博文描述
     *  content       博文内容
     *  tag           博文标签（字符串，标签之间 空格隔开）
     *  ableLook      是否公开（1-公开，0-私密）
     *  status        博文状态（0-审核不通过，1-已发布，2-待审核，3-草稿）
     *  columnIdList        博文归属专栏列表
     *
     */
    private String blogId;
    private String userId;
    private String title;
    private String publishImage;
    private String discription;
    private String content;
    private String tag;
    private Integer ableLook;
    private Integer status;
    private List<String> columnIdList;

}
