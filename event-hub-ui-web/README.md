# Event Hub Web

Interface web moderna e responsiva para o sistema Event Hub. Desenvolvida com **Angular 17**, utiliza os recursos mais recentes do framework para oferecer uma experiência de usuário fluida e performática.

##  Stack Tecnológica

- **Angular 17**: Framework Frontend (Standalone Components).
- **PrimeNG**: Biblioteca de componentes UI (Tabelas, Modais, Calendários).
- **TypeScript**: Tipagem estática e segurança no código.
- **RxJS**: Programação reativa para integração com a API.
- **SASS (SCSS)**: Estilização modular.

## Funcionalidades

### Catálogo de Eventos (Público)
- Visualização de eventos disponíveis em formato de cards.
- Indicadores visuais de capacidade e data.
- Integração direta com o fluxo de compra.

### Administração (Área Restrita)
- **Data Table**: Grid avançado para gestão de eventos.
- **CRUD**: Formulários modais para criar e editar eventos.
- **Validação Visual**: Feedback imediato para campos obrigatórios ou inválidos.
- **Formatação**: Tratamento de datas e valores monetários seguindo o padrão brasileiro (pt-BR).

### Compra de Ingressos
- Fluxo simplificado em modal.
- Validação de e-mail e nome.
- Feedback de sucesso ou erro (ex: ingressos esgotados) via Toast notifications.

## Arquitetura e Padrões

- **Standalone Components**: O projeto não utiliza `AppModule`, adotando a abordagem modular moderna do Angular.
- **Services**: Lógica de comunicação com a API isolada em serviços (`EventoService`, `IngressoService`).
- **Interceptors**: `AuthInterceptor` injeta automaticamente o token Basic Auth nas requisições administrativas.
- **Environments**: Configuração de URL da API separada por ambiente (dev/prod).

##  Como Executar

### Pré-requisitos
- Node.js (v18 ou superior)
- Angular CLI (`npm install -g @angular/cli`)

### Instalação e Execução

1. Instale as dependências do projeto:
   ```bash
   npm install
   ```

2. Execute o servidor de desenvolvimento:
   ```bash
   ng serve
   ```

3. Acesse a aplicação:
   > **http://localhost:4200**

A aplicação tentará se conectar à API em `http://localhost:8080`. Certifique-se de que o backend esteja rodando.
