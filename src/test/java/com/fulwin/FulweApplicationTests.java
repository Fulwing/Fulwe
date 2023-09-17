package com.fulwin;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CusinfoService;
import com.fulwin.service.CustomerService;
import com.fulwin.util.Image;
import com.fulwin.util.ListAndString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fulwin.util.Image.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FulweApplicationTests {

	@Autowired
	CommodityService commodityService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CusinfoService cusinfoService;

	@Test
	void contextLoads() throws IOException {
		Long userId = 1701074383603658753L;

		customerService.addCommodityToCartByUserId(userId, 1L);
		customerService.addCommodityToCartByUserId(userId, 2L);

//		List<Long> cartByCusId = customerService.getCartByCusId(userId);
//
//		Long first = cartByCusId.get(0);
//		Long second = cartByCusId.get(1);
//
//		assertEquals(first, 1L);
//		assertEquals(second, 2L);

	}

	@Test
	public void addImage() throws IOException {
		File imageFile = new File("C:/Users/danie/Downloads/bottle.jpg");
		File imageFile2 = new File("C:/Users/danie/Downloads/profilefulwin.jpg");
		byte[] imageData = Files.readAllBytes(imageFile.toPath());
		byte[] imageData2 = Files.readAllBytes(imageFile2.toPath());

		Long userId = 1701435301533503489L;

		Commodity commodity = commodityService.getCommodityById(1L);
		commodity.setItemCusid(userId);
		commodityService.updateCommodity(commodity);
	}

	@Test
	public void deleteLastItem(){

		Cusinfo cusinfo = new Cusinfo();
		cusinfo.setUserId(1701074383603658753L);
		cusinfo.setDiscord("Fulwin");
		cusinfo.setInstagram("https://instagram.com/fulwin6?igshid=OGQ5ZDc2ODk2ZA==");

		cusinfoService.insertCusInfo(cusinfo);
	}

}
