package com.sistema.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sistema.cinema.service.SalaService;
import com.sistema.cinema.service.EquipamentoService;
import com.sistema.cinema.service.SessaoService;
import com.sistema.cinema.service.UsuarioService;
import com.sistema.cinema.service.RoleService;

@Controller
@RequestMapping
public class PaginaInicialController {

    @Autowired
    private SalaService salaService;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @GetMapping({"/", "/pagina-inicial"})
    public String home(Model model) {
        model.addAttribute("totalSalas", salaService.findAll().size());
        model.addAttribute("totalEquipamentos", equipamentoService.findAll().size());
        model.addAttribute("totalSessoes", sessaoService.findAll().size());
        // Add users and roles info
        model.addAttribute("totalUsuarios", usuarioService.findAll().size());
        model.addAttribute("totalRoles", roleService.findAll().size());
        model.addAttribute("usuarios", usuarioService.findAll());
        model.addAttribute("roles", roleService.findAll());
        return "paginaInicial";
    }
}