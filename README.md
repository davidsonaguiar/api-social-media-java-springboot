## Rede Social API

**Projeto:** Rede Social API

**Descrição:**

Esta API RESTful foi desenvolvida com Spring Boot e segue os conceitos de microserviços para gerenciar usuários e posts em uma plataforma de mídia social. A aplicação implementa boas práticas e padrões de projeto recomendados para APIs REST.

**Funcionalidades:**

* **Gestão de Usuários:**
    * Criação de usuários
    * Consulta de todos os usuários
    * Consulta de um usuário específico por ID
    * Atualização de informações de usuários
    * Exclusão de usuários
* **Gestão de Posts:**
    * Criação de posts associados a um usuário
    * Consulta de todos os posts
    * Consulta de posts específicos por ID
    * Consulta de todos os posts de um usuário específico
    * Atualização de posts
    * Exclusão de posts
* **Tratamento de Exceções:**
    * Exceções personalizadas para recursos não encontrados e lista vazia
    * Tratamento genérico de exceções
    * Validação de dados de entrada com Jakarta Validation
* **HATEOAS:**
    * Implementação de HATEOAS para recursos, fornecendo links para ações relacionadas

**Ferramentas Utilizadas:**

* Linguagem de Programação: Java
* Framework: Spring Boot
* Banco de Dados: (A confirmar - verifique qual banco de dados você está utilizando)
* Validação: Jakarta Validation
* Documentação (a ser implementado): Swagger
* Monitoramento (a ser implementado): Spring Boot Actuator
* Segurança (a ser implementado): Spring Security

**Arquitetura e Design:**

A API segue uma arquitetura de microserviços, onde cada funcionalidade é encapsulada em um serviço independente. O projeto utiliza JPA para acesso ao banco de dados e implementa HATEOAS para fornecer links de navegação entre recursos relacionados.

**Endpoints:**

**Usuários:**

* **POST /users**: Cria um novo usuário.
* **GET /users**: Recupera uma lista de todos os usuários.
* **GET /users/{id}**: Recupera um usuário específico pelo ID.
* **PUT /users/{id}**: Atualiza um usuário existente.
* **DELETE /users/{id}**: Exclui um usuário.

**Posts:**

* **POST /users/{userId}/posts**: Cria um novo post associado a um usuário específico.
* **GET /posts**: Recupera uma lista de todos os posts.
* **GET /posts/{id}**: Recupera um post específico pelo ID.
* **GET /users/{userId}/posts**: Recupera todos os posts de um usuário específico.
* **PUT /posts/{id}**: Atualiza um post existente.
* **DELETE /posts/{id}**: Exclui um post.

**Documentação (a ser implementada):**

A documentação da API será integrada posteriormente utilizando o Swagger. A documentação detalhará todos os endpoints disponíveis, parâmetros aceitos e os objetos de resposta esperados.

**Monitoramento (a ser implementado):**

O Spring Boot Actuator será utilizado para monitorar o estado da API, fornecendo métricas e endpoints de health check.

**Segurança (a ser implementada):**

A autenticação e autorização serão implementadas posteriormente utilizando o Spring Security. O Spring Security permitirá controlar o acesso aos recursos da API e garantir a segurança do sistema.
