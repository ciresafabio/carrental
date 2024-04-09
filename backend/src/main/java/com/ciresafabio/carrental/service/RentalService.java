package com.ciresafabio.carrental.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ciresafabio.carrental.domain.dto.RentalDto;
import com.ciresafabio.carrental.domain.model.Car;
import com.ciresafabio.carrental.domain.model.Customer;
import com.ciresafabio.carrental.domain.model.Rental;
import com.ciresafabio.carrental.repository.CarRepo;
import com.ciresafabio.carrental.repository.CustomerRepo;
import com.ciresafabio.carrental.repository.RentalRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepo rentalRepo;

    private final CarRepo carRepo;

    private final CustomerRepo customerRepo;

    public List<Rental> getAllRentals() {
        return rentalRepo.findAll();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepo.findById(id);
    }

    public Rental createRental(RentalDto rentalDto) {
        Customer customer = getCustomer(rentalDto.getCustomerId());
        Car car = getCar(rentalDto.getCarId());
        checkCarAvailability(rentalDto);
        checkCustomerAvailability(rentalDto);
        // TODO validate before saving, e.g.: only allow if all required fields present
        return rentalRepo.save(Rental.builder().customer(customer).car(car).startDateTime(rentalDto.getStartDateTime())
                .endDateTime(rentalDto.getEndDateTime()).distanceInKilometers(rentalDto.getDistanceInKilometers())
                .build());
    }

    private void checkCustomerAvailability(RentalDto rentalDto) {
        if (!isCustomerAvailable(rentalDto.getCustomerId(), rentalDto.getStartDateTime(), rentalDto.getEndDateTime())) {
            throw new IllegalStateException("Customer is not available for the selected timeframe.");
        }
    }

    private void checkCarAvailability(RentalDto rentalDto) {
        if (!isCarAvailable(rentalDto.getCarId(), rentalDto.getStartDateTime(), rentalDto.getEndDateTime())) {
            throw new IllegalStateException("Car is not available for the selected timeframe.");
        }
    }

    public Rental updateRental(Long id, RentalDto rentalDto) {
        Rental rental = rentalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id " + id));
        Customer customer = getCustomer(rentalDto.getCustomerId());
        Car car = getCar(rentalDto.getCarId());
        checkCarAvailability(rentalDto);
        checkCustomerAvailability(rentalDto);
        // TODO implement mappers
        // maybe only allow start, end, distance modification?
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setStartDateTime(rentalDto.getStartDateTime());
        rental.setEndDateTime(rentalDto.getEndDateTime());
        rental.setDistanceInKilometers(rentalDto.getDistanceInKilometers());
        return rentalRepo.save(rental);
    }

    public void deleteRental(Long id) {
        Rental rental = rentalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found with id " + id));
        rentalRepo.delete(rental);
    }

    private Customer getCustomer(Long customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    private Car getCar(Long carId) {
        return carRepo.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
    }

    private boolean isCarAvailable(Long carId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Rental> rentalsForCar = rentalRepo.findAllByCarId(carId);
        return rentalsForCar.stream().noneMatch(rental -> startDateTime.isBefore(rental.getEndDateTime())
                && endDateTime.isAfter(rental.getStartDateTime()));
    }

    private boolean isCustomerAvailable(Long customerId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Rental> rentalsForCustomer = rentalRepo.findAllByCustomerId(customerId);
        return rentalsForCustomer.stream().noneMatch(rental -> startDateTime.isBefore(rental.getEndDateTime())
                && endDateTime.isAfter(rental.getStartDateTime()));
    }

    public int getTotalKilometersDrivenByCar(Long carId) {
        List<Rental> rentalsForCar = rentalRepo.findAllByCarId(carId);
        return rentalsForCar.stream()
                .mapToInt(Rental::getDistanceInKilometers)
                .sum();
    }
}
