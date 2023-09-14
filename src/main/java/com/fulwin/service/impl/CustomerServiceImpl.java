package com.fulwin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fulwin.mapper.CustomerMapper;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import com.fulwin.util.ListAndString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void addCommodityToCartByUserId(Long userId, Long newItemId) {
        // Fetch the customer by userId
        Customer customer = customerMapper.selectById(userId);

        // Get the current cart as a string
        String currentCart = customer.getCart();

        // Concatenate the new item ID to the existing cart string, separated by commas
        if (currentCart == null || currentCart.isEmpty()) {
            // If the cart is empty, set the new item ID as the cart
            currentCart = newItemId.toString();
        } else {
            // Otherwise, append the new item ID
            currentCart += "," + newItemId;
        }

        // Update the customer's cart in the entity
        customer.setCart(currentCart);

        // Update the customer in the database
        updateCustomer(customer);
    }

    @Override
    public void deleteCommodityFromCartByUserId(Long userId, Long newItemId) {
        // Fetch the customer by userId
        Customer customer = customerMapper.selectById(userId);

        // Get the cart as a comma-separated string
        String cartString = customer.getCart();

        // Convert the cart string to a list of Long values
        List<Long> cartList = ListAndString.convertCartStringToLongList(cartString);

        // Remove the newItemId from the cartList
        cartList.remove(newItemId);

        // Convert the updated cart list back to a comma-separated string
        String updatedCartString = ListAndString.convertCartStringToLongList(cartList);
        // Update the customer's cart
        customer.setCart(Objects.requireNonNullElse(updatedCartString, ""));
        updateCustomer(customer);
    }

    @Override
    public List<Long> getCartByCusId(Long userId) {
        // Fetch the customer by userId
        Customer customer = customerMapper.selectById(userId);

        // Get the cart as a comma-separated string
        String cartString = customer.getCart();

        // Convert the cart string to a list of Long values
        return ListAndString.convertCartStringToLongList(cartString);
    }


}