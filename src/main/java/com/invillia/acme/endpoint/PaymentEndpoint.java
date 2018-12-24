package com.invillia.acme.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.convertUtil.PaymentConvertUtil;
import com.invillia.acme.resource.dto.PaymentDTO;
import com.invillia.acme.service.IPaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Store")
@RestController
@RequestMapping("/acme/orders/{orderId}/payments")
public class PaymentEndpoint {

	@Autowired
	private PaymentConvertUtil paymentMapper;

	@Autowired
	private IPaymentService paymentService;

	@ApiOperation(value = "Service to create a payment")
	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void create(@PathVariable("orderId") final Long orderId, @RequestBody @Valid PaymentDTO payment) {
		paymentService.create(orderId, paymentMapper.convertPaymentToPaymentEntity(payment));
	}

}
