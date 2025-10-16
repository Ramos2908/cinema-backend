package com.sistema.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.cinema.entity.Sala;


public interface SalaRepository extends JpaRepository<Sala, Long> {
    
    boolean existsByNumeroSala(int numeroSala);
    boolean existsByEquipamentoId(Long equipamentoId);
}