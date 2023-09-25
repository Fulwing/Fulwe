package com.fulwin.api;

import com.fulwin.service.CustomerService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/stripe")
public class StripeApi {

    @Autowired
    CustomerService customerService;

    @Value("${stripe.apikey}")
    String stripeKey;

    @ResponseBody
    @PostMapping("/createAccount")
    public String createAccount(@RequestBody com.fulwin.pojo.Customer fulweCus) throws StripeException {
        // Rest of your code...
        Stripe.apiKey = stripeKey;

        Map<String, Object> cardPayments =
                new HashMap<>();
        cardPayments.put("requested", true);
        Map<String, Object> transfers = new HashMap<>();
        transfers.put("requested", true);
        Map<String, Object> capabilities =
                new HashMap<>();
        capabilities.put("card_payments", cardPayments);
        capabilities.put("transfers", transfers);
        Map<String, Object> params = new HashMap<>();
        params.put("type", "express");
        params.put("country", "US");
        params.put("email", fulweCus.getEmail());
        params.put("capabilities", capabilities);

        Account account = Account.create(params);
//        fulweCus.setStripeId(account.getId());
//        customerService.updateCustomer(fulweCus);

        return account.getId();
    }

    @PostMapping("/getALink")
    public String getAccountLink(@RequestParam("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        AccountLinkCreateParams paramss =
                AccountLinkCreateParams.builder()
                        .setAccount(id)
                        .setRefreshUrl("https://example.com/reauth")
                        .setReturnUrl("https://example.com/return")
                        .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                        .build();

        AccountLink accountLink = AccountLink.create(paramss);

        return accountLink.getUrl();
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(@RequestParam("id") String id) throws StripeException {
        Stripe.apiKey = stripeKey;

        Account account =
                Account.retrieve(id);

        Account deletedAccount = account.delete();
        return deletedAccount.getId();
    }

    @ResponseBody
    @PostMapping("/checkout")
    public String checkOut(@RequestParam("userid") String userId, @RequestParam("productName") String productName, @RequestParam("productPrice") BigDecimal productPrice, @RequestParam("productId") Long productId) throws StripeException {
        Stripe.apiKey = stripeKey;

        BigDecimal prices = productPrice.multiply(BigDecimal.valueOf(100));

        Map<String, Object> product_data = new HashMap<>();
        product_data.put("name", productName);
        Map<String, Object> paramss = new HashMap<>();
        paramss.put("unit_amount", prices);
        paramss.put("currency", "usd");
        paramss.put("product_data", product_data);

        Price price = Price.create(paramss);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String requestUrl = "/shop/item/" + productId;
        String cancelUrl = baseUrl + requestUrl;

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build())
                        .setPaymentIntentData(
                                SessionCreateParams.PaymentIntentData.builder()
                                        .setApplicationFeeAmount(123L)
                                        .setTransferData(
                                                SessionCreateParams.PaymentIntentData.TransferData.builder()
                                                        .setDestination(userId)
                                                        .build())
                                        .build())
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("https://example.com/success")
                        .setCancelUrl(cancelUrl)
                        .build();

        Session session = Session.create(params);

// 303 redirect to session.getUrl()
        return session.getUrl();
    }
}
