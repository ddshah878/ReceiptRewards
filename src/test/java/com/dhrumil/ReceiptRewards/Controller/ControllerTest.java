package com.dhrumil.ReceiptRewards.Controller;

import com.dhrumil.ReceiptRewards.Models.Request.ReceiptRequest;
import com.dhrumil.ReceiptRewards.Models.Response.Points;
import com.dhrumil.ReceiptRewards.Models.Response.Receipt;
import com.dhrumil.ReceiptRewards.Service.RewardsService;
import com.dhrumil.ReceiptRewards.TestUtil.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReceiptRequest receiptRequest;

    @BeforeEach
    void setUp() {
        receiptRequest = TestUtil.createTestReceiptRequestOne();
        // Initialize receiptRequest with appropriate test data
    }

    @Test
    public void testPrintApplicationName() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void testProcessReceipt() throws Exception {
        String receiptId = "test-receipt-id";
        Receipt receiptResponse = Receipt.builder().id(receiptId).build();

        Mockito.when(rewardsService.saveReceipt(Mockito.any(ReceiptRequest.class)))
                .thenReturn(receiptId);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receiptRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(receiptResponse)));
    }

    @Test
    public void testPoints() throws Exception {
        String receiptId = "test-receipt-id";
        int points = 100;
        Points pointsResponse = Points.builder().points(points).build();


        Mockito.when(rewardsService.getRewardsPoints(receiptId))
                .thenReturn(points);

        mockMvc.perform(get("/receipt/{id}/points", receiptId))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(pointsResponse)));
    }

    @Test
    public void testProcessReceiptBadRequest() throws Exception {
        Mockito.when(rewardsService.saveReceipt(Mockito.any(ReceiptRequest.class)))
                .thenThrow(new RuntimeException("Error processing receipt"));

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receiptRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPointsNotFound() throws Exception {
        String receiptId = "non-existent-receipt-id";

        Mockito.when(rewardsService.getRewardsPoints(receiptId))
                .thenThrow(new RuntimeException("Receipt not found"));

        mockMvc.perform(get("/receipt/{id}/points", receiptId))
                .andExpect(status().isNotFound());
    }
}
