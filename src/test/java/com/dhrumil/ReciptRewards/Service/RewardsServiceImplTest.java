package com.dhrumil.ReciptRewards.Service;

import org.junit.jupiter.api.Test;

import static com.dhrumil.ReciptRewards.TestUtil.TestUtil.createTestReceiptRequestOne;
import static com.dhrumil.ReciptRewards.TestUtil.TestUtil.createTestReceiptRequestTwo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RewardsServiceImplTest {
    private String ID = "TestKey234";

    private RewardsServiceImpl rewardsService = new RewardsServiceImpl();

    @Test
    public void getRewardsWhenRewardIsPresent() {
        String id = rewardsService.saveReceipt(createTestReceiptRequestOne());

        Integer points = rewardsService.getRewardsPoints(id);
        assertEquals(points, 28);
    }
    @Test
    public void getRewardsWhenRewardIsPresent1() {
        String id = rewardsService.saveReceipt(createTestReceiptRequestTwo());

        Integer points = rewardsService.getRewardsPoints(id);
        assertEquals(points, 109);
    }

    @Test
    public void getRewardsWhenRewardIsNotPresent() {
        assertThrows(RuntimeException.class, () ->
                rewardsService.getRewardsPoints(ID));
    }


}
