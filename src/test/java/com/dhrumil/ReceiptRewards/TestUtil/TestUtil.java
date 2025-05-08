package com.dhrumil.ReceiptRewards.TestUtil;

import com.dhrumil.ReceiptRewards.Models.Items;
import com.dhrumil.ReceiptRewards.Models.Request.ReceiptRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TestUtil {
    public static ReceiptRequest createTestReceiptRequestOne() {
        return ReceiptRequest.builder()
                .retailer("Target")
                .purchaseDate(LocalDate.of(2022, 1, 1))
                .purchaseTime(LocalTime.of(13, 1))
                .items(createTestItemsOne())
                .total(35.35)
                .build();
    }
    public static List<Items> createTestItemsOne() {
        return List.of(
                Items.builder()
                        .shortDescription("Mountain Dew 12PK").price(6.49)
                        .build()
                ,Items.builder()
                        .price(1.26).shortDescription("Knorr Creamy Chicken")
                        .build()
                ,Items.builder()
                        .price(3.35).shortDescription("Doritos Nacho Cheese")
                        .build()
                ,Items.builder()
                        .price(12.00).shortDescription("   Klarbrunn 12-PK 12 FL OZ  ")
                        .build()
                ,Items.builder()
                        .price(12.25).shortDescription("Emils Cheese Pizza")
                        .build()
        );
    }
    public static ReceiptRequest createTestReceiptRequestTwo() {
        return ReceiptRequest.builder()
                .retailer("M&M Corner Market")
                .purchaseDate(LocalDate.of(2022, 3, 20))
                .purchaseTime(LocalTime.of(14, 33))
                .items(createTestItemsTwo())
                .total(9.00)
                .build();
    }
    public static List<Items> createTestItemsTwo() {
        return List.of(
                Items.builder()
                        .shortDescription("Gatorade").price(2.25)
                        .build()
                ,Items.builder()
                        .price(2.25).shortDescription("Gatorade")
                        .build()
                ,Items.builder()
                        .price(2.25).shortDescription("Gatorade")
                        .build()
                ,Items.builder()
                        .price(2.25).shortDescription("Gatorade")
                        .build()
        );
    }
}
