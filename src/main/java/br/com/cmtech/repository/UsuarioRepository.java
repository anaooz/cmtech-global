package br.com.cmtech.repository;

import br.com.cmtech.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByUsername(String username);

}
