# 🛒 Commerce - Arquitetura Hexagonal com Spring Boot

Este projeto é uma refatoração para a **Arquitetura Hexagonal**, utilizando **Spring Boot** para construir um sistema de comércio eletrônico flexível e escalável.
O projeto nasceu com próposito de exercitar arquitetura hexagonal.

## 🚀 Tecnologias Utilizadas

- **Java 17**  
- **Spring Boot 3.2.3**  
- **Spring Web**  
- **Spring Data JPA**  
- **Spring Validation**  
- **Flyway** (Migração de banco de dados)  
- **MapStruct** (Mapeamento de DTOs)  
- **SpringDoc OpenAPI** (Documentação da API)  
- **MySQL**  
- **Testes com JUnit, RestAssured e Jacoco**  
- **SonarQube** (Qualidade de código)  

## 📥 Instalação e Execução

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/commerce.git
   ```

2. Configure o banco de dados MySQL no `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/commerce
   spring.datasource.username=root
   spring.datasource.password=senha
   ```

3. Execute as migrações do Flyway:
   ```sh
   mvn flyway:migrate
   ```

4. Inicie a aplicação:
   ```sh
   mvn spring-boot:run
   ```

## 🛠 Estrutura do Projeto

- `/src/main/java/com/api/hexagonal/commerce` → Código principal do microserviço  
- `/src/test/java/com/api/hexagonal/commerce` → Testes unitários e de integração  
- `/pom.xml` → Configuração do projeto e dependências  

## 📌 Funcionalidades

- 📦 **Arquitetura Hexagonal** (Separa lógica de negócios das interfaces externas)  
- 🛠 **Banco de dados relacional** com MySQL e Flyway  
- 📑 **Documentação da API** com SpringDoc OpenAPI  
- 🔍 **Testes automatizados** cobrindo REST API e regras de negócio  
- 📊 **Cobertura de código** com Jacoco e análise de qualidade com SonarQube  

## 🧪 Testes

Para rodar os testes automatizados:
```sh
mvn test
```

Para gerar relatório de cobertura de código:
```sh
mvn jacoco:report
```
