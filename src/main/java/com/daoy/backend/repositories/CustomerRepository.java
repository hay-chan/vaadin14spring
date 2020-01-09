package com.daoy.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daoy.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
