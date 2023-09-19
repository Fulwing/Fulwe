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
import java.util.ArrayList;
import java.util.Base64;
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
        List<byte[]> newImages = new ArrayList<>();
        try {
            byte[] resizedImage = Image.resizeImage(commodity.getItemPicture(), 1080, 1080);
            commodity.setItemPicture(resizedImage);
            if (commodity.getItemBpicture() != null && commodity.getItemBpicture().length > 0) {
                // The byte array is not empty; you can proceed with further processing
                List<String> images = Image.splitImagesAndToBase64(commodity.getItemBpicture());
                System.out.println(commodity.getItemBpicture().length);
                for (String image: images) {
                    byte[] byteArray = Base64.getDecoder().decode(image);
                    newImages.add((Image.resizeImage(byteArray, 1080, 1080)));
                }
                byte[] finalImages = Image.concatenateImagesWithDelimiter(newImages);
                commodity.setItemBpicture(finalImages);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        commodityMapper.insert(commodity);
    }

    @Override
    public void updateCommodity(Commodity commodity) {
        commodityMapper.updateById(commodity);
    }

    @Override
    public void deleteCommodityById(Long id) {
        commodityMapper.deleteById(id);
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
