package com.fulwin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fulwin.mapper.LineorderMapper;
import com.fulwin.pojo.Lineorder;
import com.fulwin.service.LineorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineorderServiceImpl extends ServiceImpl<LineorderMapper, Lineorder> implements LineorderService {

    @Autowired
    private LineorderMapper lineorderMapper;

    @Override
    public List<Lineorder> getAllLineOrder() {
        return lineorderMapper.selectList(null);
    }

    @Override
    public List<Lineorder> getAllLineOrderBySellerId(Long id) {
        QueryWrapper<Lineorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seller_id", id); //

        return lineorderMapper.selectList(queryWrapper);
    }

    @Override
    public List<Lineorder> getAllLineOrderByBuyerId(Long id) {
        QueryWrapper<Lineorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", id); //

        return lineorderMapper.selectList(queryWrapper);
    }

    @Override
    public Lineorder getLineOrderById(String id) {
        return lineorderMapper.selectById(id);
    }

    @Override
    public void insertLineOrder(Lineorder lineorder) {
        lineorderMapper.insert(lineorder);
    }

    @Override
    public void updateLineOrder(Lineorder lineorder) {
        lineorderMapper.updateById(lineorder);
    }

    @Override
    public Lineorder findNewestItemByBuyerId(Long userId) {
        QueryWrapper<Lineorder> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("buyer_id", userId)
                .orderByDesc("utc_create")
                .last("LIMIT 1");

        return getBaseMapper().selectOne(queryWrapper);
    }


}
