package com.fulwin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fulwin.pojo.Customer;

import java.util.List;

public interface CustomerService extends IService<Customer>{

    public List<Customer> getAllCustomer();

    public Customer getCustomerById(Long id);

    public List<Customer> getCustomerByEmail(String email);

    public void insertCustomer(Customer customer);

    public void updateCustomer(Customer customer);

    public void deleteCustomerById(Long id);

}
