package com.weblog.VO.responseVo;

import com.weblog.entity.Column;
import lombok.Data;

/**
 * @author : 其然乐衣Letitbe
 * @date : 2022/12/13
 */
@Data
public class VoColumn {

    private String id;
    private String columnId;
    private String column_name;
    private String discription;
    private String userId;
    private String cover;
    private Integer blogAmount;

    public VoColumn(Column column, Integer blogAmount) {
        this.id = column.getId();
        this.columnId = column.getColumnId();
        this.column_name = column.getColumnName();
        this.discription = column.getDiscription();
        this.userId = column.getUserId();
        this.cover = column.getCover();
        this.blogAmount = blogAmount;
    }

}
