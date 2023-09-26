package com.fulwin;

import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Cusinfo;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CusinfoService;
import com.fulwin.service.CustomerService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItemCollection;
import com.stripe.model.Transfer;
import com.stripe.model.checkout.Session;
import com.stripe.param.TransferCreateParams;
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
	public void transfer() throws StripeException {
		Stripe.apiKey = "sk_test_51Nr0cfCNKqGYER1b4C1aANv8KhQSuC7JTPlJm5NTXJapVXLXPsMQggaSk1aUeQjAR3Vc6b1QLFBW1cA3ggzsNm5J00qCCGSAah";

		TransferCreateParams params =
				TransferCreateParams.builder()
						.setAmount(1000L)
						.setCurrency("usd")
						.setSourceTransaction("ch_3NudFQCNKqGYER1b1dEwKt07")
						.setDestination("acct_1Ntv51FhJ8DGic2E")
						.build();

		Transfer transfer = Transfer.create(params);
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
