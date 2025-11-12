package com.sistema.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.cinema.entity.Role;
import com.sistema.cinema.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // ---------------- FORMULÁRIO DE CADASTRO ----------------
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("roleObj", new Role());
        return "cadastrarRole";
    }

    // ---------------- SALVAR ROLE ----------------
    @PostMapping("/save")
    public String saveRole(@ModelAttribute("roleObj") Role role, Model model, RedirectAttributes redirectAttrs) {
         // Normaliza o nome para formato padrão ROLE_XYZ
         String normalized = withRolePrefix(role.getNome());
         role.setNome(normalized);

         // Verifica duplicidade
         if (roleService.existsByNome(normalized)) {
             model.addAttribute("mensagemErro", "O papel " + normalized + " já está cadastrado.");
             return "cadastrarRole";
         }

         roleService.save(role);
         // Depois de salvar, redireciona para a lista exibindo mensagem de sucesso
         redirectAttrs.addFlashAttribute("mensagemSucesso", "Papel " + normalized + " cadastrado com sucesso!");
         return "redirect:/role/list";
     }

    // ---------------- LISTAR ROLES ----------------
    @GetMapping("/list")
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "listaRole";
    }

    // ---------------- EDITAR ROLE ----------------
    @GetMapping("/edit/{id}")
    public String editRole(@PathVariable Long id, Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("roleObj", role);
        return "editarRole";
    }

    @PostMapping("/edit")
    public String editRolePost(@ModelAttribute("roleObj") Role role, Model model) {
        String normalized = withRolePrefix(role.getNome());
        role.setNome(normalized);

        Role original = roleService.findById(role.getId());
        if (!original.getNome().equals(normalized) && roleService.existsByNome(normalized)) {
            model.addAttribute("mensagemErro", "O papel " + normalized + " já existe.");
            return listRoles(model);
        }

        roleService.save(role);
        model.addAttribute("mensagemSucesso", "Papel " + normalized + " atualizado com sucesso!");
        return listRoles(model);
    }

    // ---------------- DELETAR ROLE ----------------
    @GetMapping("/delete/{id}")
    public String deleteRole(@PathVariable Long id, Model model) {
        try {
            roleService.deleteById(id);
            model.addAttribute("mensagemSucesso", "Papel deletado com sucesso!");
        } catch (IllegalStateException ex) {
            // role is referenced by users — show friendly message
            model.addAttribute("mensagemErro", ex.getMessage());
        }
        return listRoles(model);
    }

    // ---------------- MÉTODO AUXILIAR ----------------
    private String withRolePrefix(String nome) {
        if (nome == null) return null;
        nome = nome.trim().toUpperCase();
        if (!nome.startsWith("ROLE_")) {
            nome = "ROLE_" + nome;
        }
        return nome;
    }
}