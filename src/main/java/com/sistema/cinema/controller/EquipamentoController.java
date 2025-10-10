package com.sistema.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.cinema.entity.Equipamento;
import com.sistema.cinema.service.EquipamentoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/equipamento")
public class EquipamentoController {

    @Autowired
    protected EquipamentoService equipamentoService;

    // Rota base
    @GetMapping({"", "/"})
    public String handleBaseRequest() {
        return "redirect:/equipamento/list";
    }

    // === 1. FORMULÁRIO PARA CADASTRAR NOVO EQUIPAMENTO ===
    @GetMapping("/form") 
    public String abrirFormulario(Model model) {
        model.addAttribute("equipamento", new Equipamento()); 
        return "cadastrarEquipamento"; 
    }

    // === 2. SALVAR NOVO EQUIPAMENTO ===
    @PostMapping("/save") 
    public String salvarEquipamento(@Valid @ModelAttribute Equipamento equipamento, Model model, RedirectAttributes ra) {
        try {
            equipamentoService.save(equipamento);
            ra.addFlashAttribute("mensagemSucesso", "Equipamento (" + equipamento.getModeloProjetor() + ") cadastrado com sucesso!");
            return "redirect:/equipamento/list";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());
            return "cadastrarEquipamento"; 
        }
    }

    // === 3. ABRIR FORMULÁRIO PREENCHIDO PARA EDIÇÃO ===
    @GetMapping("/edit/{id}") 
    public String abrirFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Equipamento equipamento = equipamentoService.findById(id);
            model.addAttribute("equipamento", equipamento);
            return "editarEquipamento";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/equipamento/list";
        }
    }

    // === 4. ATUALIZAR EQUIPAMENTO (POST do formulário de edição) ===
    @PostMapping("/edit") 
    public String atualizarEquipamento(@Valid @ModelAttribute Equipamento equipamento, RedirectAttributes ra) {
        try {
            equipamentoService.save(equipamento);
            ra.addFlashAttribute("mensagemSucesso", "Equipamento (" + equipamento.getModeloProjetor() + ") atualizado com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }
        return "redirect:/equipamento/list";
    }

    // === 5. LISTAR TODOS OS EQUIPAMENTOS ===
    @GetMapping("/list") 
    public String listarEquipamentos(Model model) {
        model.addAttribute("equipamentos", equipamentoService.findAll());
        return "listarEquipamento";
    }

    // === 6. DELETAR EQUIPAMENTO ===
    @GetMapping("/delete/{id}")
    public String deletarEquipamento(@PathVariable Long id, RedirectAttributes ra) {
        try {
            equipamentoService.deleteById(id);
            ra.addFlashAttribute("mensagemSucesso", "Equipamento deletado com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao deletar: " + e.getMessage());
        }
        return "redirect:/equipamento/list";
    }
}