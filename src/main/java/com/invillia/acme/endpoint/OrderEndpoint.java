package com.invillia.acme.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.convertUtil.OrderConvertUtil;
import com.invillia.acme.enums.OrderStatus;
import com.invillia.acme.resource.dto.OrderDTO;
import com.invillia.acme.service.IOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Order")
@RestController
@RequestMapping("/acme/orders")
public class OrderEndpoint {

	@Autowired
	private OrderConvertUtil orderMapper;

	@Autowired
	private IOrderService orderService;

	@ApiOperation(value = "Service to create a order")
	@PostMapping
	public ResponseEntity create(@RequestBody @Valid final OrderDTO order) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(orderMapper.convertOrderEntityToOrderDTO(orderService.save(orderMapper.convertOrderDTOToOrderEntity(order))));
	}

	@ApiOperation(value = "Search service a budget by id")
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(orderMapper.convertOrderEntityToOrderDTO(orderService.getById(id)));
	}

	@ApiOperation(value = "Service search for a budget list by parameters")
	@GetMapping
	public ResponseEntity getByParams(@RequestParam(name = "address", required = false) final String address,
			@RequestParam(name = "status", required = false) final OrderStatus status) {
		return ResponseEntity.ok(orderMapper.convertOrderEntityListToOrderDTOList(
				orderService.getByParams(orderMapper.convertOrderParamsToOrderEntity(address, status))));
	}

	@ApiOperation(value = "Service that refunds an order")
	@DeleteMapping("/refund/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void refund(@PathVariable("id") final Long id) {
		orderService.refund(id);
	}

	@ApiOperation(value = "Service that refunds an order item")
	@DeleteMapping("/items/{id}/refund/{orderItemId}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void refundItem(@PathVariable("id") final Long id, @PathVariable("orderItemId") final Long orderItemId) {
		orderService.refundItem(id, orderItemId);
	}
}
