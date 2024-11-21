package br.com.cmtech.controller;
import br.com.cmtech.model.EstacaoCarregamento;
import br.com.cmtech.repository.EstacaoCarregamentoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("estacao-carregamento")
public class EstacaoController {

    @Autowired
    private EstacaoCarregamentoRepository estacaoCarregamentoRepository;

    @GetMapping("cadastrar")
    public String cadastrar(EstacaoCarregamento estacaoCarregamento, Model model) {
        model.addAttribute("estacaoCarregamento", new EstacaoCarregamento());
        return "estacao-carregamento/cadastrar";
    }

    @PostMapping("cadastrar")
    @Transactional
    public String cadastrar(@Valid EstacaoCarregamento estacaoCarregamento, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "estacao-carregamento/cadastrar";
        }
        estacaoCarregamentoRepository.save(estacaoCarregamento);
        redirectAttributes.addFlashAttribute("mensagem", "Estação de carregamento cadastrada com sucesso!");
        return "redirect:/estacao-carregamento/cadastrar";
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
        return "estacao-carregamento/listar";
    }

    @GetMapping("detalhes/{id}")
    public String detalhesEstacao(@PathVariable Long id, Model model) {
        Optional<EstacaoCarregamento> optionalEstacao = estacaoCarregamentoRepository.findById(id);
        if (optionalEstacao.isPresent()) {
            model.addAttribute("estacao", optionalEstacao.get());
        } else {
            model.addAttribute("erro", "Estação de carregamento não encontrada");
            return "error";
        }
        return "estacao-carregamento/detalhes";
    }

    @GetMapping("editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Optional<EstacaoCarregamento> optionalEstacao = estacaoCarregamentoRepository.findById(id);
        if (optionalEstacao.isEmpty()) {
            model.addAttribute("erro", "Estação não encontrada.");
            return "redirect:/estacao-carregamento/listar";
        }
        model.addAttribute("estacaoCarregamento", optionalEstacao.get());
        return "estacao-carregamento/editar";
    }

    @PostMapping("editar")
    public String editar(@Valid EstacaoCarregamento estacaoCarregamento, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "estacao-carregamento/editar";
        }
        estacaoCarregamentoRepository.save(estacaoCarregamento);
        redirectAttributes.addFlashAttribute("mensagem", "Estação de carregamento atualizada com sucesso!");
        return "redirect:/estacao-carregamento/listar";
    }

    @PostMapping("remover")
    @Transactional
    public String remover(Long id, RedirectAttributes redirectAttributes) {
        estacaoCarregamentoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Estação de carregamento removida com sucesso");
        return "redirect:/estacao-carregamento/listar";
    }
}
