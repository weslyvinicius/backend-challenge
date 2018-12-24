package com.invillia.acme.integrated;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.invillia.acme.entity.Order;
import com.invillia.acme.entity.OrderItem;
import com.invillia.acme.resource.dto.OrderDTO;
import com.invillia.acme.resource.dto.OrderItemDTO;

public class OrderDataUtil {

	public static List<OrderDTO> orders() {
		List<OrderDTO> orders = new ArrayList<>();
		orders.add(OrderDTO.builder().address("Address New York city").items(orderItems()).build());
		return orders;
	}

	public static Set<OrderItemDTO> orderItems() {
		Set<OrderItemDTO> orderItems = new HashSet<>();
		orderItems.add(OrderItemDTO.builder().description("Item 1").price(BigDecimal.TEN).quantity(10).build());
		orderItems.add(OrderItemDTO.builder().description("Item 2").price(BigDecimal.TEN).quantity(9).build());
		return orderItems;
	}

	public static List<Order> orderEntities() {
		List<Order> orderEntities = new ArrayList<>();
		orderEntities.add(Order.builder().address("Address New York city").items(orderItemEntities()).build());
		return orderEntities;
	}

	public static Set<OrderItem> orderItemEntities() {
		Set<OrderItem> orderItemEntities = new HashSet<>();
		orderItemEntities
				.add(OrderItem.builder().description("Item 1").price(BigDecimal.TEN).quantity(10).build());
		orderItemEntities
				.add(OrderItem.builder().description("Item 2").price(BigDecimal.TEN).quantity(9).build());
		return orderItemEntities;
	}

}
