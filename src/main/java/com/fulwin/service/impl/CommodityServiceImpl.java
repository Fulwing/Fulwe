package com.fulwin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fulwin.mapper.CommodityMapper;
import com.fulwin.pojo.Commodity;
import com.fulwin.service.CommodityService;
import com.fulwin.util.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;
    @Override
    public List<Commodity> getAllCommodity() {
        return commodityMapper.selectList(null);
    }

    @Override
    public Commodity getCommodityById(Long id) {
        return commodityMapper.selectById(id);
    }

    @Override
    public void insertCommodity(Commodity commodity) {

    }

    @Override
    public void updateCommodity(Commodity commodity) {
        commodityMapper.updateById(commodity);
    }

    @Override
    public void deleteCommodityById(Long id) {

    }

    @Override
    public List<Commodity> getCommoditiesPage(int offset, int limit) {
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        // You can add additional filters or sorting as needed
        // For example, you might want to order by itemPrice: wrapper.orderByAsc("itemPrice");

        // Limit the number of results using offset and limit
        wrapper.last("LIMIT " + offset + "," + limit);

        return commodityMapper.selectList(wrapper);
    }

    @Override
    public void addBouImageByUserId(Long id, List<byte[]> images) throws IOException {

		Commodity commodity = getCommodityById(id);

		commodity.setItemBpicture(Image.concatenateImagesWithDelimiter(images));

		updateCommodity(commodity);

    }

    @Override
    public List<Commodity> getCusAllItemByUserId(Long id) {
        QueryWrapper<Commodity> wrapper = new QueryWrapper<>();
        wrapper.eq("item_cusid", id); // Filter by user ID

        return this.list(wrapper);
    }
}
