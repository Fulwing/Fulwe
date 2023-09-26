package com.fulwin.controller;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CusinfoService;
import com.fulwin.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String dashboard(){
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
    public String purchaseHistory(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);

        Cusinfo cusinfo = cusinfoService.getCusinfoById(customer.getId());

        model.addAttribute("name", customer.getUsername());
        model.addAttribute("balance", customer.getBalance());
        model.addAttribute("info", cusinfo);

        return "dashboard/purchase-history";
    }
}
