package com.prateek.instagram.repository;

import com.prateek.instagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
