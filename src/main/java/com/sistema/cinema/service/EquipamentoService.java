package com.sistema.cinema.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sistema.cinema.entity.Equipamento;
import com.sistema.cinema.repository.EquipamentoRepository;

@Service
public class EquipamentoService {

    @Autowired
    protected EquipamentoRepository equipamentoRepository;

    /**
     * Salva um equipamento no banco de dados.
     */
    public Equipamento save(Equipamento e) {
        return equipamentoRepository.save(e);
    }

    /**
     * Busca todos os equipamentos cadastrados.
     */
    public List<Equipamento> findAll() {
        return equipamentoRepository.findAll();
    }

    /**
     * Busca um equipamento pelo ID.
     */
    public Equipamento findById(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
    }

    /**
     * Deleta um equipamento pelo ID.
     */
    public void deleteById(Long id) {
        if (!equipamentoRepository.existsById(id)) {
            throw new RuntimeException("Equipamento não encontrado");
        }
        equipamentoRepository.deleteById(id);
    }
    
   
}