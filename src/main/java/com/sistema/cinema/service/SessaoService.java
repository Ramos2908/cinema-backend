package com.sistema.cinema.service;

import java.util.ArrayList; // Novo import para a correção
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cinema.entity.Sessao;
import com.sistema.cinema.repository.SessaoRepository;

@Service
public class SessaoService {

    @Autowired
    protected SessaoRepository sessaoRepository;

    /** Salva ou atualiza uma sessão. */
    public Sessao save(Sessao s) {
        return sessaoRepository.save(s);
    }

    /** * Busca todas as sessões. 
     * CORREÇÃO: Garante que o retorno nunca é 'null', prevenindo o erro no Thymeleaf.
     */
    public List<Sessao> findAll() {
        List<Sessao> lista = sessaoRepository.findAll();
        // Se a lista for nula, retorna uma ArrayList vazia.
        return lista != null ? lista : new ArrayList<>();
    }

    /** Busca uma sessão pelo ID. */
    public Sessao findById(Long id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    }

    /** Deleta uma sessão pelo ID. */
    public void deleteById(Long id) {
        if (!sessaoRepository.existsById(id)) {
            throw new RuntimeException("Sessão não encontrada");
        }
        sessaoRepository.deleteById(id);
    }
}