package com.invillia.acme.convertUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.invillia.acme.entity.Store;
import com.invillia.acme.exception.ResultNotFoundException;
import com.invillia.acme.resource.dto.StoreDTO;
import com.invillia.acme.util.MessageSourceUtils;

@Component
public class StoreConvertUtil {

	@Autowired
	private MessageSourceUtils messageSourceUtils;

	public Store convertStoreDTOToStoreEntity(final StoreDTO storeDTO) {
		return Store.builder().address(storeDTO.getAddress()).name(storeDTO.getName()).build();
	}

	public StoreDTO convertStoreEntityToStoreDTO(final Store storeEntity) {
		return Optional.ofNullable(storeEntity)
				.map(p -> StoreDTO.builder().address(p.getAddress()).name(p.getName()).id(p.getId()).build())
				.orElseThrow(() -> new ResultNotFoundException(messageSourceUtils.getResultNotFoundExceptionMessage()));
	}

	public Store convertStoreParamsToStoreEntity(final String name, final String address) {
		return Store.builder().name(name).address(address).build();
	}

	public List<StoreDTO> convertStoreEntityListToStoreDTOList(final List<Store> storeEntityList) {
		return Optional.ofNullable(storeEntityList).orElse(Collections.emptyList()).stream()
				.map(this::convertStoreEntityToStoreDTO).collect(Collectors.toList());
	}
}
