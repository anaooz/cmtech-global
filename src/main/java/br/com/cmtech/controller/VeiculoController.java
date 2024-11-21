package br.com.cmtech.controller;

import br.com.cmtech.model.Veiculo;
import br.com.cmtech.repository.VeiculoRepository;
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
@RequestMapping("veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("cadastrar")
    public String cadastrar(Veiculo veiculo, Model model) {
        model.addAttribute("veiculo", new Veiculo());
        return "veiculo/cadastrar";
    }

    @PostMapping("cadastrar")
    @Transactional
    public String cadastrar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "veiculo/cadastrar";
        }
        veiculoRepository.save(veiculo);
        redirectAttributes.addFlashAttribute("mensagem", "Veículo cadastrado com sucesso!");
        return "redirect:/veiculo/cadastrar";
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "veiculo/listar";
    }

    @GetMapping("detalhes/{id}")
    public String detalhesVeiculo(@PathVariable Long id, Model model) {
        Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);
        if (optionalVeiculo.isPresent()) {
            model.addAttribute("veiculo", optionalVeiculo.get());
        } else {
            model.addAttribute("erro", "Veículo não encontrado");
            return "error";
        }
        return "veiculo/detalhes";
    }

    @GetMapping("editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);
        if (optionalVeiculo.isEmpty()) {
            model.addAttribute("erro", "Veículo não encontrado.");
            return "redirect:/veiculo/listar";
        }
        model.addAttribute("veiculo", optionalVeiculo.get());
        return "veiculo/editar";
    }

    @PostMapping("editar")
    public String editar(@Valid Veiculo veiculo, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "veiculo/editar";
        }
        veiculoRepository.save(veiculo);
        redirectAttributes.addFlashAttribute("mensagem", "Veículo atualizado com sucesso!");
        return "redirect:/veiculo/listar";
    }

    @PostMapping("remover")
    @Transactional
    public String remover(Long id, RedirectAttributes redirectAttributes) {
        veiculoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Veículo removido com sucesso");
        return "redirect:/veiculo/listar";
    }
}
