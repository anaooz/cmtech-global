    package br.com.cmtech.controller;

    import br.com.cmtech.model.Bateria;
    import br.com.cmtech.repository.BateriaRepository;
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
    @RequestMapping("bateria")
    public class BateriaController {

        @Autowired
        private BateriaRepository bateriaRepository;

        @Autowired
        private EstacaoCarregamentoRepository estacaoCarregamentoRepository;

        @GetMapping("cadastrar")
        public String cadastrar(Bateria bateria, Model model) {
            model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
            model.addAttribute("bateria", new Bateria());
            return "bateria/cadastrar";
        }

        @PostMapping("cadastrar")
        @Transactional
        public String cadastrar(@Valid Bateria bateria, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
            if (result.hasErrors()) {
                model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
                return "bateria/cadastrar";
            }
            bateriaRepository.save(bateria);
            redirectAttributes.addFlashAttribute("mensagem", "Bateria cadastrada com sucesso!");
            return "redirect:/bateria/cadastrar";
        }

        @GetMapping("listar")
        public String listar(Model model) {
            model.addAttribute("baterias", bateriaRepository.findAll());
            return "bateria/listar";
        }

        @GetMapping("detalhes/{id}")
        public String detalhesBateria(@PathVariable Long id, Model model) {
            Optional<Bateria> optionalBateria = bateriaRepository.findById(id);
            if (optionalBateria.isPresent()) {
                model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
                model.addAttribute("bateria", optionalBateria.get());
            } else {
                model.addAttribute("erro", "Bateria não encontrada");
                return "error";
            }
            return "bateria/detalhes";
        }

        @GetMapping("editar/{id}")
        public String editar(@PathVariable("id") Long id, Model model) {
            Optional<Bateria> optionalBateria = bateriaRepository.findById(id);
            if (optionalBateria.isEmpty()) {
                model.addAttribute("erro", "Bateria não encontrada.");
                return "redirect:/bateria/listar";
            }
            model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
            model.addAttribute("bateria", optionalBateria.get());
            return "bateria/editar";
        }

        @PostMapping("editar")
        public String editar(@Valid Bateria bateria, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
            if (result.hasErrors()) {
                model.addAttribute("estacoes", estacaoCarregamentoRepository.findAll());
                return "bateria/editar";
            }
            bateriaRepository.save(bateria);
            redirectAttributes.addFlashAttribute("mensagem", "Bateria atualizada com sucesso!");
            return "redirect:/bateria/listar";
        }

        @PostMapping("remover")
        @Transactional
        public String remover(Long id, RedirectAttributes redirectAttributes) {
            bateriaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Bateria removida com sucesso");
            return "redirect:/bateria/listar";
        }
    }
