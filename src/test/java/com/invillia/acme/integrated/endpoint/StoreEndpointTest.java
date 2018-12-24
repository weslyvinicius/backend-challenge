package com.invillia.acme.integrated.endpoint;

import static com.invillia.acme.util.ObjectMapperUtil.serializeToString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.invillia.acme.InvilliaApplication;
import com.invillia.acme.entity.Store;
import com.invillia.acme.repository.IStoreRepository;
import com.invillia.acme.resource.dto.StoreDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InvilliaApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class StoreEndpointTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private IStoreRepository storeRepository;

	@Before
	public void setupStores() {
		if (storeRepository.count() < 1)
			storeRepository.saveAll(storeEntities());
	}

	@Test
	public void createStoreSuccess() throws Exception {
		mvc.perform(post("/acme/stores").contentType(MediaType.APPLICATION_JSON_UTF8).content(
				serializeToString(StoreDTO.builder().name("Store Boston city").address("Address Boston city").build())))
				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void createStoreError() throws Exception {
		mvc.perform(post("/acme/stores").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(serializeToString(createStore(null, null)))).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateStoreSuccess() throws Exception {
		mvc.perform(put("/acme/stores/1").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(serializeToString(
						StoreDTO.builder().name("Store Marvel Studios").address("Address Burbank USA ").build())))
				.andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	public void updateStoreError() throws Exception {
		mvc.perform(put("/acme/stores/1").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(serializeToString(StoreDTO.builder().name(null).address(null).build()))).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getByParamsSuccess() throws Exception {
		mvc.perform(get("/acme/stores?name=Iron&address=Avanges")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void getByParamsNoResult() throws Exception {
		mvc.perform(get("/acme/stores?name=Iron&address=Asgard")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(0)));
	}

	private List<Store> storeEntities() {
		List<Store> StoreEntities = new ArrayList<>();
		StoreEntities.add(Store.builder().name("Store Batman").address("Address BatCavern").build());
		StoreEntities.add(Store.builder().name("Store Iron Man").address("Address Building Avanges").build());
		StoreEntities.add(Store.builder().name("Store Thor").address("Address Asgard Planet").build());
		return StoreEntities;
	}

	private StoreDTO createStore(final String name, final String address) {
		return StoreDTO.builder().address(address).name(name).build();
	}

}
