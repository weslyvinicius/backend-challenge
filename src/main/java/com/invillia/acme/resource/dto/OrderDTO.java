package com.invillia.acme.resource.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.invillia.acme.enums.OrderStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2305412411370973560L;

	private final Long id;

	@NotEmpty
	private final String address;

	private final LocalDateTime confirmationDate;

	private final OrderStatus status;

	private final PaymentDTO payment;

	@Size(min = 1, max = 100)
	private final Set<OrderItemDTO> items;
}
