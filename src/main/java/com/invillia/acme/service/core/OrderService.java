package com.invillia.acme.service.core;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.invillia.acme.entity.Order;
import com.invillia.acme.repository.IOrderRepository;
import com.invillia.acme.service.IOrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService implements IOrderService {

	@Autowired
	private IOrderRepository orderRepository;

	public Order save(final Order orderEntity) {
		return orderRepository.save(orderEntity);
	}

	public Order getById(final Long id) {
		return orderRepository.getOne(id);
	}

	public List<Order> getByParams(final Order orderEntity) {
		return orderRepository.findByParams(orderEntity);
	}

	@Async
	@Transactional
	public void refund(final Long id) {
		Order orderEntity = getById(id);
		if (!orderEntity.canRefund()) {
			log.warn("Order: {} with status {} cannot be refunded", id, orderEntity.getStatus());
			return;
		}
		orderEntity.refund();
		save(orderEntity);
	}

	@Async
	@Transactional
	public void refundItem(final Long id, final Long orderItemId) {
		Order orderEntity = getById(id);
		if (!orderEntity.canRefund()) {
			log.warn("Order Item: {}, for order {} with status {} cannot be refunded", orderItemId, id,
					orderEntity.getStatus());
			return;
		}
		orderEntity.refundItem(orderItemId);
		save(orderEntity);
	}
}
