package com.example.BankingApp.user.repository;

import com.example.BankingApp.user.enums.RoleEnum;
import com.example.BankingApp.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

   // Role findByName(String name);
   Role findByRoleEnum(RoleEnum roleEnum);
}