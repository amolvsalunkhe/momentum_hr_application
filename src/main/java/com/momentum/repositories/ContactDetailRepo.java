package com.momentum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentum.models.ContactDetail;

@Repository
public interface ContactDetailRepo extends JpaRepository<ContactDetail, Long> {

}
