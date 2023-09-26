package com.fulwin.controller;

import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;
@Controller
public class LandingController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String landingPage(){

        Subject subject = SecurityUtils.getSubject();
        Long userId = (Long) subject.getPrincipal();
        Session session = subject.getSession();

        if(userId != null){
            Customer customerById = customerService.getCustomerById(userId);
            session.setAttribute("email", customerById.getEmail());
            String pictureBase64 = Base64.getEncoder().encodeToString(customerById.getProfilePicture());
            session.setAttribute("profilepic", pictureBase64);
        }
        return "index/index";
    }
}
