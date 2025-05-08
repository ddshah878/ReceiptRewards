package com.dhrumil.ReceiptRewards.Service;

import com.dhrumil.ReceiptRewards.Models.Items;
import com.dhrumil.ReceiptRewards.Models.Request.ReceiptRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.dhrumil.ReceiptRewards.util.Constants.NO_ID_FOUND_ERROR;


@Service
public class RewardsServiceImpl implements RewardsService {
    // storage to keep track of receipt points
    private final Map<String, Integer> pointsStorage = new HashMap<>();
    @Override
    public String saveReceipt(ReceiptRequest receiptRequest) {
        // Generate a unique ID for the receipt
        String uniqueId = generateID().toString();
        pointsStorage.put(uniqueId, calculatePoints(receiptRequest));
        // Return the generated ID
        return uniqueId;
    }

    @Override
    public Integer getRewardsPoints(String id) {
        if (!pointsStorage.containsKey(id)) {
            throw new RuntimeException(NO_ID_FOUND_ERROR);
        }
        return pointsStorage.getOrDefault(id, 404);
    }
    // Method to calculate points based on the receipt details
    private Integer calculatePoints(ReceiptRequest receiptRequest) {
        // Validate input before processing
        validateReceiptRequest(receiptRequest);

        int points = 0;

        // Add points from retailer name
        points += calculateRetailerNamePoints(receiptRequest.getRetailer());

        // Add points from total amount
        points += calculateTotalAmountPoints(receiptRequest.getTotal());

        // Add points from number of items
        points += calculateItemCountPoints(receiptRequest.getItems().size());

        // Add points from item descriptions
        points += calculateItemDescriptionPoints(receiptRequest.getItems());

        // Add points from purchase date
        points += calculatePurchaseDatePoints(receiptRequest.getPurchaseDate());

        // Add points from purchase time
        points += calculatePurchaseTimePoints(receiptRequest.getPurchaseTime());

        return points;
    }

    // Validate receipt request
    private void validateReceiptRequest(ReceiptRequest receiptRequest) {
        if (receiptRequest == null) {
            throw new IllegalArgumentException("Receipt request cannot be null");
        }
        if (receiptRequest.getRetailer() == null || receiptRequest.getRetailer().isEmpty()) {
            throw new IllegalArgumentException("Retailer name cannot be null or empty");
        }
        if (receiptRequest.getTotal() == null || receiptRequest.getTotal() < 0) {
            throw new IllegalArgumentException("Total amount cannot be null or negative");
        }
        if (receiptRequest.getPurchaseDate() == null) {
            throw new IllegalArgumentException("Purchase date cannot be null");
        }
        if (receiptRequest.getPurchaseTime() == null) {
            throw new IllegalArgumentException("Purchase time cannot be null");
        }
        if (receiptRequest.getItems() == null) {
            throw new IllegalArgumentException("Items list cannot be null");
        }
    }

    // Calculate points from retailer name
    private int calculateRetailerNamePoints(String retailerName) {
        int points = 0;
        // Rule: One point for every alphanumeric character in the retailer name
        for(char c: retailerName.toCharArray()) {
            if(Character.isLetterOrDigit(c))
                points++;
        }
        return points;
    }

    // Calculate points from total amount
    private int calculateTotalAmountPoints(Double total) {
        int points = 0;
        // Rule: 50 points if the total is a round dollar amount with no cents
        if (total % 1 == 0) {
            points += 50;
        }
        // Rule: 25 points if the total is a multiple of 0.25
        if (total % 0.25 == 0) {
            points += 25;
        }
        return points;
    }

    // Calculate points from number of items
    private int calculateItemCountPoints(int itemCount) {
        // Rule: 5 points for every two items on the receipt
        return (itemCount / 2) * 5;
    }

    // Calculate points from item descriptions
    private int calculateItemDescriptionPoints(List<Items> items) {
        int points = 0;
        // Rule: If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer
        for (var item : items) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += ((Double)Math.ceil(item.getPrice() * 0.2)).intValue();
            }
        }
        return points;
    }

    // Calculate points from purchase date
    private int calculatePurchaseDatePoints(LocalDate purchaseDate) {
        // Rule: 6 points if the day in the purchase date is odd
        return purchaseDate.getDayOfMonth() % 2 == 1 ? 6 : 0;
    }

    // Calculate points from purchase time
    private int calculatePurchaseTimePoints(LocalTime purchaseTime) {
        // Rule: 10 points if the time of purchase is after 2:00pm and before 4:00pm
        if (purchaseTime.isAfter(LocalTime.of(14, 0, 0))
                && purchaseTime.isBefore(LocalTime.of(16, 0, 0))) {
            return 10;
        }
        return 0;
    }

    private static Long generateID() {
        return UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
    }
}
