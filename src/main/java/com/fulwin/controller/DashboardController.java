package com.fulwin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fulwin.Enums.OrderStatus;
import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.pojo.Customer;
import com.fulwin.pojo.Lineorder;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CusinfoService;
import com.fulwin.service.CustomerService;
import com.fulwin.service.LineorderService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private CusinfoService cusinfoService;

    @Autowired
    private LineorderService lineorderService;

    @GetMapping
    public String dashboard(Model model, @RequestParam(value = "pn", defaultValue = "1") Integer pn){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);
        int process = 0;
        int delivered = 0;

        List<Lineorder> allLineOrderBySellerId = lineorderService.getAllLineOrderBySellerId(customer.getId());
        for (Lineorder order :
                allLineOrderBySellerId) {
            if(order.getOrderStatus().equals(OrderStatus.PROCESSING))
                process++;
            if(order.getOrderStatus().equals(OrderStatus.DELIVERED))
                delivered++;
        }
        IPage<Lineorder> userPage=new Page<>(pn,5);
        QueryWrapper<Lineorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("seller_id", customer.getId());
        IPage<Lineorder> page= lineorderService.page(userPage,queryWrapper);
        List<String> names = new ArrayList<>();
        for (Lineorder lineorder : page.getRecords()) {
            Commodity commodityById = commodityService.getCommodityById(lineorder.getCommodityId());
            names.add(commodityById.getItemName());
        }


        model.addAttribute("balance", customer.getBalance());
        model.addAttribute("services", commodityService.getCusAllItemByUserId(customer.getId()).size());
        model.addAttribute("process", process);
        model.addAttribute("delivered", delivered);
        model.addAttribute("orders", allLineOrderBySellerId);
        model.addAttribute("itemNames", names);
        model.addAttribute("page",page);


        return "dashboard/user-dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        List<Commodity> commodities = commodityService.getCusAllItemByUserId(customer.getId());
        List<String> pictures = new ArrayList<>();
        for (Commodity c : commodities) {
            pictures.add(Base64.getEncoder().encodeToString(c.getItemPicture()));
        }

        model.addAttribute("info", cusinfoService.getCusinfoById(customer.getId()));
        model.addAttribute("name", customer.getUsername());
        model.addAttribute("balance", customer.getBalance());
        model.addAttribute("commodities", commodities);
        model.addAttribute("pictures", pictures);

        return "dashboard/user-profile";
    }

    @GetMapping("/setting")
    public String setting(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        Cusinfo cusinfo = cusinfoService.getCusinfoById(customer.getId());

        model.addAttribute("cus" , customer);
        model.addAttribute("info", cusinfo);

        return "dashboard/user-settings";
    }

    @GetMapping("/history")
    public String purchaseHistory(Model model, @RequestParam(value = "pn", defaultValue = "1") Integer pn){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);
        Cusinfo cusinfo = cusinfoService.getCusinfoById(customer.getId());


        //limit each page 5 data
        IPage<Lineorder> userPage=new Page<>(pn,5);
        QueryWrapper<Lineorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("buyer_id", customer.getId());
        IPage<Lineorder> page= lineorderService.page(userPage,queryWrapper);

        List<String> names = new ArrayList<>();
        for (Lineorder lineorder : page.getRecords()) {
            Commodity commodityById = commodityService.getCommodityById(lineorder.getCommodityId());
            names.add(commodityById.getItemName());
        }

        model.addAttribute("page", page);
        model.addAttribute("orders", page.getRecords());
        model.addAttribute("name", customer.getUsername());
        model.addAttribute("balance", customer.getBalance());
        model.addAttribute("info", cusinfo);
        model.addAttribute("itemNames" , names);

        return "dashboard/purchase-history";
    }
}
