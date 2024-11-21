package br.com.cmtech.controller;

import br.com.cmtech.model.EstacaoCarregamento;
import br.com.cmtech.model.RegistroCarregamento;
import br.com.cmtech.model.Veiculo;
import br.com.cmtech.repository.EstacaoCarregamentoRepository;
import br.com.cmtech.repository.RegistroCarregamentoRepository;
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
@RequestMapping("registro-carregamento")
public class RegistroController {

    @Autowired
    private RegistroCarregamentoRepository registroCarregamentoRepository;

    @Autowired
    private EstacaoCarregamentoRepository estacaoCarregamentoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping("cadastrar")
    public String cadastrar(RegistroCarregamento registroCarregamento, Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
        model.addAttribute("registroCarregamento", new RegistroCarregamento());
        return "registro-carregamento/cadastrar";
    }

    @PostMapping("cadastrar")
    @Transactional
    public String cadastrar(@Valid RegistroCarregamento registroCarregamento, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
            return "registro-carregamento/cadastrar";
        }
        registroCarregamentoRepository.save(registroCarregamento);
        redirectAttributes.addFlashAttribute("mensagem", "Registro de carregamento cadastrado com sucesso!");
        return "redirect:/registro-carregamento/cadastrar";
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("registros", registroCarregamentoRepository.findAll());
        return "registro-carregamento/listar";
    }

    @GetMapping("detalhes/{id}")
    public String detalhesRegistro(@PathVariable Long id, Model model) {
        Optional<RegistroCarregamento> optionalRegistro = registroCarregamentoRepository.findById(id);
        if (optionalRegistro.isPresent()) {
            model.addAttribute("registro", optionalRegistro.get());
        } else {
            model.addAttribute("erro", "Registro de carregamento não encontrado");
            return "error";
        }
        return "registro-carregamento/detalhes";
    }

    @GetMapping("editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Optional<RegistroCarregamento> optionalRegistro = registroCarregamentoRepository.findById(id);
        if (optionalRegistro.isEmpty()) {
            model.addAttribute("erro", "Registro não encontrado.");
            return "redirect:/registro-carregamento/listar";
        }
        model.addAttribute("veiculos", veiculoRepository.findAll());
        model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
        model.addAttribute("registroCarregamento", optionalRegistro.get());
        return "registro-carregamento/editar";
    }

    @PostMapping("editar")
    public String editar(@Valid RegistroCarregamento registroCarregamento, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("veiculos", veiculoRepository.findAll());
            model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
            return "registro-carregamento/editar";
        }
        registroCarregamentoRepository.save(registroCarregamento);
        redirectAttributes.addFlashAttribute("mensagem", "Registro de carregamento atualizado com sucesso!");
        return "redirect:/registro-carregamento/listar";
    }

    @PostMapping("remover")
    @Transactional
    public String remover(Long id, RedirectAttributes redirectAttributes) {
        registroCarregamentoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Registro de carregamento removido com sucesso");
        return "redirect:/registro-carregamento/listar";
    }
}
