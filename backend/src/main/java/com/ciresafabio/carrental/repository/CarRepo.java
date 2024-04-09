package com.ciresafabio.carrental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ciresafabio.carrental.domain.model.Car;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

}
