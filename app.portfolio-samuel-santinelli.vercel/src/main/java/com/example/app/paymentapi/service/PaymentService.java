package com.example.app.paymentapi.service;

import com.example.app.paymentapi.model.Payment;
import com.example.app.paymentapi.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String efiBaseUrl; // Configuração da URL base da API da EFI

    @Autowired
    public PaymentService(RestTemplate restTemplate, @Value("${efi.api.base-url}") String efiBaseUrl) {
        this.restTemplate = restTemplate;
        this.efiBaseUrl = efiBaseUrl;
    }

    public PaymentResponse processPayment(Payment payment) {
        String url = efiBaseUrl + "/process-payment";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Payment> requestEntity = new HttpEntity<>(payment, headers);
        
        ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, PaymentResponse.class);
        
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Handle error cases
            throw new RuntimeException("Failed to process payment. Status code: " + responseEntity.getStatusCode());
        }
    }

    public PaymentResponse checkPaymentStatus(String paymentId) {
        String url = efiBaseUrl + "/check-payment-status/{paymentId}";
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("paymentId", paymentId);
        
        HttpEntity<?> entity = new HttpEntity<>(headers);
        
        ResponseEntity<PaymentResponse> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                PaymentResponse.class,
                paymentId);
        
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            // Handle error cases
            throw new RuntimeException("Failed to check payment status. Status code: " + responseEntity.getStatusCode());
        }
    }
}
