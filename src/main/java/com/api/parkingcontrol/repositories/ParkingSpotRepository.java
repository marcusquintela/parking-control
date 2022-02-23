package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.ParkingApotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingApotModel, UUID> {

    boolean existsByLicensePlateCar(String licensePlateCar);

    boolean existsByParkingSpotNumber(String parkingSpoNumber);

    boolean existsByApartamentAndBlock(String apartament, String block);

}
