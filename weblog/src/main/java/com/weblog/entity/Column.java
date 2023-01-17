package com.weblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/13
 */
@ApiModel(description = "专栏实体类")
@Data
@TableName("tb_weblog_column")
public class Column {

    private String id;
    private String columnId;
    private String columnName;
    private String discription;
    private String userId;
    private String cover;

}
