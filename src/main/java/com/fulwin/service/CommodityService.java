package com.fulwin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fulwin.pojo.Commodity;

import java.util.List;

public interface CommodityService extends IService<Commodity> {

    public List<Commodity> getAllCommodity();

    public Commodity getCommodityById(Long id);

    public void insertCommodity(Commodity Commodity);

    public void updateCommodity(Commodity Commodity);

    public void deleteCommodityById(Long id);

    List<Commodity> getCommoditiesPage(int offset, int limit);
}
