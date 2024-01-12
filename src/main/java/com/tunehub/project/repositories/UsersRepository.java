package com.tunehub.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tunehub.project.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{
	public Users findByEmail(String email);
}
