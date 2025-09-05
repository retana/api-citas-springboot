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

import edu.galileo.citas.model.Cita;
import edu.galileo.citas.model.repository.CitaRepository;
import edu.galileo.citas.service.CitaService;

@WebMvcTest(CitaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CitaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean CitaService citaService;
    @MockBean CitaRepository citaRepository;

    @Test
    void list_ReturnsOk() throws Exception {
        when(citaService.findAll()).thenReturn(List.of(new Cita()));
        mockMvc.perform(get("/citas"))
            .andExpect(status().isOk());
    }

    @Test
    void create_Update_Delete_Flows() throws Exception {
        when(citaRepository.findById(1)).thenReturn(java.util.Optional.of(new Cita()));
        when(citaRepository.save(any(Cita.class))).thenAnswer(i -> i.getArgument(0));

        mockMvc.perform(post("/citas")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"fecha\":\"2025-01-01\",\"hora\":\"10:00\",\"comentario\":\"Rev\",\"motivo\":\"Mant\",\"taller\":\"T1\",\"estado\":\"P\"}"))
            .andExpect(status().isOk());

        mockMvc.perform(put("/citas/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"fecha\":\"2025-01-02\",\"hora\":\"11:00\",\"comentario\":\"Rev2\",\"motivo\":\"Mant\",\"taller\":\"T2\",\"estado\":\"P\"}"))
            .andExpect(status().isOk());

        mockMvc.perform(delete("/citas/1"))
            .andExpect(status().isOk());
    }
}
