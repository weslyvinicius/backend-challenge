package com.invillia.acme.resource.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class StoreDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7910962848078629395L;

	private final Long id;

	@NotEmpty
	private final String name;

	@NotEmpty
	private final String address;

}
