package com.felipe.api.config;

import com.felipe.api.repository.UsuarioRepository;
import com.felipe.api.service.TokenService;
import jakarta.servlet.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            String path =request.getRequestURI();
            if(path.startsWith("/api/auth")){
                filterChain.doFilter(request,response);
                return;
            }


            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer")) {
                String token = authHeader.replace("Bearer", "");
                String email = tokenService.validarToken(token);
                if (email != null) {
                    usuarioRepository.findByEmail(email).ifPresent(usuarios -> {
                        System.out.println("3" + usuarios.getEmail());
                                UserDetails userDatails = new User(usuarios.getEmail(), usuarios.getSenha(), Collections.emptyList());

                                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDatails, null, userDatails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            });
                }
              }
            filterChain.doFilter(request,response);
    }
}
