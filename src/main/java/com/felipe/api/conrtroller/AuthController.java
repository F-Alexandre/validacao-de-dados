package com.felipe.api.conrtroller;


import com.felipe.api.model.dto.Usuarios;
import com.felipe.api.repository.UsuarioRepository;
import com.felipe.api.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;



    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuarios usuario){
        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", "E-mail ja foi cadastrado!"));
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuarios novoUsuario= usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem" , "Usuario criado com sucesso!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuarios usuario) {
       var usuariosOpt = usuarioRepository.findByEmail(usuario.getEmail());
       if(usuariosOpt.isPresent() && passwordEncoder.matches(usuario.getSenha() , usuariosOpt.get().getSenha())){
           String token  = tokenService.gerarToken(usuariosOpt.get());
           return ResponseEntity.ok(Map.of("token", token));
       }
       return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro" , "E-mail ou senha invalidos"));
    }

}

