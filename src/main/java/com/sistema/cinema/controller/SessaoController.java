package com.sistema.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.cinema.entity.Sessao;
import com.sistema.cinema.service.SessaoService;
import com.sistema.cinema.service.SalaService; 

import jakarta.validation.Valid;

@Controller
@RequestMapping("/sessao")
public class SessaoController {

    @Autowired
    protected SessaoService sessaoService;
    
    @Autowired
    protected SalaService salaService; // Para buscar as salas disponíveis

    // Método auxiliar para adicionar objetos comuns ao Model
    private void adicionarObjetosComuns(Model model) {
        // Adiciona a lista de salas para preencher o campo de seleção (dropdown)
        model.addAttribute("salasDisponiveis", salaService.findAll()); 
    }

    // === 1. FORMULÁRIO PARA CADASTRAR NOVA SESSÃO ===
    @GetMapping("/form")
    public String abrirFormulario(Model model) {
        model.addAttribute("sessao", new Sessao());
        adicionarObjetosComuns(model);
        return "cadastrarSessao";
    }

    // === 2. SALVAR NOVA SESSÃO ===
    @PostMapping("/save")
    public String salvarSessao(@Valid @ModelAttribute Sessao sessao, Model model, RedirectAttributes ra) {
        try {
            sessaoService.save(sessao);
            ra.addFlashAttribute("mensagemSucesso", "Sessão do filme '" + sessao.getFilme() + "' salva com sucesso!");
            return "redirect:/sessao/list";
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao salvar a sessão: " + e.getMessage());
            adicionarObjetosComuns(model);
            return "cadastrarSessao"; // Volta para o formulário em caso de erro
        }
    }

    // === 3. ABRIR FORMULÁRIO PREENCHIDO PARA EDIÇÃO ===
    @GetMapping("/edit/{id}")
    public String abrirFormularioEdicao(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Sessao sessao = sessaoService.findById(id);
            model.addAttribute("sessao", sessao);
            adicionarObjetosComuns(model); // Carrega as salas para o dropdown
            return "editarSessao";
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", e.getMessage());
            return "redirect:/sessao/list";
        }
    }

    // === 4. ATUALIZAR SESSÃO (POST do formulário de edição) ===
    @PostMapping("/edit")
    public String atualizarSessao(@Valid @ModelAttribute Sessao sessao, RedirectAttributes ra) {
        try {
            Sessao sessaoExistente = sessaoService.findById(sessao.getId());
            // Atualize apenas os campos necessários
            sessaoExistente.setFilme(sessao.getFilme());
            sessaoExistente.setSala(sessao.getSala());
            sessaoExistente.setDataHora(sessao.getDataHora());
            sessaoService.save(sessaoExistente);
            ra.addFlashAttribute("mensagemSucesso", "Sessão do filme '" + sessao.getFilme() + "' atualizada com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }
        return "redirect:/sessao/list";
    }

    // === 5. LISTAR TODAS AS SESSÕES ===
    @GetMapping("/list")
    public String listarSessoes(Model model) {
        model.addAttribute("sessoes", sessaoService.findAll());
        return "listarSessao";
    }

    // === 6. DELETAR SESSÃO ===
    @GetMapping("/delete/{id}")
    public String deletarSessao(@PathVariable Long id, RedirectAttributes ra) {
        try {
            Sessao sessao = sessaoService.findById(id); // Busca para obter o nome do filme
            sessaoService.deleteById(id);
            ra.addFlashAttribute("mensagemSucesso", "Sessão do filme '" + sessao.getFilme() + "' deletada com sucesso!");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao deletar: " + e.getMessage());
        }
        return "redirect:/sessao/list";
    }
}