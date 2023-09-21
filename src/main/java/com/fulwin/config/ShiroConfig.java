package com.fulwin.config;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

    @Bean("authorizer")
    public CustomerRealm userRealm() {
        return new CustomerRealm();
    }


    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
//        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
//        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        chainDefinition.addPathDefinition("/shop/cart","user");
        chainDefinition.addPathDefinition("/shop/deletecartitem/**","user");

        chainDefinition.addPathDefinition("/user/updateacc","authc");
        chainDefinition.addPathDefinition("/dashboard/**","authc");
        chainDefinition.addPathDefinition("/shop/addcommodity","authc");
        chainDefinition.addPathDefinition("/shop/deleteitem/**","authc");
        chainDefinition.addPathDefinition("/shop/editcommodity/**","authc");
        chainDefinition.addPathDefinition("/shop/addcommodity","user");


        return chainDefinition;
    }
}

