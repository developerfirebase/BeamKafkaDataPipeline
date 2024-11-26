package com.agecalculator.ageCalculator.records;

import lombok.Builder;

@Builder
public record Payload(String name,
                      String address,
                      String  dob) {
}
