package com.fulwin.controller;

import com.fulwin.pojo.Cusinfo;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CusinfoService;
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
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CusinfoService cusinfoService;

    @PostMapping("/register")
    public String Register(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, HttpSession session){
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setEmail(email);
        customer.setPassword(password);

        customerService.insertCustomer(customer);

        session.setAttribute("email", email);

        Cusinfo cusinfo = new Cusinfo();
        cusinfo.setUserId(customerService.getCustomerByEmail(email).get(0).getId());

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

    @PostMapping("/updateacc")
    public String updateAcc(@RequestParam("customFile") MultipartFile profileImage,
                            @RequestParam("username") String username,
                            @RequestParam("email") String email,
                            @RequestParam("new-password") String newPassword,
                            @RequestParam(name = "instagram", required = false) String instagram,
                            @RequestParam(name = "discord", required = false) String discord,
                            @RequestParam(name = "snapchat", required = false) String snapchat,
                            Model model,
                            HttpSession s) {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        List<Customer> customers = customerService.getCustomerByEmail((String) session.getAttribute("email"));
        Customer customer = customers.get(0);
        Cusinfo cusinfo = cusinfoService.getCusinfoById(customer.getId());

        if(!instagram.isEmpty())
            cusinfo.setInstagram(instagram);
        if(!discord.isEmpty())
            cusinfo.setDiscord(discord);
        if(!snapchat.isEmpty())
            cusinfo.setSnapchat(snapchat);

        byte[] fileBytes;
        if (!profileImage.isEmpty()) {
            try {
                fileBytes = profileImage.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            customer.setProfilePicture(fileBytes);
        }

        if (!newPassword.isEmpty()) {
            customer.setPassword(newPassword);
            customer.setUsername(username);
            customer.setEmail(email);
            customerService.updateCustomer(customer);
            cusinfoService.updateCusInfo(cusinfo);
            subject.logout();
            return "index/index";
        }

        customer.setUsername(username);
        customer.setEmail(email);

        customerService.updateCustomer(customer);
        cusinfoService.updateCusInfo(cusinfo);

        return "redirect:/dashboard/setting?success=Success! Your update is complete.";
    }
}
