package edu.galileo.citas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.model.repository.UsuarioRepository;

class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder);
    }

    @Test
    void save_EncodesPassword() {
        Usuario u = new Usuario();
        u.setNombre("Test");
        u.setUsername("test");
        u.setPassword("plain");

        when(passwordEncoder.encode("plain")).thenReturn("encoded");
        when(usuarioRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Usuario saved = usuarioService.save(u);
        assertEquals("encoded", saved.getPassword());

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertEquals("encoded", captor.getValue().getPassword());
    }

    @Test
    void signIn_UsesPasswordEncoder() {
        Usuario u = new Usuario();
        u.setUsername("test");
        u.setPassword("encoded");
        when(usuarioRepository.findByUsername("test")).thenReturn(u);
        when(passwordEncoder.matches("plain", "encoded")).thenReturn(true);

        assertTrue(usuarioService.signIn("test", "plain"));
        when(passwordEncoder.matches("bad", "encoded")).thenReturn(false);
        assertFalse(usuarioService.signIn("test", "bad"));
    }

    @Test
    void findAll_ReturnsList() {
        when(usuarioRepository.findAll()).thenReturn(List.of());
        assertNotNull(usuarioService.findAll());
    }
}

