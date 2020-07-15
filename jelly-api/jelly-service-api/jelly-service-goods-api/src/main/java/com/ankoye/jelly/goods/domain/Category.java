package com.ankoye.jelly.goods.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ankoye@qq.com
 */
@Data
@TableName("tb_category")
public class Category implements Serializable {
    @TableId
    private Integer id;

    private String name;

    private Boolean isShow;

    private Boolean isMenu;

    private Integer seq;

    private Integer parentId;

    private String template;
}