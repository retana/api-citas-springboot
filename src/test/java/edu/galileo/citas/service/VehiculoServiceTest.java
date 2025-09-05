package edu.galileo.citas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.galileo.citas.model.Vehiculo;
import edu.galileo.citas.model.repository.VehiculoRepository;

class VehiculoServiceTest {

    @Test
    void findAllAndSave() {
        VehiculoRepository repo = mock(VehiculoRepository.class);
        VehiculoService svc = new VehiculoService(repo);

        when(repo.findAll()).thenReturn(List.of(new Vehiculo()));
        assertEquals(1, svc.findAll().size());

        Vehiculo v = new Vehiculo();
        when(repo.save(v)).thenReturn(v);
        assertSame(v, svc.save(v));
    }
}

