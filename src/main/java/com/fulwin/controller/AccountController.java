package com.fulwin.controller;

import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

        session.setAttribute("username", username);

        return "index/loggedin";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session){

        // get current user
        Subject subject = SecurityUtils.getSubject();

        //get login token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);



        //try to login
        try{
            subject.login(token);
            return "index/loggedin";
        }
        catch (UnknownAccountException e){ // no matching username
            model.addAttribute("msg", "username or password error");
            return "index/index";
        }
        catch (IncorrectCredentialsException e){
            model.addAttribute("msg", "username or password error");
            return "index/index";
        }

//        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
//            session.setAttribute("loginUser", username);
//            return "redirect:/main";
//        }else {
//            model.addAttribute("msg", "用户名或者密码错误");
//            return "index";
//        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index/index";
    }
}
