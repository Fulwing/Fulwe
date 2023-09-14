package com.fulwin.controller;

import com.fulwin.pojo.Commodity;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

import static com.fulwin.util.Image.splitImagesAndToBase64;

@Controller
@RequestMapping("/shop")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/item/{id}")
    public String singleItem(@PathVariable Long id, Model model){

        Commodity commodity = commodityService.getCommodityById(id);
        byte[] delimiter = new byte[] { (byte) 0x1A, (byte) 0x2B, (byte) 0x3C, (byte) 0x4D, (byte) 0x5E, (byte) 0x6F };

        String pictureBase64 = Base64.getEncoder().encodeToString(commodity.getItemPicture());
        model.addAttribute("picture", pictureBase64);
        model.addAttribute("username", customerService.getCustomerById(commodity.getItemCusid()).getUsername());
        model.addAttribute("price", commodity.getItemPrice());
        model.addAttribute("intro", commodity.getItemIntro());
        model.addAttribute("name", commodity.getItemName());

        if(commodity.getItemBpicture() != null) {
            byte[] itemBpicture = commodity.getItemBpicture();
            model.addAttribute("bpictures", splitImagesAndToBase64(itemBpicture));
        }else {
            model.addAttribute("bpictures", null);
        }

        return "shop/single-product";
    }


}

