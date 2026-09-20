# API de Pedidos — Trabalho 1

Disciplina: Desenvolvimento de Sistemas Distribuídos
Projeto incremental: **API de Pedidos**

## Integrantes do grupo

- Nome: Gustavo Alves Oliveira; RA: N080AJ5
- Nome: Guilherme Dias da Silva; RA: G863DJ0
- Nome: Isabella Victorino dos Santos; RA: N094419
- Nome: Guilherme Rodrigues de Freitas; RA: G804767

> *(substituir pelos nomes e RAs reais do grupo antes da entrega)*

## Arquitetura

- **Cliente → API de Pedidos → PostgreSQL**
- A aplicação (Spring Boot) e o banco (PostgreSQL) executam em containers separados.
- Comunicação cliente–API: HTTP/JSON.
- Comunicação aplicação–banco: protocolo nativo do PostgreSQL (via JDBC).

### Camadas internas da aplicação

| Camada | Pacote | Responsabilidade |
|---|---|---|
| API / Controller | `com.unip.pedidos.api` | Recebe requisições HTTP e produz respostas |
| Service | `com.unip.pedidos.service` | Lógica de negócio, cálculo de `valor_total`, coordenação |
| Repository | `com.unip.pedidos.repository` | Persistência (Spring Data JPA) |
| Model | `com.unip.pedidos.model` | Entidade `Pedido` e enum `StatusPedido` |
| DTO | `com.unip.pedidos.dto` | Objetos de entrada/saída da API |

As três camadas (API, Service, Repository) rodam no mesmo processo/container (`pedidos`).
O PostgreSQL roda em outro container (`postgres`), pois é outro processo/componente.

## Modelo de Pedido

| Campo | Descrição |
|---|---|
| `id` | Identificador do pedido |
| `cliente` | Identificação textual do cliente |
| `produto` | Identificação textual do produto |
| `quantidade` | Quantidade solicitada |
| `valorUnitario` | Preço de uma unidade |
| `valorTotal` | Calculado pela aplicação (`quantidade * valorUnitario`) |
| `status` | `CRIADO`, `CONFIRMADO` ou `CANCELADO` |
| `dataCriacao` | Instante em que o pedido foi registrado |

## Endpoints

| Método | Caminho | Descrição |
|---|---|---|
| `POST` | `/pedidos` | Cria um pedido. Entrada: `cliente`, `produto`, `quantidade`, `valorUnitario`. Retorna `201 Created`. |
| `GET` | `/pedidos/{id}` | Consulta um pedido. Retorna `200 OK` ou `404 Not Found`. |
| `GET` | `/pedidos` | Lista todos os pedidos (sem paginação nesta versão). |
| `PATCH` | `/pedidos/{id}/status` | Atualiza apenas o status do pedido. |
| `GET` | `/health` | Retorna `{ "status": "ok" }`. Não é funcionalidade de negócio. |

### Exemplo — criar pedido

```bash
curl -X POST http://localhost:8000/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "cliente": "Maria Silva",
    "produto": "Teclado mecânico",
    "quantidade": 2,
    "valorUnitario": 250.00
  }'
```

Resposta esperada (`201 Created`):

```json
{
  "id": 1,
  "cliente": "Maria Silva",
  "produto": "Teclado mecânico",
  "quantidade": 2,
  "valorUnitario": 250.00,
  "valorTotal": 500.00,
  "status": "CRIADO",
  "dataCriacao": "2026-09-19T12:00:00"
}
```

### Exemplo — alterar status

```bash
curl -X PATCH http://localhost:8000/pedidos/1/status \
  -H "Content-Type: application/json" \
  -d '{ "status": "CONFIRMADO" }'
```

## Tecnologias

- Java 17
- Spring Boot 4.0.8 (Web, Data JPA, Validation)
- PostgreSQL 16
- Docker e Docker Compose
- Maven

## Configuração por variáveis de ambiente

Nenhuma credencial de banco fica fixa no código. A aplicação lê:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Essas variáveis são definidas automaticamente pelo `docker-compose.yml`, a partir de
`POSTGRES_DB`, `POSTGRES_USER` e `POSTGRES_PASSWORD` (ver `.env.example`).

## Como executar

Pré-requisito: Docker e Docker Compose instalados. Nenhuma outra dependência local é necessária —
o Maven e o JDK são usados apenas dentro da imagem de build (multi-stage Dockerfile).

```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_REPOSITORIO>
git checkout APIPedidos-1-final
docker compose up -d --build
```

A API ficará disponível em: **http://localhost:8000**

Para verificar se subiu corretamente:

```bash
curl http://localhost:8000/health
```

## Persistência

Os dados do PostgreSQL ficam em um volume Docker nomeado (`pedidos-db-data`), então:

- Os pedidos sobrevivem a um `docker compose restart pedidos` ou a um reinício apenas do container da aplicação.
- Os dados só são perdidos se o volume for removido explicitamente (`docker compose down -v`).

### Experimento sugerido (persistência)

```bash
# 1. Criar um pedido
curl -X POST http://localhost:8000/pedidos -H "Content-Type: application/json" \
  -d '{"cliente":"Teste","produto":"Item","quantidade":1,"valorUnitario":10.0}'

# 2. Reiniciar somente o container da aplicação
docker compose restart pedidos

# 3. Consultar novamente o mesmo pedido — o dado permanece,
#    pois está armazenado no container "postgres" e no volume "pedidos-db-data",
#    não no container da aplicação.
curl http://localhost:8000/pedidos/1
```

## Estrutura do projeto

```
/
├── src/main/java/com/unip/pedidos/
│   ├── PedidosApplication.java
│   ├── api/            # Controllers (camada de API)
│   ├── service/         # Regras de negócio (camada Service)
│   ├── repository/      # Spring Data JPA (camada Repository)
│   ├── model/           # Entidade Pedido e enum StatusPedido
│   ├── dto/             # Objetos de entrada/saída da API
│   └── exception/        # Tratamento de erros (404, validação)
├── src/main/resources/application.yml
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── .env.example
└── README.md
```
