package com.invillia.acme.service.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.entity.Store;
import com.invillia.acme.repository.IStoreRepository;
import com.invillia.acme.service.IStoreService;

@Service
public class StoreService implements IStoreService {

	@Autowired
	private IStoreRepository storeRepository;

	public Store save(final Store storeEntity) {
		return storeRepository.save(storeEntity);
	}

	public Store update(final Long id, final Store storeEntity) {
		Store storeFound = getById(id);
		return save(storeFound.updateWith(storeEntity));
	}

	public Store getById(final Long id) {
		return storeRepository.getOne(id);
	}

	public List<Store> getByStoreParams(final Store storeEntity) {
		return storeRepository.findByParams(storeEntity);
	}

}
