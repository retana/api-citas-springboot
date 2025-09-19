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

import edu.galileo.citas.model.Vehiculo;
import edu.galileo.citas.model.repository.VehiculoRepository;
import edu.galileo.citas.service.VehiculoService;
import edu.galileo.citas.security.JwtAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetailsService;

@WebMvcTest(VehiculoController.class)
@AutoConfigureMockMvc(addFilters = false)
class VehiculoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean VehiculoService vehiculoService;
    @MockBean VehiculoRepository vehiculoRepository;
    @MockBean JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean UserDetailsService userDetailsService;

    @Test
    void list_ReturnsOk() throws Exception {
        when(vehiculoService.findAll()).thenReturn(List.of(new Vehiculo()));
        mockMvc.perform(get("/vehiculos"))
            .andExpect(status().isOk());
    }

    @Test
    void create_Update_Delete_Flows() throws Exception {
        when(vehiculoRepository.findById(1)).thenReturn(java.util.Optional.of(new Vehiculo()));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/vehiculos")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"marca\":\"Toyota\",\"modelo\":\"Corolla\",\"placa\":\"P1\",\"tipo\":\"Sedan\"}"))
            .andExpect(status().isOk());

        mockMvc.perform(put("/vehiculos/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"marca\":\"Honda\",\"modelo\":\"Civic\",\"placa\":\"P2\",\"tipo\":\"Sedan\"}"))
            .andExpect(status().isOk());

        mockMvc.perform(delete("/vehiculos/1"))
            .andExpect(status().isOk());
    }
}
