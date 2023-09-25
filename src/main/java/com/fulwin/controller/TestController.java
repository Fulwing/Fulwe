package com.fulwin.controller;
import com.fulwin.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/test")
    public String register(@RequestParam("email") String email) {
        Customer customer = new Customer();
        customer.setEmail(email);

        ObjectMapper objectMapper = new ObjectMapper();

        String responseFromExternalAPI = null;
        try {
            // Convert the customer object to a JSON string
            String requestBody = objectMapper.writeValueAsString(customer);

            // Create an HTTP headers object and set content type to JSON
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create an HttpEntity with headers and the JSON request body
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the POST request to the external API with the "customer" parameter
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/stripe/createAccount", requestEntity, String.class);

            // Get the response from the external API
            responseFromExternalAPI = responseEntity.getBody();

            // Rest of your code...
        } catch (Exception e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return responseFromExternalAPI;
    }

}
