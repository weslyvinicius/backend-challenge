package com.invillia.acme.service;

import java.util.List;

import com.invillia.acme.entity.Store;

public interface IStoreService {

	/**
	 * Method of saving a store
	 * 
	 * @param storeEntity
	 * @return StoreEntity
	 */
	public Store save(final Store storeEntity);

	/**
	 * Store Change Method
	 * 
	 * @param id
	 * @param storeEntity
	 * @return return StoreEntity
	 */
	public Store update(Long id, final Store storeEntity);

	/**
	 * Search method of store by id
	 * 
	 * @param id
	 * @return Return Store
	 */
	public Store getById(final Long id);

	/**
	 * Store Search Method by Parameters
	 * 
	 * @param storeEntity
	 * @return Return list of Stores
	 */
	public List<Store> getByStoreParams(final Store storeEntity);

}
