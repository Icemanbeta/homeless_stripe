package com.andy.homeless;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/stripe")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/payment")
	public ResponseEntity<Object> paymentWithCheckoutPage(@RequestBody CheckoutPayment payment)
			throws StripeException, URISyntaxException {
		String sessionUrl = paymentService.getStripeSessionUrl(payment);
		URI stripe = new URI(sessionUrl);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(stripe);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
	}


}
