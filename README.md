# 🐾 Clyvo — Sistema de Gestão Clínica Veterinária

API RESTful desenvolvida com Java e Spring Boot para gerenciamento de clínicas veterinárias.  
O sistema permite cadastrar tutores, pets e veterinários, realizar agendamentos de consultas e registrar o histórico clínico dos animais.

---

## 👥 Equipe - 2TDSPO

|         Nome        |   RM   |
|---------------------|--------|
| Bruno A Zanateli     | 563736 |
| Christian S Freitas | 566098 |
| Pedro P Biasolli    | 562521 |
| Rodrigo Tiezzi      | 562975 |
| Maheus E Souza      | 562532 |
--------------------------------

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Java       |   17   |
| Spring Boot | 4.0.6 |
| Spring Data JPA | 3.x |
| Azure SQL / SQL Server | 2022 |
| Hibernate | 6.x |
| SpringDoc OpenAPI (Swagger) | 2.8.8 |
| Bean Validation | 3.x |
| Maven | 3.3.4 |

---

## 🏗️ Arquitetura

O projeto segue a arquitetura em camadas:

```
Controller → Service → Repository → Database (Azure SQL / SQL Server)
```

```
src/
├── controllers/       # Endpoints REST
├── services/          # Regras de negócio
├── repository/        # Acesso ao banco de dados (Spring Data JPA)
├── model/             # Entidades JPA
├── enums/             # Enumerações (StatusAgendamento, UF, SufixoCrmv)
└── exceptions/        # Tratamento global de erros
```

---

## 📊 Diagrama de Classes

```mermaid
classDiagram
    direction TB

    class Pet {
        +Long id
        +String nome
        +String especie
        +String raca
        +String cor
        +Integer idade
        +Double peso
        +List~Tutor~ tutores
    }

    class Tutor {
        +Long id
        +String nome
        +Integer idade
        +String endereco
        +String tel
        +String cpf
        +List~Pet~ pets
    }

    class Veterinario {
        +Long id
        +String nome
        +String area
        +Crmv crmv
    }

    class Crmv {
        <<Embeddable>>
        +String prefixo
        +UF uf
        +Integer numeroDeInscricao
        +SufixoCrmv sufixo
    }

    class Agendamento {
        +Long id
        +LocalDateTime dataHora
        +Pet pet
        +Veterinario veterinario
        +StatusAgendamento status
        +String motivo
        +Consulta consulta
    }

    class Consulta {
        +Long id
        +Agendamento agendamento
        +LocalDateTime dataRealizacao
        +String diagnostico
        +String tratamento
        +String observacoes
    }

    class StatusAgendamento {
        <<enumeration>>
        AGENDADO
        REALIZADO
        CANCELADO
    }

    class UF {
        <<enumeration>>
        AC, AL, AM, BA, CE
        DF, ES, GO, MA, MG
        MS, MT, PA, PB, PE
        PI, PR, RJ, RN, RO
        RR, RS, SC, SE, SP, TO
    }

    class SufixoCrmv {
        <<enumeration>>
        MV
        ZT
    }

    Pet "N" <--> "N" Tutor : TB_PET_TUTOR
    Agendamento "N" --> "1" Pet : id_pet
    Agendamento "N" --> "1" Veterinario : id_vet
    Consulta "1" --> "1" Agendamento : id_agendamento
    Veterinario "1" *-- "1" Crmv : embedded
    Agendamento --> StatusAgendamento
    Crmv --> UF
    Crmv --> SufixoCrmv
```

---

## 🗄️ DER — Diagrama Entidade-Relacionamento

```mermaid
erDiagram
    TB_TUTOR {
        NUMBER ID_TUTOR PK
        VARCHAR NOME
        NUMBER IDADE
        VARCHAR ENDERECO
        VARCHAR NUM_TELEFONE
        VARCHAR CPF UK
    }

    TB_PET {
        NUMBER ID_PET PK
        VARCHAR NOME
        VARCHAR ESPECIE
        VARCHAR RACA
        VARCHAR COR
        NUMBER IDADE
        NUMBER PESO
    }

    TB_PET_TUTOR {
        NUMBER ID_PET FK
        NUMBER ID_TUTOR FK
    }

    TB_VETERINARIO {
        NUMBER ID_VET PK
        VARCHAR NOME
        VARCHAR AREA
        VARCHAR CRMV_PREFIXO
        VARCHAR CRMV_UF
        NUMBER CRMV_NUMINSCRICAO
        VARCHAR CRMV_SUFIXO
    }

    TB_AGENDAMENTO {
        NUMBER ID_AGENDAMENTO PK
        TIMESTAMP DT_HORA
        NUMBER ID_PET FK
        NUMBER ID_VET FK
        VARCHAR STATUS
        VARCHAR MOTIVO
    }

    TB_CONSULTA {
        NUMBER ID_CONSULTA PK
        NUMBER ID_AGENDAMENTO FK
        TIMESTAMP DT_REALIZACAO
        VARCHAR DIAGNOSTICO
        VARCHAR TRATAMENTO
        VARCHAR OBSERVACOES
    }

    TB_TUTOR ||--o{ TB_PET_TUTOR : "possui"
    TB_PET   ||--o{ TB_PET_TUTOR : "pertence a"
    TB_PET         }o--|| TB_AGENDAMENTO : "é agendado em"
    TB_VETERINARIO }o--|| TB_AGENDAMENTO : "atende em"
    TB_AGENDAMENTO ||--o| TB_CONSULTA    : "gera"
```

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

- Java 17+
- Maven 3.x
- Um banco SQL Server (local via `docker compose` ou Azure SQL Database)

### Configuração

Nenhuma credencial fica no código. O `application.properties` lê tudo de variáveis de ambiente:

| Variável | Obrigatória | Descrição |
|----------|-------------|-----------|
| `SPRING_DATASOURCE_URL` | Sim | URL JDBC do SQL Server |
| `SPRING_DATASOURCE_USERNAME` | Sim | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Sim | Senha do banco |
| `JWT_SECRET` | Não | Chave de assinatura do JWT (mínimo 32 caracteres). Tem valor padrão; em produção, defina a sua |
| `JWT_EXPIRATION_MS` | Não | Expiração do token (padrão 3600000) |
| `ADMIN_USERNAME` / `ADMIN_PASSWORD` | Não | Login do admin (padrão `admin` / `admin123`) |
| `VET_USERNAME` / `VET_PASSWORD` | Não | Login do veterinário (padrão `veterinario` / `vet123`) |

Exemplo da URL de conexão (Azure SQL):

```
jdbc:sqlserver://<SERVIDOR>.database.windows.net:1433;database=<BANCO>;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;
```

As tabelas são criadas automaticamente pelo Hibernate (`ddl-auto=update`). O arquivo `challengeClyvo/script.sql` é apenas uma referência do schema.

### Executando

```bash
# Clone o repositório
git clone https://github.com/XXXXXX/challengeClyvo.git

# Entre na pasta do projeto
cd challengeClyvo

# Execute com Maven
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080` e `https://java-challenge-fiap-2026.onrender.com/web/login`

---

## ☁️ Deploy na Azure (GitHub Actions)

O workflow `.github/workflows/master_webapp-clyvo-care.yml` compila o projeto e publica o JAR no Azure Web App a cada push na `master`.

Cadastre em **Settings → Secrets and variables → Actions → New repository secret**:

| Secret | Valor |
|--------|-------|
| `SPRING_DATASOURCE_URL` | URL JDBC do Azure SQL |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco |
| `AzureAppService_PublishProfile_...` | Criado automaticamente pelo `az webapp deployment github-actions add` ou pelo Deployment Center |

> **Importante:** os GitHub Secrets são usados no *build/teste* do pipeline. Para a aplicação **rodando** no Azure, configure as mesmas variáveis (`SPRING_DATASOURCE_*` e, idealmente, `JWT_SECRET`, `ADMIN_PASSWORD` e `VET_PASSWORD`) em **Web App → Settings → Environment variables**.

---

## 📖 Documentação da API

Com a aplicação rodando, acesse o Swagger UI:

```
http://localhost:8080/swagger-ui.html
```

---

## 📋 Endpoints

### 👤 Tutor `/tutor`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/tutor` | Lista todos os tutores |
| GET | `/tutor/{id}` | Busca tutor por ID |
| GET | `/tutor/nome/{nome}` | Busca tutor por nome |
| GET | `/tutor/cpf/{cpf}` | Busca tutor por CPF |
| POST | `/tutor` | Cadastra novo tutor |
| PUT | `/tutor/{id}` | Atualiza tutor |
| DELETE | `/tutor/{id}` | Remove tutor |

### 🐾 Pet `/pet`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/pet` | Lista todos os pets |
| GET | `/pet/{id}` | Busca pet por ID |
| GET | `/pet/nome/{nome}` | Busca pet por nome |
| GET | `/pet/especie/{especie}` | Busca pet por espécie |
| GET | `/pet/tutor/{idTutor}` | Lista pets de um tutor |
| POST | `/pet` | Cadastra novo pet |
| POST | `/pet/{idPet}/tutor/{idTutor}` | Vincula tutor ao pet |
| PUT | `/pet/{id}` | Atualiza pet |
| DELETE | `/pet/{id}` | Remove pet |
| DELETE | `/pet/{idPet}/tutor/{idTutor}` | Desvincula tutor do pet |

### 🩺 Veterinário `/veterinario`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/veterinario` | Lista todos os veterinários |
| GET | `/veterinario/{id}` | Busca veterinário por ID |
| GET | `/veterinario/area/{area}` | Busca por área de atuação |
| GET | `/veterinario/crmv/{numero}` | Busca por número CRMV |
| GET | `/veterinario/uf/{uf}` | Busca por UF do CRMV |
| POST | `/veterinario` | Cadastra novo veterinário |
| PUT | `/veterinario/{id}` | Atualiza veterinário |
| DELETE | `/veterinario/{id}` | Remove veterinário |

### 📅 Agendamento `/agendamento`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/agendamento` | Lista todos os agendamentos |
| GET | `/agendamento/{id}` | Busca agendamento por ID |
| GET | `/agendamento/status/{status}` | Busca por status (AGENDADO, REALIZADO, CANCELADO) |
| GET | `/agendamento/veterinario/{idVet}` | Lista agendamentos de um veterinário |
| GET | `/agendamento/pet/{idPet}` | Lista agendamentos de um pet |
| POST | `/agendamento` | Cria novo agendamento |
| PATCH | `/agendamento/{id}/cancelar` | Cancela um agendamento |

### 🏥 Consulta `/consulta`

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/consulta` | Lista todas as consultas |
| GET | `/consulta/{id}` | Busca consulta por ID |
| GET | `/consulta/pet/{idPet}` | Lista consultas de um pet |
| GET | `/consulta/veterinario/{idVet}` | Lista consultas de um veterinário |
| POST | `/consulta/agendamento/{idAgendamento}` | Realiza consulta de um agendamento |
| PATCH | `/consulta/{id}/observacoes` | Atualiza observações da consulta |

---

## 📁 Estrutura de Documentação

```
docs/
├── APIClyvoRequisicoes_postman_collection.json   # Coleção Postman com todos os endpoints
├── diagrama-classes.mermaid                       # Diagrama de Classes
└── der.mermaid                                    # Diagrama Entidade-Relacionamento
```

---

## ✅ Requisitos Atendidos

- [x] Persistência de dados com Azure SQL Database (SQL Server)
- [x] Relacionamentos JPA (`@ManyToMany`, `@ManyToOne`, `@OneToOne`, `@Embeddable`)
- [x] Bean Validation (`@NotNull`, `@NotBlank`, `@Future`, `@Positive`, etc.)
- [x] Paginação e ordenação de resultados
- [x] Busca com parâmetros
- [x] Tratamento global de exceções
- [x] Documentação com Swagger (SpringDoc)
- [x] Spring JPA Query Methods e JPQL
- [x] Regras de negócio além do CRUD
- [x] Exportação de endpoints (Postman)
