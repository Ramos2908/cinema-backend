package com.sistema.cinema.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Version;

@SuppressWarnings("serial")
@Entity
@Table(name = "sala")
public class Sala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
 
    
    @NotNull(message = "O número da sala é obrigatório")
    @Column(name = "numero_sala", unique = true, nullable = false) 
    private int numeroSala;

    @NotNull(message = "A capacidade da sala é obrigatória")
    @Min(value = 1, message = "A capacidade deve ser no mínimo 1")
    @Column(nullable = false)
    private int capacidade;

    @NotBlank(message = "O tipo da sala é obrigatório")
    @Column(name = "tipo_sala", nullable = false)
    private String tipoSala; // 2D, 3D, IMAX, VIP

    @NotBlank(message = "O status da sala é obrigatório")
    @Column(nullable = false)
    private String status; // disponível, manutenção, ocupada

    // Relacionamento 1:1 com Equipamento
    @OneToOne
    private Equipamento equipamento;

    // Relacionamento 1:N com Sessao
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL)
    private List<Sessao> sessoes;

    @Version
    private Integer version;

    // -------------------- GETTERS E SETTERS --------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public int getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(int numeroSala) {
        this.numeroSala = numeroSala;
    }

   
    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public String getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(String tipoSala) {
        this.tipoSala = tipoSala;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }

    // Version getter/setter to allow initializing missing DB values
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}