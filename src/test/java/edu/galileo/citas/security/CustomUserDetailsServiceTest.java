package edu.galileo.citas.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.model.repository.UsuarioRepository;

class CustomUserDetailsServiceTest {

    @Test
    void loadUser_ReturnsUserWithRole() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        Usuario u = new Usuario();
        u.setUsername("alice"); u.setPassword("pass"); u.setRol("ADMIN");
        when(repo.findByUsername("alice")).thenReturn(u);

        CustomUserDetailsService svc = new CustomUserDetailsService(repo);
        UserDetails details = svc.loadUserByUsername("alice");
        assertEquals("alice", details.getUsername());
        assertTrue(details.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUser_NotFound_Throws() {
        UsuarioRepository repo = mock(UsuarioRepository.class);
        when(repo.findByUsername("none")).thenReturn(null);
        CustomUserDetailsService svc = new CustomUserDetailsService(repo);
        assertThrows(UsernameNotFoundException.class, () -> svc.loadUserByUsername("none"));
    }
}

