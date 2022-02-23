package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dto.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingApotModel;
import com.api.parkingcontrol.services.ParkingSpoService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpoService parkingSpoService;

    public ParkingSpotController(ParkingSpoService parkingSpoService) {
        this.parkingSpoService = parkingSpoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        if (parkingSpoService.existsByLicensePlaterCar(parkingSpotDto.getLicensePlateCar())) {
            return ResponseEntity.status(CONFLICT).body("Conflict: License Plate Car is already in use!");
        }

        if (parkingSpoService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
            return ResponseEntity.status(CONFLICT).body("Conflict: Parking Spot is already in use!");

        }
        if (parkingSpoService.existsByApartamentAndBlock(parkingSpotDto.getApartament(), parkingSpotDto.getBlock())) {
            return ResponseEntity.status(CONFLICT).body("Conflict: Apartament and block is already in use!");
        }

        var parkingSpotModel = new ParkingApotModel();

        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistratorDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(CREATED).body(parkingSpoService.save(parkingSpotModel));
    }

    @GetMapping
    public ResponseEntity<Page<ParkingApotModel>> getAllParkingSpot(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") UUID id) {

        Optional<ParkingApotModel> optionalParkingApotModel = parkingSpoService.findById(id);
        if (!optionalParkingApotModel.isPresent()) {
            return ResponseEntity.status(NOT_FOUND).body("Parking Spot not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(optionalParkingApotModel.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpotById(@PathVariable(value = "id") UUID id) {

        Optional<ParkingApotModel> optionalParkingApotModel = parkingSpoService.findById(id);
        if (!optionalParkingApotModel.isPresent()) {
            return ResponseEntity.status(NOT_FOUND).body("Parking Spot not found.");
        }

        parkingSpoService.delete(optionalParkingApotModel.get());

        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpotById(@PathVariable(value = "id") UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        Optional<ParkingApotModel> optionalParkingApotModel = parkingSpoService.findById(id);
        if (!optionalParkingApotModel.isPresent()) {
            return ResponseEntity.status(NOT_FOUND).body("Parking Spot not found.");
        }

        var parkingSpotModel = new ParkingApotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(optionalParkingApotModel.get().getId());
        parkingSpotModel.setRegistratorDate(optionalParkingApotModel.get().getRegistratorDate());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpoService.save(parkingSpotModel));
    }
}
