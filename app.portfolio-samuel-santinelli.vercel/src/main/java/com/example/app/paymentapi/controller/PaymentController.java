// src/main/java/com/example/app/paymentapi/controller/PaymentController.java
package com.example.app.paymentapi.controller;

import com.example.app.paymentapi.model.Payment;
import com.example.app.paymentapi.model.PaymentResponse;
import com.example.app.paymentapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(@RequestBody Payment payment) {
        return paymentService.processPayment(payment);
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable String id) {
        // Lógica para obter o pagamento
        Payment payment = new Payment();
        payment.setId(id);
        payment.setAmount(100.0);
        payment.setDescription("Payment description");
        return payment;
    }
}
