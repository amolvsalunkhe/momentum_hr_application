package com.momentum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.momentum.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	User findByUserName(String userName);
}
