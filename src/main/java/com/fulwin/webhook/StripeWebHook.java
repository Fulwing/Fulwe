package com.fulwin.webhook;

import com.fulwin.Enums.OrderStatus;
import com.fulwin.mapper.LineorderMapper;
import com.fulwin.pojo.Commodity;
import com.fulwin.pojo.Lineorder;
import com.fulwin.service.CommodityService;
import com.fulwin.service.CustomerService;
import com.fulwin.service.LineorderService;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.TransferCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stripewebhook")
public class StripeWebHook {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LineorderService lineorderService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${stripe.apikey}")
    private String stripeKey;

    @ResponseBody
    @PostMapping("/payment")
    public String handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws StripeException {
        Stripe.apiKey = stripeKey;
        String endpointSecret = "whsec_116852f5010a227a960a42814747c6f642821782ad49209ecd3f72fe7a953b5d";

        Event event = null;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (JsonSyntaxException e) {
            // Invalid payload
            System.out.println("400 Invalid payload");
            return "";
        } catch (SignatureVerificationException e) {
            // Invalid signature
            System.out.println("400 Invalid signature");
            return "";
        }

        if ("checkout.session.completed".equals(event.getType())) {
            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();

            if (dataObjectDeserializer.getObject().isPresent()) {
                Session session = (Session) dataObjectDeserializer.getObject().get();
                handleCompletedCheckoutSession(session);
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
                System.out.println("API Version Mismatch");
            }
        }
        return "";
    }

    private void handleCompletedCheckoutSession(Session sessions) throws StripeException {

        Session session = Session.retrieve(sessions.getId());
//        Map<String, Object> params = new HashMap<>();
//        params.put("limit", 5);
//        LineItemCollection lineItems = session.listLineItems(params);
//        List<LineItem> data = lineItems.getData();
//        PaymentIntent paymentIntent = PaymentIntent.retrieve(session.getPaymentIntent());
        Long productId = Long.valueOf(session.getMetadata().get("productId"));
        Long buyerId = Long.valueOf(session.getMetadata().get("buyerId"));
        BigDecimal total = BigDecimal.valueOf(session.getAmountTotal()).divide(BigDecimal.valueOf(100), RoundingMode.DOWN);
        BigDecimal platformFee = BigDecimal.valueOf(1.23);


        //put data into database
        Lineorder lineorder = new Lineorder();
        lineorder.setOrderId(session.getPaymentIntent());
        lineorder.setCommodityId(productId);
        lineorder.setSellerId(commodityService.getCommodityById(productId).getItemCusid()); // change this to connection id
        lineorder.setBuyerId(buyerId);
        lineorder.setTotalPrice(total);
        lineorder.setPlatformFee(platformFee);
        lineorder.setSellerSalary(total.subtract(platformFee));
        lineorder.setOrderStatus(OrderStatus.PROCESSING);

        Commodity commodity = commodityService.getCommodityById(productId);
        commodity.setItemSold(1); // mark as sold

        commodityService.updateCommodity(commodity);
        lineorderService.insertLineOrder(lineorder);

    }

}
