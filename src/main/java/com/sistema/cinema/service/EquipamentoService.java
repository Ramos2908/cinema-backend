package com.sistema.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cinema.entity.Equipamento;
import com.sistema.cinema.entity.Sala;
import com.sistema.cinema.repository.EquipamentoRepository;
import com.sistema.cinema.repository.SalaRepository;

@Service
public class EquipamentoService {

    @Autowired
    protected EquipamentoRepository equipamentoRepository;

    @Autowired
    protected SalaRepository salaRepository; // Para desvincular equipamento antes de excluir

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
     * Deleta um equipamento pelo ID. Se o equipamento estiver vinculado a uma sala,
     * ele será desvinculado da sala antes de ser removido (sem remover a sala).
     */
    public void deleteById(Long id) {
        if (!equipamentoRepository.existsById(id)) {
            throw new RuntimeException("Equipamento não encontrado");
        }

        // Procurar sala vinculada ao equipamento e desvincular
        Optional<Sala> salaOpt = salaRepository.findByEquipamentoId(id);
        if (salaOpt.isPresent()) {
            Sala sala = salaOpt.get();
            sala.setEquipamento(null);
            salaRepository.save(sala);
        }

        equipamentoRepository.deleteById(id);
    }
    
   
}