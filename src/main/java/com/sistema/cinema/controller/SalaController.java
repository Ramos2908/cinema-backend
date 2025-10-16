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

import com.sistema.cinema.entity.Sala;
import com.sistema.cinema.service.EquipamentoService;
import com.sistema.cinema.service.SalaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    protected SalaService salaService;

    @Autowired
    protected EquipamentoService equipamentoService;

    // === FORMULÁRIO PARA CADASTRAR NOVA SALA ===
    @GetMapping("/form")
    public String abrirFormulario(Model model) {
        model.addAttribute("sala", new Sala());
        model.addAttribute("equipamentosDisponiveis", equipamentoService.findAll());
        return "cadastrarSala";
    }

    // === SALVAR NOVA SALA ===
    @PostMapping("/save")
    public String salvarSala(@Valid @ModelAttribute Sala sala, Model model, RedirectAttributes ra) {
        // Verifica duplicidade do número da sala
        if (salaService.existsByNumeroSala(sala.getNumeroSala())) {
            model.addAttribute("mensagemErro", "Sala com número " + sala.getNumeroSala() + " já cadastrada.");
            model.addAttribute("equipamentosDisponiveis", equipamentoService.findAll());
            return "cadastrarSala";
        }
        // Verifica se o equipamento já está em uso
        if (sala.getEquipamento() != null && salaService.existsByEquipamentoId(sala.getEquipamento().getId())) {
            model.addAttribute("mensagemErro", "Este equipamento já está cadastrado em uma sala.");
            model.addAttribute("equipamentosDisponiveis", equipamentoService.findAll());
            return "cadastrarSala";
        }
        try {
            salaService.save(sala);
            ra.addFlashAttribute("mensagemSucesso", "Sala " + sala.getNumeroSala() + " cadastrada com sucesso!");
            return "redirect:/sala/list";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar a sala: " + e.getMessage());
            model.addAttribute("equipamentosDisponiveis", equipamentoService.findAll());
            return "cadastrarSala";
        }
    }

    // === ABRIR FORMULÁRIO PREENCHIDO PARA EDIÇÃO ===
    @GetMapping("/edit/{id}")
    public String abrirFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Sala sala = salaService.findById(id);
            model.addAttribute("sala", sala);
            model.addAttribute("equipamentosDisponiveis", equipamentoService.findAll());
            return "editarSala";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/sala/list";
        }
    }

    // === ATUALIZAR SALA ===
    @PostMapping("/edit")
    public String atualizarSala(@Valid @ModelAttribute Sala sala, RedirectAttributes ra) {

        try {
            Sala salaOriginal = salaService.findById(sala.getId());

            if ((salaOriginal.getNumeroSala() != sala.getNumeroSala()) &&
                    salaService.existsByNumeroSala(sala.getNumeroSala())) {
                ra.addFlashAttribute("mensagemErro", "O número de sala " + sala.getNumeroSala() + " já está em uso por outra sala.");
                return "redirect:/sala/edit/" + sala.getId();
            }

            salaService.save(sala);
            ra.addFlashAttribute("mensagemSucesso", "Sala " + sala.getNumeroSala() + " atualizada com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }

        return "redirect:/sala/list";
    }

    // === LISTAR TODAS AS SALAS ===
    @GetMapping("/list")
    public String listarSalas(Model model) {
        model.addAttribute("salas", salaService.findAll());
        return "listarSala";
    }

    // === DELETAR SALA ===
    @GetMapping("/delete/{id}")
    public String deletarSala(@PathVariable Long id, RedirectAttributes ra) {
        try {
            Sala sala = salaService.findById(id);
            salaService.deleteById(id);
            ra.addFlashAttribute("mensagemSucesso", "Sala " + sala.getNumeroSala() + " deletada com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao deletar: " + e.getMessage());
        }
        return "redirect:/sala/list";
    }
}