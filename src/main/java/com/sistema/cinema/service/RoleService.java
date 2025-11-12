package com.sistema.cinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.cinema.entity.Role;
import com.sistema.cinema.repository.RoleRepository;
import com.sistema.cinema.repository.UsuarioRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Role save(Role a) {
        return roleRepository.save(a);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role não encontrado"));
    }

    public void deleteById(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("Role não encontrado");
        }
        // prevent deletion if any user references this role
        if (usuarioRepository.existsByRoles_Id(id)) {
            throw new IllegalStateException("Não é possível excluir o papel: existem usuários associados a este papel.");
        }
        roleRepository.deleteById(id);
    }

    public boolean existsByNome(String nome) {
        return roleRepository.existsByNome(nome);
    }
}