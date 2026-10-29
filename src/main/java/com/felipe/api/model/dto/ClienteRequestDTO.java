package com.felipe.api.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO {

    @NotBlank(message = "O Nome é obrigatorio")
    @Size(min =3 , max = 100 , message = "O nome deve ter entre 3 e 100 caracter")
    @Column(nullable = false , length = 100)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatorio")
    @Email(message = "Forneça um endereço de e-mail válido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}",message = "O telefone deve apenas números com DDD(10 ou 11 dígitos)")
    @Column(nullable = false ,unique = true , length = 100)
    private String telefone;
}
