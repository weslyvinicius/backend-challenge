package com.invillia.acme.convertUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invillia.acme.entity.Order;
import com.invillia.acme.entity.OrderItem;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.resource.dto.OrderDTO;
import com.invillia.acme.resource.dto.OrderItemDTO;
import com.invillia.acme.util.MessageSourceUtils;

@Component
public class OrderConvertUtil {

	@Autowired
	private MessageSourceUtils messageSourceUtils;

	@Autowired
	private PaymentConvertUtil paymentMapper;

	public List<OrderDTO> convertOrderEntityListToOrderDTOList(final List<Order> orderEntityList) {
		return Optional.ofNullable(orderEntityList).orElse(new ArrayList<>()).stream().map(this::convertOrderEntityToOrderDTO)
				.collect(Collectors.toList());
	}

	public OrderDTO convertOrderEntityToOrderDTO(final Order orderEntity) {
		return Optional.ofNullable(orderEntity)
				.map(order -> OrderDTO.builder().address(order.getAddress()).confirmationDate(order.getConfirmationDate())
						.id(order.getId()).status(order.getStatus())
						.items(convertOrderItemEntitySetToOrderItemDTOSet(order.getItems()))
						.payment(paymentMapper.convertPaymentEntityToPayment(order.getLastOrderPayment())).build())
				.orElseThrow(() -> new ResultNotFoundException(messageSourceUtils.getResultNotFoundExceptionMessage()));
	}

	public Order convertOrderDTOToOrderEntity(final OrderDTO orderDTO) {
		return Order.builder().address(orderDTO.getAddress())
				.items(convertOrderItemDTOSetToOrderItemEntitySet(orderDTO.getItems())).build();
	}

	public Set<OrderItem> convertOrderItemDTOSetToOrderItemEntitySet(final Set<OrderItemDTO> orderItemDTOSet) {
		return Optional.ofNullable(orderItemDTOSet).orElse(new HashSet<>()).stream().map(this::convertOrderItemDTOToOrderItemEntity)
				.collect(Collectors.toSet());
	}

	public OrderItem convertOrderItemDTOToOrderItemEntity(final OrderItemDTO orderItemDTO) {
		return Optional.of(orderItemDTO).map(item -> OrderItem.builder().description(item.getDescription())
				.price(item.getPrice()).quantity(item.getQuantity()).build()).orElse(null);
	}

	public Set<OrderItemDTO> convertOrderItemEntitySetToOrderItemDTOSet(final Set<OrderItem> orderItemEntitySet) {
		return Optional.ofNullable(orderItemEntitySet).orElse(new HashSet<>()).stream()
				.map(this::convertOrderItemEntityToOrderItemDTO).collect(Collectors.toSet());
	}

	public OrderItemDTO convertOrderItemEntityToOrderItemDTO(final OrderItem orderItemEntity) {
		return Optional.of(orderItemEntity).map(item -> OrderItemDTO.builder().id(item.getId())
				.description(item.getDescription()).price(item.getPrice()).quantity(item.getQuantity()).build())
				.orElse(null);
	}

	public Order convertOrderParamsToOrderEntity(final String address, final OrderStatus status) {
		return Order.builder().address(address).status(status).build();
	}

}
