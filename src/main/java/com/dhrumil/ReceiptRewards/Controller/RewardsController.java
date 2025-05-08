package com.dhrumil.ReceiptRewards.Controller;

import com.dhrumil.ReceiptRewards.Models.Request.ReceiptRequest;
import com.dhrumil.ReceiptRewards.Models.Response.Points;
import com.dhrumil.ReceiptRewards.Models.Response.Receipt;
import com.dhrumil.ReceiptRewards.Service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RewardsController {

    @Autowired
    RewardsService rewardsService;

    @GetMapping("/hello")
    public String printApplicationName() {
        return "Hello World";
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<Receipt> processReceipt(@RequestBody ReceiptRequest receipt) {
        try {
            // Input validation is handled in the service layer
            String receiptId = rewardsService.saveReceipt(receipt);
            return ResponseEntity.ok(Receipt.builder()
                    .id(receiptId)
                    .build());
        } catch (IllegalArgumentException e) {
            // Return specific error for validation failures
            return ResponseEntity
                    .badRequest()
                    .build();
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity
                    .badRequest()
                    .build();
        }

    }

    @GetMapping("/receipt/{id}/points")
    public ResponseEntity<Points> points(@PathVariable("id") String receiptId) {
        try {
            Integer point = rewardsService.getRewardsPoints(receiptId);
            return ResponseEntity.ok(Points.builder()
                    .points(point)
                    .build());
        } catch (RuntimeException err) {
            return ResponseEntity.notFound()
                    .build();
        }
    }
}
