package com.medpharm.service;

import com.medpharm.dto.*;
import com.medpharm.model.Usuario;
import com.medpharm.repository.UsuarioRepository;
import com.medpharm.security.JwtUtils;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarios;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarios, JwtUtils jwtUtils) {
        this.authenticationManager=authenticationManager; this.usuarios=usuarios; this.jwtUtils=jwtUtils;
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        Usuario u = usuarios.findByUsername(request.username()).orElseThrow();
        return new AuthResponseDTO(jwtUtils.generateToken(u.getUsername()), u.getUsername(), u.getRol());
    }
}
