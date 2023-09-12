package com.fulwin.config;

import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    CustomerService customerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");

        UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
        List<Customer> customers = customerService.getCustomerByEmail(userToken.getUsername());

        if (customers.isEmpty()) {
            // User not found
            return null;
        }

        Customer customer = customers.get(0);

        // Check if the provided password matches the stored password
        if (!customer.getPassword().equals(new String(userToken.getPassword()))) {
            // Passwords don't match
            return null;
        }

        return new SimpleAuthenticationInfo(customer.getUsername(), customer.getPassword(), getName());
    }

}