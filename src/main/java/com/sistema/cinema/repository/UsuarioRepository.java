package com.sistema.cinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.cinema.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByMatricula(String matricula);
    Optional<Usuario> findByLogin(String login);
    boolean existsByRoles_Id(Long roleId);
}