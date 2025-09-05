package edu.galileo.citas.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsuarioService usuarioService;

    @Test
    void list_ReturnsOk() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(new Usuario()));
        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk());
    }

    @Test
    void create_ReturnsOk() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenAnswer(i -> i.getArgument(0));
        mockMvc.perform(post("/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nombre\":\"X\",\"username\":\"x\",\"password\":\"y\"}"))
            .andExpect(status().isOk());
    }
}
