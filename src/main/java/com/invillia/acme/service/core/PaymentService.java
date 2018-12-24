package com.invillia.acme.service.core;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.invillia.acme.entity.Order;
import com.invillia.acme.entity.Payment;
import com.invillia.acme.service.IPaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentService implements IPaymentService {

	@Autowired
	private OrderService orderService;

	@Async
	@Transactional
	public void create(final Long orderId, final Payment orderPaymentEntity) {
		Order orderEntity = orderService.getById(orderId);
		if (orderEntity.getLastOrderPayment() != null) {
			log.warn("Order {} already have a payment", orderId);
			return;
		}
		orderService.save(orderEntity.withPayment(orderPaymentEntity));
	}

}
