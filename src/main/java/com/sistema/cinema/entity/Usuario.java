package com.sistema.cinema.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import org.apache.catalina.User;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements UserDetails{

    @jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "{nome.vazio}")
	@Column(nullable = false)
    private String nome;

    @NotBlank(message = "{matricula.vazio}")
	@Column(unique = true, nullable = false)
    private String matricula;

    @NotBlank(message = "{login.vazio}")
	@Column(unique = true, nullable = false)
    private String login;

    @NotBlank(message = "{senha.vazio}")
	@Column(nullable = false)
    private String senha;

    @NotNull(message = "{data.vazio}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "nascimento", nullable = false)
    private Date dataNascimento;

    @NotBlank(message = "{email.vazio}")
	@Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
			name = "tb_user_role",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO: implementar conforme necessário (retorna as roles/autoridades do usuário)
        return roles;
    }

    @Override
    public String getPassword() {
        // Retorna a senha codificada do usuário
        return senha;
    }

    @Override
    public String getUsername() {
        // Retorna o identificador de login do usuário
        return login;
    }

    

}