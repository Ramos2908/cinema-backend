package com.sistema.cinema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.cinema.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    
}

