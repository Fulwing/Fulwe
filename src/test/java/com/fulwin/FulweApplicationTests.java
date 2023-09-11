package com.fulwin;

import com.fulwin.pojo.Customer;
import com.fulwin.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FulweApplicationTests {

	@Autowired
	CustomerService customerService;

	@Test
	void contextLoads() {
		customerService.getAllCustomer().forEach(System.out::println);
	}

}
