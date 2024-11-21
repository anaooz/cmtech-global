package br.com.cmtech.repository;

import br.com.cmtech.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String nome);
}
