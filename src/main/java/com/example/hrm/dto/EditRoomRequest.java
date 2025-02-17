package com.example.hrm.dto;

import com.example.hrm.domain.RoomStatus;
import com.example.hrm.domain.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditRoomRequest {
    @NotBlank(message = "Room number is required")
    private String roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Nearest station is required")
    private String nearestStation;

    @NotBlank(message = "Building name is required")
    private String buildingName;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotNull(message = "Short term price is required")
    @Positive(message = "Short term price must be positive")
    private BigDecimal shortTermPrice;

    @NotNull(message = "Middle term price is required")
    @Positive(message = "Middle term price must be positive")
    private BigDecimal middleTermPrice;

    @NotNull(message = "Status is required")
    private RoomStatus status;

    @NotBlank(message = "Image URL is required")
    private String imageUrl;
}
