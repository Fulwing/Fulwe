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
        chainDefinition.addPathDefinition("/shop/deleteitem/**","user");
        chainDefinition.addPathDefinition("/dashboard/**","user");
        chainDefinition.addPathDefinition("/dashboard","user");

        chainDefinition.addPathDefinition("/dashboard/setting","authc");
        chainDefinition.addPathDefinition("/user/updateacc","authc");
        chainDefinition.addPathDefinition("/dashboard/deleteitem","authc");





        return chainDefinition;
    }
}

