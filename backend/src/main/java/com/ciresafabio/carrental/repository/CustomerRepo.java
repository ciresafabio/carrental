package com.ciresafabio.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciresafabio.carrental.domain.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
