package com.momentum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentum.models.Address;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

}
