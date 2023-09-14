package com.fulwin;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Customer;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CustomerService;
import com.fulwin.util.Image;
import org.junit.jupiter.api.Test;
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

@SpringBootTest
class FulweApplicationTests {

	@Autowired
	CommodityService commodityService;

	@Test
	void contextLoads() throws IOException {
	}

	@Test
	public void addImage() throws IOException {
				File imageFile = new File("C:/Users/danie/Downloads/test2.jpg");
		File imageFile2 = new File("C:/Users/danie/Downloads/test3.jpg");
		byte[] imageData = Files.readAllBytes(imageFile.toPath());
		byte[] imageData2 = Files.readAllBytes(imageFile2.toPath());
		List<byte[]> listImg = new ArrayList<>();
		listImg.add(imageData);
		listImg.add(imageData2);

		commodityService.addBouImageByUserId(1L, listImg);
	}

}
