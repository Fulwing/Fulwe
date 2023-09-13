package com.fulwin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {


    @TableId(type = IdType.ASSIGN_ID)
    private Long itemId;
    private String itemName;
    private BigDecimal itemPrice;
    private byte[] itemPicture;
    private Long itemCusid;
    private String itemIntro;
    private String itemGroup;
    @TableField(fill = FieldFill.INSERT)
    private Date utcCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date utcUpdate;
    @Version
    private Integer version;
    @TableLogic
    private Integer deleted;

}
