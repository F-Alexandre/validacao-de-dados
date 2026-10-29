package com.felipe.api.service;


import com.felipe.api.model.Cliente;
import com.felipe.api.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;


    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("O e-mail informado ja esta cadastrado");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Optional<Cliente> atualizar(Long id, Cliente dadosAtualizado) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNome(dadosAtualizado.getNome());
                    clienteExistente.setEmail(dadosAtualizado.getEmail());
                    clienteExistente.setTelefone(dadosAtualizado.getTelefone());
                    return clienteRepository.save(clienteExistente);
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