package com.fulwin.webhook;

import com.fulwin.service.CustomerService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stripewebhook")
public class StripeWebHook {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${stripe.apikey}")
    String stripeKey;

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

        System.out.println("200");
        return "";
    }

    private static void handleCompletedCheckoutSession(Session sessions) throws StripeException {

        Session session = Session.retrieve(sessions.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 5);
        LineItemCollection lineItems = session.listLineItems(params);
        List<LineItem> data = lineItems.getData();
        System.out.println(data.toString());
        PaymentIntent paymentIntent = PaymentIntent.retrieve(sessions.getPaymentIntent());
        System.out.println(paymentIntent.getLatestCharge());
    }

}
