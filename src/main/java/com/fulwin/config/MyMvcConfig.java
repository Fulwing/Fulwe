package com.fulwin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index/index");
        registry.addViewController("/dashboard").setViewName("dashboard/user-dashboard");
    }

//    @Bean
//    public LocaleResolver localeResolver(){
//        return new MyLocaleResolver();
//    }

}

