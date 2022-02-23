package com.api.parkingcontrol.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_PARKING_SPOT")
@Data
public class ParkingApotModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String licensePlateCar;

    @Column(nullable = false, unique = true, length = 7)
    private String parkingSpotNumber;

    @Column(nullable = false, length = 70)
    private String brandCar;

    @Column(nullable = false, length = 70)
    private String modelCar;

    @Column(nullable = false, length = 70)
    private String colorCar;

    @Column(nullable = false)
    private LocalDateTime registratorDate;

    @Column(nullable = false, length = 130)
    private String responsibleName;

    @Column(nullable = false, length = 30)
    private String apartament;

    @Column(nullable = false, length = 30)
    private String block;

}
