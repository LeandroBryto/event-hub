# Event Hub API

API REST responsável por toda a lógica de negócio do ecossistema Event Hub. Desenvolvida com **Java 17** e **Spring Boot 3**, esta aplicação foca em escalabilidade, segurança e consistência de dados.

##  Stack Tecnológica

- **Java 17**: Linguagem base (LTS).
- **Spring Boot 3.3.2**: Framework principal.
- **Spring Data JPA / Hibernate**: Persistência de dados.
- **PostgreSQL**: Banco de dados relacional (Produção).
- **H2 Database**: Banco em memória (Testes).
- **Spring Security**: Autenticação e Autorização.
- **Spring Cache**: Otimização de performance.
- **Lombok**: Redução de boilerplate code.
- **Docker**: Containerização.
- **JUnit 5 & Mockito**: Testes unitários.

##  Principais Funcionalidades

### Gestão de Eventos
- CRUD completo de eventos (Listagem, Cadastro, Atualização, Remoção).
- **Cache**: Listagem de eventos otimizada com `@Cacheable` para reduzir carga no banco.
- **Validação**: Bean Validation (`@NotBlank`, `@NotNull`, `@Future`) garante integridade dos dados de entrada.

### Venda de Ingressos & Concorrência
- **Optimistic Locking**: Implementado com `@Version` na entidade `Evento`. Isso garante que, em cenários de alta concorrência, não ocorra "overbooking" (venda de ingressos além da capacidade). Se dois usuários tentarem comprar o último ingresso simultaneamente, apenas um terá sucesso e o outro receberá um feedback apropriado (HTTP 409).
- **Transacionalidade**: Operações de venda são atômicas (`@Transactional`).
- **Geração de Tickets**: Criação automática de identificadores únicos (UUID) para cada ingresso.

### Segurança
- **Basic Auth**: Autenticação stateless simples e eficaz para o escopo do projeto.
- **Roles**:
  - `ADMIN`: Permissão total (Criar eventos, gerenciar sistema).
  - `USER`: Permissão de leitura e compra de ingressos.

##  Como Executar

### Via Docker (Recomendado)
A maneira mais simples de rodar a API junto com o banco de dados.
```bash
docker-compose up --build
```

### Manualmente (Local)
Requer PostgreSQL rodando localmente na porta 5432.
```bash
# Configurar variáveis de ambiente ou ajustar application.properties se necessário
mvn spring-boot:run
```

##  Testes
O projeto possui cobertura de testes unitários para os serviços críticos.
```bash
mvn test
```

##  Documentação da API
Principais endpoints disponíveis (Prefixo base: `/v1`):

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| GET | `/eventos` | Lista todos os eventos ativos (Cacheado) | Não |
| GET | `/eventos/{id}` | Busca detalhes de um evento por ID | Não |
| POST | `/eventos` | Cria um novo evento | Sim (Admin) |
| PUT | `/eventos/{id}` | Atualiza dados de um evento | Sim (Admin) |
| DELETE | `/eventos/{id}` | Remove um evento do sistema | Sim (Admin) |
| POST | `/ingressos/compra` | Realiza a compra de um ingresso | Sim (User/Admin) |
| GET | `/ingressos/participante/{email}` | Histórico de compras por email | Sim (User/Admin) |
