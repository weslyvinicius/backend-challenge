package com.invillia.acme.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity(name = "order_item")
public class OrderItem extends AbstraticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7267397688578014698L;

	@NotEmpty
	private String description;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal price;

	@Min(1L)
	private Integer quantity;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Builder
	public OrderItem(final String description, final BigDecimal price, final Integer quantity) {
		this.description = description;
		this.price = price;
		this.quantity = quantity;
	}
}
