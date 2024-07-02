package com.aplazo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplazo.model.input.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
