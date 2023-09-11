package com.fulwin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fulwin.mapper.CustomerMapper;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    public Customer getCustomerById(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public List<Customer> getCustomerByEmail(String email) {
        HashMap<String, Object> map = new HashMap<>();
        //自定义要查询的
        map.put("email",email);

        return customerMapper.selectByMap(map);
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
    public void deleteCustomerById(Long id) {
        customerMapper.deleteById(id);
    }
}