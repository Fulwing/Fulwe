package com.fulwin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fulwin.mapper.CustomerMapper;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<Customer> getAllCustomer() {
        return customerMapper.selectList(null);
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerMapper.selectById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerMapper.insert(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerMapper.updateById(customer);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerMapper.deleteById(id);
    }
}