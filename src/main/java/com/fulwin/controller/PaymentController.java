package com.fulwin.controller;

import com.fulwin.service.CustomerService;
import com.google.gson.JsonSyntaxException;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.LineItem;
import com.stripe.model.LineItemCollection;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.stripe.exception.*;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${stripe.apikey}")
    private String stripeKey;

    @GetMapping("/success")
    public String checkoutSuccess() { // go to success page

        return "shop/payment-success";
    }

}
