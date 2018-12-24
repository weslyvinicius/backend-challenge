package com.invillia.acme.service;

import java.util.List;

import com.invillia.acme.entity.Order;

public interface IOrderService {

	/**
	 * Method of saving the request class
	 * 
	 * @param Order
	 * @return orderEntity
	 */
	public Order save(final Order orderEntity);

	/**
	 * Search method of class order by id
	 * 
	 * @param id Order
	 * @return OrderEntity
	 */
	public Order getById(final Long id);

	/**
	 * Search method of order class by parameters
	 * 
	 * @param orderEntity
	 * @return Returns a list of Order
	 */
	public List<Order> getByParams(final Order orderEntity);

	/**
	 * Method that Change Order Status for Refund
	 * 
	 * @param id Order
	 */
	public void refund(final Long id);

	/**
	 * Method that changes the item status of the order for refund
	 * 
	 * @param id          Order
	 * @param orderItemId Order Item id0
	 */
	public void refundItem(final Long id, final Long orderItemId);

}
