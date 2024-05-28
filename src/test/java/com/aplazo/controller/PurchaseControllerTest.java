package com.aplazo.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.aplazo.model.entity.PurchaseEntity;
import com.aplazo.model.input.Credit;
import com.aplazo.model.input.Customer;
import com.aplazo.model.input.Purchase;
import com.aplazo.service.PurchaseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PurchaseService purchaseService;

    private Customer customer;
    private Credit credit;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Carlos");
        customer.setBirthDate(LocalDate.of(1970, 1, 1));

        credit = new Credit();
        credit.setAmount(1000.0);
        customer.setCredit(credit);
    }

    @Test
    public void testRegisterPurchaseAndReturnOk() throws Exception {
        double purchaseAmount = 500.0;
        PurchaseEntity purchase = new PurchaseEntity();
        purchase.setId(1L);
        purchase.setCustomerId(customer.getId());
        purchase.setPurchaseAmount(purchaseAmount);

        when(purchaseService.getCustomerById(anyLong())).thenReturn(Optional.of(customer));
        when(purchaseService.isPurchaseAmountExceedingCredit(any(Customer.class), anyDouble())).thenReturn(false);
        when(purchaseService.registerPurchase(anyLong(), anyDouble())).thenReturn(purchase);

        Purchase request = new Purchase(customer.getId(), purchaseAmount);

        mockMvc.perform(post("/api/purchases/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(purchase.getId()))
                .andDo(print());
    }

    @Test
    public void testRegisterPurchaseCustomerNotFound() throws Exception {
        double purchaseAmount = 500.0;

        when(purchaseService.getCustomerById(anyLong())).thenReturn(Optional.empty());

        Purchase request = new Purchase(customer.getId(), purchaseAmount);

        mockMvc.perform(post("/api/purchases/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(result -> result.getResponse().getContentAsString().contains("Customer not found"))
                .andDo(print());
    }

    @Test
    public void testRegisterPurchaseExceedsCredit() throws Exception {
        when(purchaseService.getCustomerById(anyLong())).thenReturn(Optional.of(customer));
        when(purchaseService.isPurchaseAmountExceedingCredit(any(Customer.class), anyDouble())).thenReturn(true);

        mockMvc.perform(post("/api/purchases/register")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> result.getResponse().getContentAsString().contains("Purchase amount exceeds credit line"))
                .andDo(print());
    }
}
