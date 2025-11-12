package com.sistema.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.cinema.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByNome(String nome);
}
