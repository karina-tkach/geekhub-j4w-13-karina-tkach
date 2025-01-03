package org.geekhub.ticketbooking.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.geekhub.ticketbooking.stripe.Request;
import org.geekhub.ticketbooking.stripe.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentIntentController {
    @PostMapping("/create-payment-intent")
    public Response createPaymentIntent(@RequestBody Request request)
        throws StripeException {
        PaymentIntentCreateParams params =
            PaymentIntentCreateParams.builder()
                .setAmount(request.getAmount() * 100L)
                .putMetadata("productName",
                    request.getProductName())
                .setCurrency("uah")
                .setAutomaticPaymentMethods(
                    PaymentIntentCreateParams
                        .AutomaticPaymentMethods
                        .builder()
                        .setEnabled(true)
                        .build()
                )
                .build();

        PaymentIntent intent =
            PaymentIntent.create(params);

        return new Response(intent.getId(),
            intent.getClientSecret());
    }
}
