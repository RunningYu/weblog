package com.weblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/10
 */
@ApiModel(description = "照片（来源于博文）")
@Data
@TableName("tb_weblog_picture")
public class Picture {

    private Integer id;
    private String pictureId;
    private String picturePath;
    private int userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
