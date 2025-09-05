package edu.galileo.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.galileo.citas.model.Cita;
import edu.galileo.citas.model.repository.CitaRepository;
import edu.galileo.citas.service.CitaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/citas")
@SecurityRequirement(name = "bearerAuth")
public class CitaController {

    private final CitaService citaService;
    private final CitaRepository citaRepository;

    @Autowired
    public CitaController(CitaService citaService, CitaRepository citaRepository) {
        this.citaService = citaService;
        this.citaRepository = citaRepository;
    }

    @GetMapping
    public List<Cita> findAll() { return citaService.findAll(); }

    @PostMapping
    public Cita save(@RequestBody Cita c) { return citaService.save(c); }

    @GetMapping("/{id}")
    public Cita getById(@PathVariable Integer id) { return citaRepository.findById(id).orElseThrow(); }

    @PutMapping("/{id}")
    public Cita update(@PathVariable Integer id, @RequestBody Cita c) {
        Cita current = citaRepository.findById(id).orElseThrow();
        current.setFecha(c.getFecha());
        current.setHora(c.getHora());
        current.setComentario(c.getComentario());
        current.setMotivo(c.getMotivo());
        current.setTaller(c.getTaller());
        current.setEstado(c.getEstado());
        current.setVehiculo(c.getVehiculo());
        return citaRepository.save(current);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { citaRepository.deleteById(id); }
}
