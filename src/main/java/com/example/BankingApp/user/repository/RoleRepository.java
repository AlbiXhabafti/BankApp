package com.example.BankingApp.user.repository;

import com.example.BankingApp.user.enums.RoleEnum;
import com.example.BankingApp.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
   Optional<Role> findByRoleEnum(RoleEnum roleEnum);
}