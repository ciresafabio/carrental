package com.ciresafabio.carrental.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ciresafabio.carrental.domain.dto.RentalDto;
import com.ciresafabio.carrental.domain.model.Rental;
import com.ciresafabio.carrental.service.RentalService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "rental")
@RequiredArgsConstructor
// TODO add spring security and configure globally
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class RentalApi {

    private final RentalService rentalService;

    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return ResponseEntity.ok(rentals);
    }

    @GetMapping("{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        return rentalService.getRentalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody RentalDto rental) {
        Rental savedRental = rentalService.createRental(rental);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRental);
    }

    @PutMapping("{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody RentalDto rentalDetails) {
        try {
            Rental updatedRental = rentalService.updateRental(id, rentalDetails);
            return ResponseEntity.ok(updatedRental);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        try {
            rentalService.deleteRental(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/car/{carId}/total-kilometers")
    public ResponseEntity<Integer> getTotalKilometersDrivenByCar(@PathVariable Long carId) {
        int totalKilometers = rentalService.getTotalKilometersDrivenByCar(carId);
        return ResponseEntity.ok(totalKilometers);
    }

}
