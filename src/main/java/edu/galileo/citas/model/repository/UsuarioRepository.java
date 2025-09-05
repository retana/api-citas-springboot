package edu.galileo.citas.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.galileo.citas.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer>{
    List<Usuario> findAll();
    Usuario findByUsername(String username);
}
