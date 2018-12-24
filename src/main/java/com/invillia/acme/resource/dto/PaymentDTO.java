package com.invillia.acme.resource.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.invillia.acme.enums.PaymentStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PaymentDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1363664221741017681L;
	private final Long id;
	@NotEmpty
	private final String creditCardNumber;
	private final PaymentStatus status;
	private final LocalDateTime paymentDate;
}
