package com.dhrumil.ReciptRewards.Service;

import com.dhrumil.ReciptRewards.Models.Request.ReceiptRequest;

public interface RewardsService {
    public String saveReceipt(ReceiptRequest receiptRequest);

    public Integer getRewardsPoints(String id);
}
