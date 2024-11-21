package br.com.cmtech.controller;

import br.com.cmtech.dto.UseForm;
import br.com.cmtech.repository.RoleRepository;
import br.com.cmtech.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("registrar")
    public String cadastrar(Model model){
        model.addAttribute("usuario", new UseForm());
        model.addAttribute("roles", roleRepository.findAll());
        return "usuario/form";
    }

    @PostMapping("registrar")
    public String cadastrar(@Valid UseForm useForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "usuario/form";
        }
        usuarioService.salvar(useForm.getUsername(),
                passwordEncoder.encode(useForm.getPassword()), useForm.getRoles());
        return "redirect:/login";
    }

}