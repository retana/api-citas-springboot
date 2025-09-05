package edu.galileo.citas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.galileo.citas.model.Vehiculo;
import edu.galileo.citas.model.repository.VehiculoRepository;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }
}
