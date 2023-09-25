package com.fulwin;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CusinfoService;
import com.fulwin.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest
class FulweApplicationTests {

	@Autowired
	CommodityService commodityService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CusinfoService cusinfoService;

	@Autowired
	private ServletContext servletContext;

	@Test
	void contextLoads() throws IOException {
		String baseUrl =
				ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		System.out.println(baseUrl);
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
