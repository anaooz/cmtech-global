package br.com.cmtech.service;

import br.com.cmtech.model.Role;
import br.com.cmtech.model.Usuario;
import br.com.cmtech.repository.RoleRepository;
import br.com.cmtech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void salvar(String username, String password, List<String> roles){
        Set<Role> listaRoles = new HashSet<>();
        for (String nome : roles){
            Role role = roleRepository.findByName(nome);
            if (role != null) listaRoles.add(role);
        }
        Usuario usuario = new Usuario(username, password, listaRoles);
        usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        Set<SimpleGrantedAuthority> authorities = usuario.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                authorities);
    }
}