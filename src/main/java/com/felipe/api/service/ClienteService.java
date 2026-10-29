package com.felipe.api.service;


import com.felipe.api.model.Cliente;
import com.felipe.api.model.dto.ClienteRequestDTO;
import com.felipe.api.model.dto.ClienteResponseDTO;
import com.felipe.api.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private ModelMapper modelMapper;

    public ClienteService(ClienteRepository clienteRepository , ModelMapper modelMapper) {
        this.clienteRepository = clienteRepository;
        this.modelMapper = modelMapper;
    }

    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> modelMapper.map(cliente,ClienteResponseDTO.class))
                .collect(Collectors.toList());

    }

    public Optional<ClienteResponseDTO> buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class));
    }

    @Transactional
    public ClienteResponseDTO salvar(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRepository.existsByEmail(clienteRequestDTO.getEmail())) {
            throw new IllegalArgumentException("O e-mail informado ja esta cadastrado");
        }
        Cliente cliente = modelMapper.map(clienteRequestDTO, Cliente.class);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return modelMapper.map(clienteSalvo,ClienteResponseDTO.class);
    }

    @Transactional
    public Optional<ClienteResponseDTO> atualizar(Long id, ClienteRequestDTO clienteRequestDTO) {
        return clienteRepository.findById(id)
                .map(clienteExistente ->{
                    //Atualiza a entidade existente com os dados do DTO
                   modelMapper.map(clienteRequestDTO , clienteExistente);
                   Cliente atualizado = clienteRepository.save(clienteExistente);
                   return modelMapper.map(atualizado, ClienteResponseDTO.class);
                });


    }

    @Transactional
    public boolean deletar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}