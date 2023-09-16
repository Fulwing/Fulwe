package com.fulwin.controller;

import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/user")
public class AccountController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/register")
    public String Register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, HttpSession session){
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(password);

        customerService.insertCustomer(customer);

        session.setAttribute("email", email);

        return "index/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam(defaultValue = "false", name = "remember") boolean rememberMe, Model model){
        // get current user
        Subject subject = SecurityUtils.getSubject();
        //get login token
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        token.setRememberMe(rememberMe);

        Session session = subject.getSession();

        //try to login
        try {
            subject.login(token);
            session.setAttribute( "email", email);
            return "redirect:/";
        }
        catch ( AuthenticationException ae ) {
            model.addAttribute("msg", "username or password error");
            return "index/login";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "index/index";
    }
}
