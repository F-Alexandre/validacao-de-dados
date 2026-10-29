package com.felipe.api.conrtroller;

import com.felipe.api.model.Cliente;
import com.felipe.api.repository.ClienteRepository;
import com.felipe.api.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Cliente cliente) {
       try{
           Cliente novoCliente = clienteService.salvar(cliente);
           return  ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
       }catch (IllegalArgumentException e ){
           return  ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro" , e.getMessage()));
       }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody Cliente clienteAtualizado) {
        return clienteService.atualizar(id, clienteAtualizado)
                .map(clienteExistente -> {
                   clienteExistente.setNome(clienteAtualizado.getNome());
                   clienteExistente.setEmail(clienteAtualizado.getEmail());
                   clienteExistente.setTelefone(clienteAtualizado.getTelefone());

                   Cliente atualizando = clienteService.salvar(clienteExistente);
                   return  ResponseEntity.ok(atualizando);
                }).orElse(ResponseEntity.notFound().build());


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if(!clienteService.deletar(id)){
            return ResponseEntity.notFound().build();
        }
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();

 }
}
