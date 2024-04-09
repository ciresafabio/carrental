package com.ciresafabio.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciresafabio.carrental.domain.model.Rental;

@Repository
public interface RentalRepo extends JpaRepository<Rental, Long> {

    List<Rental> findAllByCarId(Long carId);

    List<Rental> findAllByCustomerId(Long customerId);

}
