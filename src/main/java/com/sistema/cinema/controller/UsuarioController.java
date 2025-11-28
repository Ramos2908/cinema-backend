package com.sistema.cinema.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import java.util.Locale;

import com.sistema.cinema.entity.Role;
import com.sistema.cinema.entity.Usuario;
import com.sistema.cinema.service.RoleService;
import com.sistema.cinema.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    // ---------------- FORMULÁRIO DE CADASTRO ----------------
    @GetMapping("/form")
    public String form(Model model) {
        carregarRoles(model);
        model.addAttribute("usuario", new Usuario());
        return "cadastrarUsuario";
    }

    // ---------------- SALVAR USUÁRIO ----------------
    @PostMapping("/save")
    public String saveUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                              BindingResult bindingResult,
                              @RequestParam(value = "roleIds", required = false) java.util.List<Long> roleIds,
                              org.springframework.ui.Model model,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {

        // Se houver erros de validação, retornar ao formulário
        if (bindingResult.hasErrors()) {
            carregarRoles(model);
            return "cadastrarUsuario";
        }

        // Se vierem ids de roles, converter para entidades Role e setar no usuario
        if (roleIds != null && !roleIds.isEmpty()) {
            java.util.Set<Role> rolesSet = new java.util.HashSet<>();
            for (Long rid : roleIds) {
                Role r = roleService.findById(rid);
                if (r != null) {
                    rolesSet.add(r);
                }
            }
            usuario.setRoles(rolesSet);
        }

        // Verifica duplicidade de matrícula
        if (usuarioService.existsByMatricula(usuario.getMatricula())) {
            bindingResult.rejectValue("matricula", "matricula.existe", new Object[]{usuario.getMatricula()}, null);
            carregarRoles(model);
            return "cadastrarUsuario";
        }

        // Codifica senha antes de salvar
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.save(usuario);

        // Use flash attribute so message survives redirect
        String msg = messageSource.getMessage("usuario.cadastrado", null, locale);
        redirectAttributes.addFlashAttribute("mensagemSucesso", msg);
        return "redirect:/usuario/list";
    }

    // ---------------- LISTAR USUÁRIOS ----------------
    @GetMapping("/list")
    public String listUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listarUsuario";
    }

    // ---------------- EDITAR USUÁRIO ----------------
    @GetMapping("/edit/{id}")
    public String editUsuario(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        carregarRoles(model);
        model.addAttribute("usuario", usuario);
        return "editarUsuario";
    }

    @PostMapping("/edit")
    public String editUsuarioPost(@Valid @ModelAttribute("usuario") Usuario usuario,
                                  BindingResult bindingResult,
                                  @org.springframework.web.bind.annotation.RequestParam(value = "roleIds", required = false) java.util.List<Long> roleIds,
                                  Model model,
                                  Locale locale,
                                  RedirectAttributes redirectAttributes) {
        // If validation failed, return to edit form
        if (bindingResult.hasErrors()) {
            carregarRoles(model);
            return "editarUsuario";
        }

        // Converter role ids selecionados para entidades Role
        if (roleIds != null) {
            java.util.Set<Role> rolesSet = new java.util.HashSet<>();
            for (Long rid : roleIds) {
                Role r = roleService.findById(rid);
                if (r != null) rolesSet.add(r);
            }
            usuario.setRoles(rolesSet);
        }

        Usuario original = usuarioService.findById(usuario.getId());

        // Verifica se matrícula foi alterada e já existe em outro usuário
        if (!original.getMatricula().equals(usuario.getMatricula())
                && usuarioService.existsByMatricula(usuario.getMatricula())) {
            bindingResult.rejectValue("matricula", "matricula.existe.outro", new Object[]{usuario.getMatricula()}, null);
            carregarRoles(model);
            model.addAttribute("usuario", usuario);
            return "editarUsuario";
        }

        // Se senha foi alterada, reencoda
        if (!usuario.getSenha().equals(original.getSenha())) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        usuarioService.save(usuario);
        String msg = messageSource.getMessage("usuario.atualizado", null, locale);
        redirectAttributes.addFlashAttribute("mensagemSucesso", msg);
        return "redirect:/usuario/list";
    }

    // ---------------- DELETAR USUÁRIO ----------------
    @GetMapping("/delete/{id}")
    public String deleteUsuario(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, Locale locale) {
        usuarioService.deleteById(id);
        String msg = messageSource.getMessage("usuario.deletado", null, locale);
        redirectAttributes.addFlashAttribute("mensagemSucesso", msg);
        return "redirect:/usuario/list";
    }

    // ---------------- MÉTODO AUXILIAR ----------------
    private void carregarRoles(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
    }
}