# validacao-de-dados
API RESTFul para gerenciamento de clientes, integrando manipulacao em banco de dadose tratamento rigoroso de validacao de dados de entrada
---
## Tecnologias Utilizadas 

- **Linguagem:** Java 17+
- **Framework:** Spring Boot
- **Persistência:** Spring Data JPA / JPA
- **Banco de Dados em nuven(Aiven):** A aplicação está configurada para conectar-se a uma instância gerenciada de **MySQL no Aiven Cloud**, utilizando comunicação criptografada via **SSL/TLS**. 
- **Validação:** Bean Validation (Hibernate Validator)
- **Gerenciador de Dependências:** Maven

---

## Funcionalidades & EndPoints
| Metodos | Endpoint | Descricao |
| :--- | :--- | :--- |
| GET | /api/clientes | Lista todos os clientes cadastrados |
| GET | /api/clientes{id} | Busca um cliente especifico por ID |
| POST| /api/clientes | Cadastra um novo cliente com validação nos campos |
| PUT | /api/clientes/{id} | Atualiza dados de um cliente existente |
| DELETE | api/clientes/{id} | Remove um cliente do banco de dados pelo id|

---
## Regras de validação Aplicadas
- **Nome:** Obrigatório e deve conter entre 3 e 100 caracteres.
- **E-mail:** Obrigatório, deve possuir formato válido ('usuario@dominio.com') e ser único no banco de dados.
- **Telefone:** Obrigatório e deve conter apenas dígitos numéricos validos (com DDD).
---

