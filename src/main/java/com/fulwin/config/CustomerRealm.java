package com.fulwin.config;

import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    CustomerService customerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
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

        return new SimpleAuthenticationInfo(customer.getId(), customer.getPassword(), getName());
    }

}
