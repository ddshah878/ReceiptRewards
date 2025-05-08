package com.dhrumil.ReceiptRewards.Models.Request;

import com.dhrumil.ReceiptRewards.Models.Items;
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
