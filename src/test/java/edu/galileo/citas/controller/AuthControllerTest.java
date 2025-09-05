package edu.galileo.citas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.model.repository.UsuarioRepository;
import edu.galileo.citas.security.JwtService;
import edu.galileo.citas.service.UsuarioService;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean AuthenticationManager authenticationManager;
    @MockBean JwtService jwtService;
    @MockBean UsuarioService usuarioService;
    @MockBean UsuarioRepository usuarioRepository;

    @Test
    void login_ReturnsOk() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken("juan", "pass");
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtService.generateToken("juan")).thenReturn("jwt");

        mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"juan\",\"password\":\"pass\"}"))
            .andExpect(status().isOk());
    }

    @Test
    void register_ReturnsOk() throws Exception {
        when(usuarioRepository.findByUsername("juan")).thenReturn(null);
        when(usuarioService.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombre\":\"Juan\",\"username\":\"juan\",\"password\":\"x\"}"))
            .andExpect(status().isOk());
    }
}

