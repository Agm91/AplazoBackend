package com.aplazo.controller;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.aplazo.model.input.Customer;
import com.aplazo.model.output.RegisterCustomerResponse;
import com.aplazo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testRegisterCustomerAndReturnOk() throws Exception {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setBirthDate(LocalDate.of(1970, 1, 1));

        RegisterCustomerResponse response = new RegisterCustomerResponse(1L, 8000.0);

        when(customerService.registerCustomer(Mockito.any(Customer.class))).thenReturn(response);

        mockMvc.perform(post("/api/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.amount", is(response.getAmount())))
                .andDo(print());
    }

    @Test
    public void testRegisterCustomerAndReturn404() throws Exception {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setBirthDate(LocalDate.of(1990, 1, 1));

        when(customerService.registerCustomer(Mockito.any(Customer.class))).thenThrow(new RuntimeException("Information not valid."));

        mockMvc.perform(post("/api/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}