package com.example.UserService.Repository;

import com.example.UserService.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByContactNumber(String contactNumber);

    Optional<CustomerEntity> findByFullName(String name);
}
