package com.fulwin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cusinfo {

    @TableId(type = IdType.NONE)
    private Long userId;
    private String snapchat;
    private String instagram;
    private String discord;
    private int phone;
    @Version
    private Integer version;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date utcUpdate;
    @TableField(fill = FieldFill.INSERT)
    private Date utcCreate;
    @TableLogic
    private Integer deleted;
}
