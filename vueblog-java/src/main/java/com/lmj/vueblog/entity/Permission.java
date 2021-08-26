package com.lmj.vueblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("m_permission")
public class Permission {

    private static final long serialVersionUID = 1L;

    /** 数据库中设置该字段自增时该注解不能少 **/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String url;
}
