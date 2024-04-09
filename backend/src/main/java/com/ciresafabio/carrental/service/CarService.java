package com.ciresafabio.carrental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ciresafabio.carrental.domain.model.Car;
import com.ciresafabio.carrental.repository.CarRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepo carRepo;

    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepo.findById(id);
    }

    public Car saveCar(Car car) {
        // TODO validate before saving, e.g.: only allow if all required fields present
        return carRepo.save(car);
    }

    public Car updateCar(Long id, Car carDetails) {
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + id));
        // TODO mappers with POJO DTOs
        car.setMake(carDetails.getMake());
        car.setModel(carDetails.getModel());
        car.setLicensePlate(carDetails.getLicensePlate());
        car.setVin(carDetails.getVin());
        return carRepo.save(car);
    }

    public void deleteCar(Long id) {
        Car car = carRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id " + id));
        // TODO handle delete constraints
        carRepo.delete(car);
    }

}
