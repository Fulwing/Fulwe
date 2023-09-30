package com.fulwin.controller;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Customer;
import com.fulwin.pojo.Lineorder;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CustomerService;
import com.fulwin.service.LineorderService;
import com.fulwin.util.Image;
import com.google.gson.JsonSyntaxException;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.stripe.exception.*;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private LineorderService lineorderService;

    @GetMapping("/success")
    public String checkoutSuccess(Model model) throws IOException { // go to success page
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));

        if(!customers.isEmpty()){
            Customer customer = customers.get(0);
            Lineorder lineOrder = lineorderService.findNewestItemByBuyerId(customer.getId());
            Long commodityId = lineOrder.getCommodityId();
            Commodity commodityById = commodityService.getCommodityById(commodityId);
            byte[] picture = commodityById.getItemPicture();
            String newPicture = Base64.getEncoder().encodeToString(Image.resizeImage(picture, 520,520));

            model.addAttribute("name", commodityById.getItemName());
            model.addAttribute("id", commodityById.getItemId());
            model.addAttribute("picture", newPicture);
            //model.addAttribute("sellerEmail") add seller email here
        }else {
            return "error/500";
        }

        return "shop/payment-success";
    }

}
