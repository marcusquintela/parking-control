package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ParkingApotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParkingSpoService {

    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpoService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @Transactional
    public ParkingApotModel save(ParkingApotModel parkingApotModel) {
        return parkingSpotRepository.save(parkingApotModel);
    }

    public boolean existsByLicensePlaterCar(String licensePlateCar) {
        return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
    }

    public boolean existsByParkingSpotNumber(String parkingSpoNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpoNumber);
    }

    public boolean existsByApartamentAndBlock(String apartament, String block) {
        return parkingSpotRepository.existsByApartamentAndBlock(apartament, block);
    }

    public Page<ParkingApotModel> findAll(Pageable pageable) {
        return parkingSpotRepository.findAll(pageable);
    }

    public Optional<ParkingApotModel> findById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    @Transactional
    public void delete(ParkingApotModel parkingApotModel) {
        parkingSpotRepository.delete(parkingApotModel);
    }
}
