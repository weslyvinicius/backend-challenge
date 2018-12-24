package com.invillia.acme.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.invillia.acme.convertUtil.StoreConvertUtil;
import com.invillia.acme.resource.dto.StoreDTO;
import com.invillia.acme.service.IStoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API REST Store")
@RestController
@RequestMapping("acme/stores")
public class StoreEndpoint {

	@Autowired
	private IStoreService storeService;

	@Autowired
	private StoreConvertUtil storeMapper;

	@ApiOperation(value = "Store creation service")
	@PostMapping
	public ResponseEntity create(@RequestBody @Valid final StoreDTO store) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(storeMapper.convertStoreEntityToStoreDTO(storeService.save(storeMapper.convertStoreDTOToStoreEntity(store))));
	}

	@ApiOperation(value = "Store update service")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") final Long id, @RequestBody @Valid final StoreDTO store) {
		storeService.update(id, storeMapper.convertStoreDTOToStoreEntity(store));
	}

	@ApiOperation(value = "Search service for a store by id")
	@GetMapping("/{id}")
	public ResponseEntity getById(@PathVariable("id") final Long id) {
		return ResponseEntity.ok(storeMapper.convertStoreEntityToStoreDTO(storeService.getById(id)));
	}

	@ApiOperation(value = "Search service list store by parameters")
	@GetMapping
	public ResponseEntity getByParams(@RequestParam(name = "name", required = false) final String name,
			@RequestParam(name = "address", required = false) final String address) {
		return ResponseEntity.ok(storeMapper.convertStoreEntityListToStoreDTOList(
				storeService.getByStoreParams(storeMapper.convertStoreParamsToStoreEntity(name, address))));
	}
}
