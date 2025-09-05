package edu.galileo.citas.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.model.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        String role = u.getRol() == null ? "USER" : u.getRol();
        return new User(u.getUsername(), u.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role)));
    }
}
