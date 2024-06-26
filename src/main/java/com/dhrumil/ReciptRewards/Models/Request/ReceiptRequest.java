package com.dhrumil.ReciptRewards.Models.Request;

import com.dhrumil.ReciptRewards.Models.Items;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptRequest {
    String retailer;
    LocalDate purchaseDate;
    LocalTime purchaseTime;
    Double total;
    List<Items> items;
}
