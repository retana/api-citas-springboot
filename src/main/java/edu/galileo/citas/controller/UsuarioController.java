package edu.galileo.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.galileo.citas.model.Usuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import edu.galileo.citas.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @PostMapping
    public Usuario save(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }
}
