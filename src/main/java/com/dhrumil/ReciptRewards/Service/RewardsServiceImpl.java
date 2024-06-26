package com.dhrumil.ReciptRewards.Service;

import com.dhrumil.ReciptRewards.Models.Request.ReceiptRequest;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.dhrumil.ReciptRewards.util.Constants.NO_ID_FOUND_ERROR;


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
        int points = 0;
        // Rule: One point for every alphanumeric character in the retailer name
        for(char c: receiptRequest.getRetailer().toCharArray()) {
            if(Character.isLetterOrDigit(c))
                points++;
        }

        if (receiptRequest.getTotal() % 1 == 0) {
            points += 50;
        }

        if (receiptRequest.getTotal() % 0.25 == 0) {
            points += 25;
        }

        points += ((receiptRequest.getItems().size() / 2) * 5);

        for (var item : receiptRequest.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += ((Double)Math.ceil(item.getPrice() * 0.2)).intValue();
            }
        }

        if (receiptRequest.getPurchaseDate().getDayOfMonth() % 2 == 1) {
            points += 6;
        }

        if (receiptRequest.getPurchaseTime().isAfter(LocalTime.of(14, 0, 0))
                && receiptRequest.getPurchaseTime().isBefore(LocalTime.of(16, 0, 0))) {
            points += 10;
        }

        return points;
    }

    private static Long generateID() {
        return UUID.randomUUID().getLeastSignificantBits() & Long.MAX_VALUE;
    }
}
