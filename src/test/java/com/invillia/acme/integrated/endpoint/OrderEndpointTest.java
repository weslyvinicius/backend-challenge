package com.invillia.acme.integrated.endpoint;

import static com.invillia.acme.util.ObjectMapperUtil.serializeToString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.After;
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
import com.invillia.acme.integrated.OrderDataUtil;
import com.invillia.acme.repository.IOrderRepository;
import com.invillia.acme.resource.dto.OrderDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = InvilliaApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class OrderEndpointTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private IOrderRepository orderRepository;

	@Before
	public void setupOrders() {
		orderRepository.saveAll(OrderDataUtil.orderEntities());
	}

	@After
	public void clearOrders() {
		orderRepository.deleteAll();
	}

	@Test
	public void createOrderSuccess() throws Exception {
		mvc.perform(post("/acme/orders").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(serializeToString(
						OrderDTO.builder().address("Address Boston city").items(OrderDataUtil.orderItems()).build())))
				.andDo(print()).andExpect(status().isCreated());
	}

	@Test
	public void createOrderError() throws Exception {
		mvc.perform(post("/acme/orders").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(serializeToString(OrderDTO.builder().address("").items(Collections.emptySet()).build())))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void getByParamsSuccess() throws Exception {
		mvc.perform(get("/acme/orders?address=New York&status=PAYMENT_PENDING")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	public void getByParamsNoContent() throws Exception {
		mvc.perform(get("/acme/orders?address=Boston&status=PAYMENT_PENDING")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(0)));
	}

}
