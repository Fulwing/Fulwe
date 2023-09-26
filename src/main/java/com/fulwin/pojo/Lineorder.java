package com.fulwin.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fulwin.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lineorder {

    @TableId(type = IdType.NONE)
    private String orderId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long buyerId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sellerId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long CommodityId;
    @TableField(fill = FieldFill.INSERT)
    private Date utcCreate;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date utcUpdate;
    @Version
    private Integer version;
    private OrderStatus orderStatus;
    private BigDecimal sellerSalary;
    private BigDecimal totalPrice;
    private BigDecimal platformFee;

}
