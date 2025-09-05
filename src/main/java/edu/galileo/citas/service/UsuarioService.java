package edu.galileo.citas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.model.repository.UsuarioRepository;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuario);
    }

    public Boolean signIn(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return usuario != null && passwordEncoder.matches(password, usuario.getPassword());
    }
}
