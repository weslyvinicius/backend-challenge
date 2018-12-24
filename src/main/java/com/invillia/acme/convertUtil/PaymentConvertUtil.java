package com.invillia.acme.convertUtil;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.invillia.acme.entity.Payment;
import com.invillia.acme.resource.dto.PaymentDTO;

@Component
public class PaymentConvertUtil {

	public Payment convertPaymentToPaymentEntity(final PaymentDTO payment) {
		return Optional.ofNullable(payment)
				.map(p -> Payment.builder().creditCardNumber(p.getCreditCardNumber()).build()).orElse(null);
	}

	public PaymentDTO convertPaymentEntityToPayment(final Payment paymentEntity) {
		return Optional.ofNullable(paymentEntity)
				.map(p -> PaymentDTO.builder().id(p.getId()).creditCardNumber(p.getCreditCardNumber())
						.paymentDate(p.getPaymentDate()).status(p.getStatus()).build())
				.orElse(null);
	}

}
