package com.momentum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentum.models.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
