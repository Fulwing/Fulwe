package com.fulwin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Customer;
import com.fulwin.pojo.Lineorder;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface LineorderService extends IService<Lineorder> {
    public List<Lineorder> getAllLineOrder();

    public List<Lineorder> getAllLineOrderBySellerId(Long id);

    public List<Lineorder> getAllLineOrderByBuyerId(Long id);

    public Lineorder getLineOrderById(String id);

    public void insertLineOrder(Lineorder lineorder);

    public void updateLineOrder(Lineorder lineorder);

    public Lineorder findNewestItemByBuyerId(@Param("userId") Long userId);

}
