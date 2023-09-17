package com.fulwin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Version
    private Integer version;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date utcUpdate;
    @TableField(fill = FieldFill.INSERT)
    private Date utcCreate;
    @TableLogic
    private Integer deleted;
    private String cart;
    private byte[] profilePicture;
    private BigDecimal balance;

}
