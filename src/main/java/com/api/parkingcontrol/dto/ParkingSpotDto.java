package com.api.parkingcontrol.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ParkingSpotDto {

    @NotBlank
    @Size(max = 7)
    private String licensePlateCar;

    @NotBlank
    private String parkingSpotNumber;

    @NotBlank
    private String brandCar;

    @NotBlank
    private String modelCar;

    @NotBlank
    private String colorCar;

    @NotBlank
    private String responsibleName;

    @NotBlank
    private String apartament;

    @NotBlank
    private String block;
}
