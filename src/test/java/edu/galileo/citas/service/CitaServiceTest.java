package edu.galileo.citas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.galileo.citas.model.Cita;
import edu.galileo.citas.model.repository.CitaRepository;

class CitaServiceTest {

    @Test
    void findAllAndSave() {
        CitaRepository repo = mock(CitaRepository.class);
        CitaService svc = new CitaService(repo);

        when(repo.findAll()).thenReturn(List.of(new Cita()));
        assertEquals(1, svc.findAll().size());

        Cita c = new Cita();
        when(repo.save(c)).thenReturn(c);
        assertSame(c, svc.save(c));
    }
}

