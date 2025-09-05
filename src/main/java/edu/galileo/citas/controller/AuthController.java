package edu.galileo.citas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.galileo.citas.dto.AuthRequest;
import edu.galileo.citas.dto.AuthResponse;
import edu.galileo.citas.dto.RegisterRequest;
import edu.galileo.citas.model.Usuario;
import edu.galileo.citas.model.repository.UsuarioRepository;
import edu.galileo.citas.security.JwtService;
import edu.galileo.citas.service.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtService.generateToken(auth.getName());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().build();
        }
        Usuario u = new Usuario();
        u.setNombre(request.getNombre());
        u.setUsername(request.getUsername());
        u.setPassword(request.getPassword());
        u.setRol("USER");
        return ResponseEntity.ok(usuarioService.save(u));
    }
}
