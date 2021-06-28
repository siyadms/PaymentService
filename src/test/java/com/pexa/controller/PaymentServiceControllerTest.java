package com.pexa.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentServiceControllerTest {
	
    @Autowired
    private MockMvc mockMvc;
    
    private String getJson(int testcaseNo) {
    	StringBuilder json = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(PaymentServiceControllerTest.class.getClassLoader().getResourceAsStream("input"+testcaseNo+".json")),
                            StandardCharsets.UTF_8));
            String str;
            while ((str = in.readLine()) != null)
                json.append(str);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Caught exception reading resource input"+testcaseNo+".json", e);
        }
        return json.toString();
    }


	@Test
	void testInput1() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/payments/schedule")
				.accept(MediaType.APPLICATION_JSON).content(getJson(1))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println("-------"+response.getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertTrue(response.getContentAsString().contains("\"amount\":100000"));
		//assertTrue(response.getContentAsString().equals("[{\"id\":\"bank1~bank2150000\",\"from\":\"bank2\",\"to\":\"bank1\",\"amount\":100000}]"));
		assertFalse(response.getContentAsString().contains("\"amount\":200000"));

	}
	
	@Test
	void testInput2() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/payments/schedule")
				.accept(MediaType.APPLICATION_JSON).content(getJson(2))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		 System.out.println("-------"+response.getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertTrue(response.getContentAsString().contains("\"amount\":100000"));
		assertTrue(response.getContentAsString().contains("\"amount\":350000"));
		//assertTrue(response.getContentAsString().equals("[{\"id\":\"bank1~bank2150000\",\"from\":\"bank2\",\"to\":\"bank1\",\"amount\":100000},{\"id\":\"bank3~bank1350000\",\"from\":\"bank3\",\"to\":\"bank1\",\"amount\":350000}]"));
		assertFalse(response.getContentAsString().contains("\"amount\":200000"));

	}
	
	@Test
	void testInput3() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/payments/schedule")
				.accept(MediaType.APPLICATION_JSON).content(getJson(3))
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		System.out.println("-------"+response.getContentAsString());
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertTrue(response.getContentAsString().contains("\"amount\":100000"));
		assertTrue(response.getContentAsString().contains("\"amount\":400000"));
		// assertTrue(response.getContentAsString().equals("[{\"id\":\"bank1~bank2150000\",\"from\":\"bank2\",\"to\":\"bank1\",\"amount\":100000},{\"id\":\"bank1~bank3100000\",\"from\":\"bank3\",\"to\":\"bank1\",\"amount\":400000},{\"id\":\"bank2~bank3100000\",\"from\":\"bank2\",\"to\":\"bank3\",\"amount\":100000}]"));
		assertFalse(response.getContentAsString().contains("\"amount\":200000"));

	}

}
