package edu.galileo.citas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.galileo.citas.model.Cita;
import edu.galileo.citas.model.repository.CitaRepository;

@Service
public class CitaService {
    private final CitaRepository citaRepository;

    @Autowired
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }
}
