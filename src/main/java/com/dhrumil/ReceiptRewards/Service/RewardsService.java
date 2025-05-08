package com.dhrumil.ReceiptRewards.Service;

import com.dhrumil.ReceiptRewards.Models.Request.ReceiptRequest;

public interface RewardsService {
    public String saveReceipt(ReceiptRequest receiptRequest);

    public Integer getRewardsPoints(String id);
}
