package com.fulwin;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@SpringBootTest
class FulweApplicationTests {

	@Autowired
	CommodityService commodityService;

	@Test
	void contextLoads() throws IOException {
		//commodityService.getAllCommodity().forEach(System.out::println);

		File imageFile = new File("C:/Users/danie/Downloads/tbody.jpg");
		byte[] imageData = Files.readAllBytes(imageFile.toPath());

		System.out.println(Arrays.toString(imageData));
		Commodity commodity = commodityService.getCommodityById(2L);
		commodity.setItemPicture(imageData);

		commodityService.updateCommodity(commodity);

		System.out.println(commodityService.getCommodityById(2L));
	}

}
