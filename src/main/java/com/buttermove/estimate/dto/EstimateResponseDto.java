package com.buttermove.estimate.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EstimateResponseDto {
    private String date;
    private double totalCost;
}
