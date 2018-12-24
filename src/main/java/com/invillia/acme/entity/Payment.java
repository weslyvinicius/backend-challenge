package com.invillia.acme.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.invillia.acme.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "payment")
public class Payment extends AbstraticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8516802127057325031L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty
	private String creditCardNumber;

	@NotNull
	@Column(name = "payment_date")
	private LocalDateTime paymentDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@Builder
	public Payment(final String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public void refund() {
		this.status = PaymentStatus.REFUNDED;
	}

	public boolean isConcluded() {
		return PaymentStatus.CONCLUDED == status;
	}

	@PrePersist
	private void prePersist() {
		this.paymentDate = LocalDateTime.now();
		this.status = PaymentStatus.CONCLUDED;
	}
}
