package com.invillia.acme.service;

import com.invillia.acme.entity.Payment;

public interface IPaymentService {

	/**
	 * Method that creates a payment according to the order
	 * 
	 * @param orderId            id order
	 * @param orderPaymentEntity Entity Order Payment
	 */
	public void create(final Long orderId, final Payment orderPaymentEntity);

}
