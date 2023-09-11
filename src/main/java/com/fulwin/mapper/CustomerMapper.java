package com.fulwin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fulwin.pojo.Customer;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMapper extends BaseMapper<Customer> {
}
