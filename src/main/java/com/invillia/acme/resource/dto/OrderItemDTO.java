package com.invillia.acme.resource.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OrderItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6003140788858428104L;

	private Long id;

	@NotEmpty
	private String description;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price;

	@Min(1L)
	private Integer quantity;
}
