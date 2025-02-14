package com.example.hrm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String roomType;
    private String address;
    private String buildingName;
    private Company company;
    private BigDecimal shortTermPrice;
    private BigDecimal middleTermPrice;
    private String status;
    private String imageUrl;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Company {
        private Long id;
        private String name;
    }
}
