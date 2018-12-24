package com.invillia.acme.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.ObjectUtils;

import com.invillia.acme.enums.OrderStatus;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "orders")
public class Order extends AbstraticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6771117045733440309L;

	private static final Long REFUND_PERIOD_IN_DAYS = 10l;

	@NotEmpty
	private String address;

	@NotNull
	@Column(name = "confirmation_date")
	private LocalDateTime confirmationDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "order")
	private List<Payment> payments;

	@NotNull
	@Size(min = 1)
	@OneToMany(mappedBy = "order")
	private Set<OrderItem> items;

	@Builder
	public Order(final String address, final Set<OrderItem> items, final OrderStatus status) {
		this.address = address;
		this.items = items;
		this.status = status;
	}

	public Order withPayment(final Payment orderPaymentEntity) {
		Optional.of(orderPaymentEntity).ifPresent(orderPayment -> {
			this.payments = Optional.ofNullable(this.payments).orElse(new ArrayList<>());
			orderPayment.setOrder(this);
			this.setStatus(OrderStatus.COMPLETE);
			this.payments.add(orderPayment);
		});
		return this;
	}

	public void refundItem(final Long orderItemId) {
		if (canRefund()) {
			Optional.of(items)
					.ifPresent(items -> items.removeIf(item -> ObjectUtils.nullSafeEquals(item.getId(), orderItemId)));
		}
	}

	public void refund() {
		if (canRefund()) {
			Payment lastConcludedPayment = getLastConcludedPayment();
			lastConcludedPayment.refund();
			status = OrderStatus.REFUNDED;
		}
	}

	public boolean canRefund() {
		Payment lastConcludedPayment = getLastConcludedPayment();
		return lastConcludedPayment != null && isConfirmationInsideRefundPeriod();
	}

	public boolean isConfirmationInsideRefundPeriod() {
		return confirmationDate.isAfter(LocalDateTime.now().minusDays(REFUND_PERIOD_IN_DAYS));
	}

	public Payment getLastOrderPayment() {
		return Optional.ofNullable(payments).orElse(Collections.emptyList()).stream()
				.sorted(Comparator.comparing(Payment::getPaymentDate).reversed()).findFirst().orElse(null);
	}

	private Payment getLastConcludedPayment() {
		return Optional.ofNullable(payments).orElse(Collections.emptyList()).stream()
				.filter(payment -> payment.isConcluded()).findFirst().orElse(null);
	}

	@PrePersist
	private void prePersist() {
		this.confirmationDate = LocalDateTime.now();
		this.status = OrderStatus.PAYMENT_PENDING;
	}

}
