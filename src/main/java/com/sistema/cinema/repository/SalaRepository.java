package com.sistema.cinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.cinema.entity.Sala;


public interface SalaRepository extends JpaRepository<Sala, Long> {
    
    boolean existsByNumeroSala(int numeroSala);
    boolean existsByEquipamentoId(Long equipamentoId);
    Optional<Sala> findByEquipamentoId(Long equipamentoId);
    // Verifica se existe sala com o mesmo numero, excluindo um id específico (útil para atualização)
    boolean existsByNumeroSalaAndIdNot(int numeroSala, Long id);
}