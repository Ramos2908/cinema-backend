package com.sistema.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.cinema.entity.Sala;
import com.sistema.cinema.service.EquipamentoService;
import com.sistema.cinema.service.SalaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sala") // Prefixo para todas as rotas: /sala/...
public class SalaController {

    @Autowired
    protected SalaService salaService; // Injeta o serviço

    @Autowired
    protected EquipamentoService equipamentoService;

    
    @GetMapping({"", "/"}) 
    public String handleBaseRequest() {
        // Redireciona para o método de listagem
        return "redirect:/sala/list"; 
    }

    // === 1. FORMULÁRIO PARA CADASTRAR NOVA SALA ===
    @GetMapping("/form") // GET /sala/form
    public String abrirFormulario(Model model) {
        model.addAttribute("sala", new Sala());
        model.addAttribute("equipamentosDisponiveis", equipamentoService.findAll());
        return "cadastrarSala"; 
    }

    // === 2. SALVAR NOVA SALA (POST do formulário) ===
    @PostMapping("/save") // POST /sala/save
    public String salvarSala(@Valid @ModelAttribute Sala sala, Model model, RedirectAttributes ra) {

        // Verifica se o número da sala já existe
        if (salaService.existsByNumeroSala(sala.getNumeroSala())) {
            model.addAttribute("mensagemErro", "Sala com número " + sala.getNumeroSala() + " já cadastrada.");
            return "cadastrarSala"; // Volta para o formulário
        } else {
            try {
                salaService.save(sala); 
                ra.addFlashAttribute("mensagemSucesso", "Sala " + sala.getNumeroSala() + " cadastrada com sucesso!");
                return "redirect:/sala/list";
            } catch (Exception e) {
                model.addAttribute("mensagemErro", "Erro ao salvar a sala: " + e.getMessage());
                return "cadastrarSala";
            }
        }
    }

    // === 3. ABRIR FORMULÁRIO PREENCHIDO PARA EDIÇÃO ===
    @GetMapping("/edit/{id}") // GET /sala/edit/{id}
    public String abrirFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Sala sala = salaService.findById(id); 
            model.addAttribute("sala", sala);     
            return "editarSala"; 
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/sala/list";
        }
    }

    // === 4. ATUALIZAR SALA (POST do formulário de edição) ===
    @PostMapping("/edit") // POST /sala/edit
    public String atualizarSala(@Valid @ModelAttribute Sala sala, RedirectAttributes ra) {
        
        try {
            Sala salaOriginal = salaService.findById(sala.getId());
            
            // Lógica para evitar duplicidade de número de sala ao editar
            if ((salaOriginal.getNumeroSala() != sala.getNumeroSala()) && 
                salaService.existsByNumeroSala(sala.getNumeroSala())) {
                
                ra.addFlashAttribute("mensagemErro", "O número de sala " + sala.getNumeroSala() + " já está em uso por outra sala.");
                return "redirect:/sala/edit/" + sala.getId();
            } 
            
            // Salva/Atualiza
            salaService.save(sala);
            ra.addFlashAttribute("mensagemSucesso", "Sala " + sala.getNumeroSala() + " atualizada com sucesso!");
            
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }
        
        return "redirect:/sala/list"; 
    }

    // === 5. LISTAR TODAS AS SALAS ===
    @GetMapping("/list") // GET /sala/list
    public String listarSalas(Model model) {
        model.addAttribute("salas", salaService.findAll()); 
        return "listarSala"; 
    }

    // === 6. DELETAR SALA ===
    @GetMapping("/delete/{id}") // GET /sala/delete/{id}
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