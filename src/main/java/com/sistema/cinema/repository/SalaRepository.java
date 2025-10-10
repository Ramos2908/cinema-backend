package com.sistema.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.cinema.entity.Sala;

// SalaRepository.java
public interface SalaRepository extends JpaRepository<Sala, Long> {
    // Este método será implementado automaticamente pelo Spring Data JPA
    boolean existsByNumeroSala(int numeroSala);

	
}