package com.invillia.acme.entity;

import java.util.Optional;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "store")
public class Store extends AbstraticEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2398544989128961034L;

	@NotEmpty
	private String name;

	@NotEmpty
	private String address;

	@Builder
	public Store(final String name, final String address) {
		this.name = name;
		this.address = address;
	}

	public Store updateWith(final Store store) {
		Optional.of(store).ifPresent(p -> {
			this.setName(p.getName());
			this.setAddress(p.getAddress());
		});

		return this;
	}

}
