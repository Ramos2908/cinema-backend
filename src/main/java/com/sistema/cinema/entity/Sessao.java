package com.sistema.cinema.entity;


import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "sessao")
public class Sessao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "O nome do filme é obrigatório")
    @Column(nullable = false)
    private String filme;

    @NotNull(message = "A data e hora da sessão são obrigatórias")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // Adaptação para data e hora
    @Temporal(TemporalType.TIMESTAMP) // Armazena data e hora
    @Column(name = "data_hora", nullable = false)
    private Date dataHora;

    @NotBlank(message = "O idioma é obrigatório")
    @Column(nullable = false)
    private String idioma;

   
    @NotNull(message = "A informação de legendado é obrigatória") 
    @Column(nullable = false)
    private boolean legendado; // sim/não. Padrão no Java é 'false'.

   
    @NotNull(message = "A duração é obrigatória")
    @Min(value = 1, message = "A duração deve ser no mínimo 1 minuto")
    @Column(nullable = false)
    private int duracaoMinutos; // Duração em minutos. Padrão no Java é '0'.

    @NotBlank(message = "O status da sessão é obrigatório")
    @Column(nullable = false)
    private String status; // agendada, em andamento, concluída, cancelada

    // Relacionamento N:1 com Sala (Sessão é o lado "filho" / dono da FK)
    @ManyToOne
    @JoinColumn(name = "sala_id", nullable = false) // Coluna de FK na tabela 'sessao'
    @NotNull(message = "A sala da sessão é obrigatória")
    private Sala sala;

    // -------------------- GETTERS E SETTERS --------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilme() {
        return filme;
    }

    public void setFilme(String filme) {
        this.filme = filme;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

   
    public boolean isLegendado() { 
        return legendado;
    }

    public void setLegendado(boolean legendado) {
        this.legendado = legendado;
    }

   
    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
}