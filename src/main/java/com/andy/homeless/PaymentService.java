package com.andy.homeless;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class PaymentService {
	
	@Value("${STRIPE_KEY}")
	String stripeKey;
	public String getStripeSessionUrl(CheckoutPayment payment) throws StripeException {
		Stripe.apiKey = stripeKey;
		// Stripe session parameters
		SessionCreateParams params = SessionCreateParams.builder()
				// Credit card payment method
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
				.setCancelUrl(
						payment.getCancelUrl())
				.addLineItem(
						SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder()
												.setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
												.setProductData(SessionCreateParams.LineItem.PriceData.ProductData
														.builder().setName(payment.getName()).build())
												.build())
								.build())
				.build();
		// Create a stripe session
		Session stripeSession = Session.create(params);
		return stripeSession.getUrl();
	}

}
