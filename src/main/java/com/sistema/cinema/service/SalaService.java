package com.sistema.cinema.service;

import java.util.List;
import java.util.Optional; // Importado para um método de busca mais seguro, se necessário

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cinema.entity.Sala;
import com.sistema.cinema.repository.SalaRepository;

@Service // Indica que esta classe é um serviço do Spring, responsável pela lógica de negócio.
public class SalaService {

    @Autowired // Injeta automaticamente uma instância do SalaRepository (acesso ao banco de dados).
    protected SalaRepository salaRepository;

    /**
     * Salva uma sala no banco de dados (Criação ou Atualização).
     * @param sala A entidade Sala a ser persistida.
     * @return A Sala persistida.
     */
    public Sala save(Sala sala) {
        // Implementação do fluxo de salvamento: repassa para o repositório.
        return salaRepository.save(sala);
    }

    /**
     * Busca todas as salas cadastradas.
     * @return Lista de Salas.
     */
    public List<Sala> findAll() {
        // Implementação do fluxo de listagem: SELECT * no banco.
        return salaRepository.findAll();
    }

    /**
     * Busca uma sala pelo ID.
     * @param id O ID da Sala.
     * @return A Sala encontrada.
     * @throws RuntimeException se a sala não for encontrada.
     */
    public Sala findById(Long id) {
        // Implementação do fluxo de busca por ID.
        // Usa orElseThrow para lançar exceção caso a Sala não exista.
        return salaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));
    }

    /**
     * Busca uma sala pelo equipamento associado (caso exista).
     */
    public Optional<Sala> findByEquipamentoId(Long equipamentoId) {
        return salaRepository.findByEquipamentoId(equipamentoId);
    }
    
    /**
     * Deleta uma sala pelo ID.
     * @param id O ID da Sala a ser removida.
     * @throws RuntimeException se a sala não existir.
     */
    public void deleteById(Long id) {
       
        if (!salaRepository.existsById(id)) {
            throw new RuntimeException("Sala não encontrada");
        }
        salaRepository.deleteById(id);
    }

    /**
     * Verifica se já existe uma sala com o número especificado.
     * (Requer que o método 'existsByNumeroSala(int)' exista no SalaRepository).
     * @param numeroSala O número da sala a ser verificada.
     * @return 'true' se a sala já existe, 'false' caso contrário.
     */
    public boolean existsByNumeroSala(int numeroSala) {
        return salaRepository.existsByNumeroSala(numeroSala);
    }

    /**
     * Verifica se já existe uma sala com o mesmo número, ignorando a sala com o id fornecido.
     * Útil para checagem de duplicidade durante atualização.
     */
    public boolean existsByNumeroSalaAndIdNot(int numeroSala, Long id) {
        return salaRepository.existsByNumeroSalaAndIdNot(numeroSala, id);
    }
    
    public boolean existsByEquipamentoId(Long equipamentoId) {
        return salaRepository.existsByEquipamentoId(equipamentoId);
    }
}