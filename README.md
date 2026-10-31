# validacao-de-dados
API RESTFul para gerenciamento de clientes, integrando manipulacao em banco de dadose tratamento rigoroso de validacao de dados de entrada
---
## Tecnologias Utilizadas 

- **Linguagem:** Java 17+
- **Framework:** Spring Boot
- **Segurança:** Spring Security , JWT (JSON Web Token), BCrypt Password Hashing
- **Persistência:** Spring Data JPA /Hibernate
- **Banco de Dados em nuven(Aiven):** A aplicação está configurada para conectar-se a uma instância gerenciada de **MySQL no Aiven Cloud**, utilizando comunicação criptografada via **SSL/TLS**. 
- **Validação:** Bean Validation (Hibernate Validator)
- **Gerenciador de Dependências:** Maven
- **Documentação:** SpringDoc OpenAPI 3 (Swagger UI)

---

## ⚙️ Arquitetura do Projeto
O Projeto segue rigorosamente o padrão de arquitetura em camadas para garantir alta testabilidade, baixo acoplamento e separação de responsabilidades:

```text
src/main/java/com/seuusuario/api/
│
├── config/         # Configurações de Segurança (SecurityConfig, SecurityFilter) e OpenAPI (Swagger)
├── controller/     # Endpoints REST (Controllers)
├── exception/      # tratamento de exceções
├── model/          # Entidades JPA (Mapeamento das tabelas do banco de dados)
  ├── dto/            # Objetos de Transferência de Dados (Request/Response DTOs)
├── repository/     # Interfaces de Acesso a Dados (Spring Data JPA)
└── service/        # Regras de Negócio e Serviços (TokenService, etc.)
 ```
## 🔒 Segurança (JWT)
A API utiliza autenticação Stateless baseada em tokens JWT.

1. O cliente envia as credenciais para /api/auth/login.

2. Se validadas, a API retorna um Token JWT assinado digitalmente.

3. As rotas protegidas (como /api/clientes) exigem que o token seja enviado no cabeçalho HTTP no formato:

```bash
HTTP

Authorization: Bearer <seu_token_aqui>

```

## Funcionalidades & EndPoints
| Metodos | Endpoint | Descricao |
| :--- | :--- | :--- |
| GET | /api/clientes | Lista todos os clientes cadastrados |
| GET | /api/clientes{id} | Busca um cliente especifico por ID |
| POST| /api/clientes | Cadastra um novo cliente com validação nos campos |
| PUT | /api/clientes/{id} | Atualiza dados de um cliente existente |
| DELETE | api/clientes/{id} | Remove um cliente do banco de dados pelo id|


## 📚 Documentação da API (Swagger)
A documentação interativa da API foi construída utilizando o SpringDoc OpenAPI 3.

Após iniciar a aplicação, você pode testar todos os endpoints diretamente pelo navegador através do Swagger UI:

## 👉 URL do Swagger: 
### http://localhost:8080/swagger-ui/index.html
---
Nota: Para testar rotas protegidas no Swagger, faça o login na rota de autenticação, copie o token gerado, clique no botão verde "Authorize" no topo da página do Swagger e cole o seu token.
---
### 🚀 Como Executar o Projeto Localmente
## Pré-requisitos
- **Java 17 ou superior instalado.**

- **Maven instalado.**

- **Acesso à internet (para conectar ao banco de dados na nuvem MySQL/Aiven) ou uma instância local do MySQL configurada.**

Passos:
1. Clone o repositório:
   ```bash
   git clone https://github.com/F-Alexandre/validacao-de-dados.git
   cd validacao-de-dados
2. Configure as credenciais do seu banco de dados no arquivo
   ### src/main/resources/application.properties:
   ```bash
   spring.datasource.url=jdbc:mysql://seu-host-aiven:porta/seu_banco
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
 
3. Execute a aplicação via Maven:
   ```bash
   Bash
   mvn spring-boot:run
4. A API estará disponível em http://localhost:8080.

---
## Regras de validação Aplicadas
- **Nome:** Obrigatório e deve conter entre 3 e 100 caracteres.
- **E-mail:** Obrigatório, deve possuir formato válido ('usuario@dominio.com') e ser único no banco de dados.
- **Telefone:** Obrigatório e deve conter apenas dígitos numéricos validos (com DDD).
---

