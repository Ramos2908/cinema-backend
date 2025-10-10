package com.sistema.cinema.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "equipamento")
public class Equipamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O modelo do projetor é obrigatório")
    @Column(nullable = false)
    private String modeloProjetor;

    @NotBlank(message = "O sistema de som é obrigatório")
    @Column(nullable = false)
    private String sistemaSom; // ex: Dolby Atmos, 5.1

    @NotBlank(message = "A dimensão da tela é obrigatória")
    @Column(nullable = false)
    private String telaDimensao; // Dimensão da tela

    @NotNull(message = "A informação de ar condicionado é obrigatória")
    @Column(nullable = false)
    private Boolean arCondicionado; // sim/não

    @Column(columnDefinition = "TEXT")
    private String outros; // Detalhes adicionais

    

    // -------------------- GETTERS E SETTERS --------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModeloProjetor() {
        return modeloProjetor;
    }

    public void setModeloProjetor(String modeloProjetor) {
        this.modeloProjetor = modeloProjetor;
    }

    public String getSistemaSom() {
        return sistemaSom;
    }

    public void setSistemaSom(String sistemaSom) {
        this.sistemaSom = sistemaSom;
    }

    public String getTelaDimensao() {
        return telaDimensao;
    }

    public void setTelaDimensao(String telaDimensao) {
        this.telaDimensao = telaDimensao;
    }

    public Boolean getArCondicionado() {
        return arCondicionado;
    }

    public void setArCondicionado(Boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }
}
