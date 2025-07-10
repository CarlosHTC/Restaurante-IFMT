# Sistema de Gestão de Restaurante

Este é um projeto Spring Boot completo para gestão de vendas e entregas de refeições de um restaurante.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- Spring HATEOAS
- H2 Database (em memória)
- Maven
- Lombok
- ModelMapper
- Apache Commons Lang3

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── ifmt/cba/restaurante/
│   │       ├── RestauranteApplication.java
│   │       ├── config/
│   │       │   ├── CorsConfig.java
│   │       │   └── ModelMapperConfig.java
│   │       ├── controller/
│   │       │   ├── BairroController.java
│   │       │   ├── ClienteController.java
│   │       │   ├── GlobalExceptionHandler.java
│   │       │   ├── GrupoAlimentarController.java
│   │       │   ├── PedidoController.java
│   │       │   ├── PreparoProdutoController.java
│   │       │   ├── ProdutoController.java
│   │       │   ├── RegistroEstoqueController.java
│   │       │   └── TipoPreparoController.java
│   │       ├── dto/
│   │       │   ├── BairroDTO.java
│   │       │   ├── CardapioDTO.java
│   │       │   ├── ClienteDTO.java
│   │       │   ├── ColaboradorDTO.java
│   │       │   ├── EntregadorDTO.java
│   │       │   ├── EstadoPedidoDTO.java
│   │       │   ├── GrupoAlimentarDTO.java
│   │       │   ├── ItemPedidoDTO.java
│   │       │   ├── MovimentoEstoqueDTO.java
│   │       │   ├── PedidoDTO.java
│   │       │   ├── PreparoProdutoDTO.java
│   │       │   ├── ProdutoDTO.java
│   │       │   ├── RegistroEstoqueDTO.java
│   │       │   └── TipoPreparoDTO.java
│   │       ├── entity/
│   │       │   ├── Bairro.java
│   │       │   ├── Cardapio.java
│   │       │   ├── Cliente.java
│   │       │   ├── Colaborador.java
│   │       │   ├── Entregador.java
│   │       │   ├── EstadoPedido.java
│   │       │   ├── GrupoAlimentar.java
│   │       │   ├── ItemPedido.java
│   │       │   ├── MovimentoEstoque.java
│   │       │   ├── Pedido.java
│   │       │   ├── PreparoProduto.java
│   │       │   ├── Produto.java
│   │       │   ├── RegistroEstoque.java
│   │       │   └── TipoPreparo.java
│   │       ├── exception/
│   │       │   ├── ExceptionResponse.java
│   │       │   ├── NotFoundException.java
│   │       │   └── NotValidDataException.java
│   │       ├── negocio/
│   │       │   ├── BairroNegocio.java
│   │       │   ├── CardapioNegocio.java
│   │       │   ├── ClienteNegocio.java
│   │       │   ├── ColaboradorNegocio.java
│   │       │   ├── EntregadorNegocio.java
│   │       │   ├── GrupoAlimentarNegocio.java
│   │       │   ├── PedidoNegocio.java
│   │       │   ├── PreparoProdutoNegocio.java
│   │       │   ├── ProdutoNegocio.java
│   │       │   ├── RegistroEstoqueNegocio.java
│   │       │   └── TipoPreparoNegocio.java
│   │       └── repository/
│   │           ├── BairroRepository.java
│   │           ├── CardapioRepository.java
│   │           ├── ClienteRepository.java
│   │           ├── ColaboradorRepository.java
│   │           ├── EntregadorRepository.java
│   │           ├── GrupoAlimentarRepository.java
│   │           ├── ItemPedidoRepository.java
│   │           ├── PedidoRepository.java
│   │           ├── PreparoProdutoRepository.java
│   │           ├── ProdutoRepository.java
│   │           ├── RegistroEstoqueRepository.java
│   │           └── TipoPreparoRepository.java
│   └── resources/
│       └── application.properties
```

## Funcionalidades Implementadas

### Entidades Principais
- **Bairro**: Gestão de bairros com custos de entrega
- **Cliente**: Cadastro de clientes com endereços
- **Colaborador**: Gestão de colaboradores internos
- **Entregador**: Cadastro de entregadores
- **GrupoAlimentar**: Categorização de produtos alimentares
- **Produto**: Gestão de produtos em estoque
- **TipoPreparo**: Tipos de preparo de alimentos
- **PreparoProduto**: Variações de preparo para produtos
- **Cardapio**: Composição de cardápios diários
- **Pedido**: Gestão completa de pedidos
- **ItemPedido**: Itens individuais dos pedidos
- **RegistroEstoque**: Controle de movimentações de estoque

### APIs REST Disponíveis

#### Bairros
- `GET /bairro` - Lista todos os bairros
- `GET /bairro/codigo/{codigo}` - Busca bairro por código
- `GET /bairro/nome/{nome}` - Busca bairro por nome
- `POST /bairro` - Cria novo bairro
- `PUT /bairro` - Atualiza bairro
- `DELETE /bairro/{codigo}` - Remove bairro

#### Clientes
- `GET /cliente` - Lista todos os clientes
- `GET /cliente/codigo/{codigo}` - Busca cliente por código
- `GET /cliente/nome/{nome}` - Busca cliente por nome
- `GET /cliente/cpf/{cpf}` - Busca cliente por CPF
- `POST /cliente` - Cria novo cliente
- `PUT /cliente` - Atualiza cliente
- `DELETE /cliente/{codigo}` - Remove cliente

#### Grupos Alimentares
- `GET /grupo-alimentar` - Lista todos os grupos
- `GET /grupo-alimentar/codigo/{codigo}` - Busca grupo por código
- `GET /grupo-alimentar/nome/{nome}` - Busca grupo por nome
- `POST /grupo-alimentar` - Cria novo grupo
- `PUT /grupo-alimentar` - Atualiza grupo
- `DELETE /grupo-alimentar/{codigo}` - Remove grupo

#### Produtos
- `GET /produto` - Lista todos os produtos
- `GET /produto/codigo/{codigo}` - Busca produto por código
- `GET /produto/nome/{nome}` - Busca produto por nome
- `GET /produto/estoque-minimo` - Lista produtos abaixo do estoque mínimo
- `POST /produto` - Cria novo produto
- `PUT /produto` - Atualiza produto
- `DELETE /produto/{codigo}` - Remove produto

#### Tipos de Preparo
- `GET /tipo-preparo` - Lista todos os tipos
- `GET /tipo-preparo/codigo/{codigo}` - Busca tipo por código
- `GET /tipo-preparo/descricao/{descricao}` - Busca tipo por descrição
- `POST /tipo-preparo` - Cria novo tipo
- `PUT /tipo-preparo` - Atualiza tipo
- `DELETE /tipo-preparo/{codigo}` - Remove tipo

#### Preparo de Produtos
- `GET /preparo-produto` - Lista todos os preparos
- `GET /preparo-produto/codigo/{codigo}` - Busca preparo por código
- `GET /preparo-produto/nome/{nome}` - Busca preparo por nome
- `POST /preparo-produto` - Cria novo preparo
- `PUT /preparo-produto` - Atualiza preparo
- `DELETE /preparo-produto/{codigo}` - Remove preparo

#### Pedidos
- `GET /pedido` - Lista todos os pedidos
- `GET /pedido/codigo/{codigo}` - Busca pedido por código
- `GET /pedido/estado/{estado}` - Lista pedidos por estado
- `GET /pedido/data/{data}` - Lista pedidos por data
- `POST /pedido` - Cria novo pedido
- `PUT /pedido/{codigo}/iniciar-producao` - Inicia produção do pedido
- `PUT /pedido/{codigo}/finalizar-producao` - Finaliza produção do pedido
- `PUT /pedido/{codigo}/iniciar-entrega/{entregador}` - Inicia entrega do pedido
- `PUT /pedido/{codigo}/finalizar-entrega` - Finaliza entrega do pedido
- `PUT /pedido/{codigo}/cancelar` - Cancela pedido

#### Registro de Estoque
- `GET /registro-estoque` - Lista todos os registros
- `GET /registro-estoque/codigo/{codigo}` - Busca registro por código
- `GET /registro-estoque/movimento/{movimento}` - Lista registros por movimento
- `GET /registro-estoque/periodo/{dataInicio}/{dataFim}` - Lista registros por período
- `POST /registro-estoque` - Cria novo registro

## Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

## Como Executar

1. **Clone ou extraia o projeto**

2. **Navegue até o diretório do projeto**
   ```bash
   cd restaurante
   ```

3. **Compile o projeto**
   ```bash
   mvn clean compile
   ```

4. **Execute a aplicação**
   ```bash
   mvn spring-boot:run
   ```

5. **Acesse a aplicação**
   - URL base: `http://localhost:8080`
   - Console H2: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:testdb`
     - Username: `sa`
     - Password: `password`

## Testando a API

### Exemplo de uso com curl:

1. **Criar um grupo alimentar**
   ```bash
   curl -X POST http://localhost:8080/grupo-alimentar \
     -H "Content-Type: application/json" \
     -d '{"nome": "Proteínas"}'
   ```

2. **Listar todos os grupos alimentares**
   ```bash
   curl http://localhost:8080/grupo-alimentar
   ```

3. **Criar um bairro**
   ```bash
   curl -X POST http://localhost:8080/bairro \
     -H "Content-Type: application/json" \
     -d '{"nome": "Centro", "custoEntrega": 5.0}'
   ```

4. **Criar um produto**
   ```bash
   curl -X POST http://localhost:8080/produto \
     -H "Content-Type: application/json" \
     -d '{
       "nome": "Frango",
       "custoUnidade": 15.0,
       "valorEnergetico": 200,
       "estoque": 100,
       "estoqueMinimo": 10,
       "grupoAlimentar": {"codigo": 1}
     }'
   ```

## Configurações

### Banco de Dados
- Utiliza H2 Database em memória
- Dados são perdidos ao reiniciar a aplicação
- Console H2 habilitado para desenvolvimento

### CORS
- Configurado para aceitar requisições de qualquer origem
- Adequado para desenvolvimento e testes

### Logs
- Configurado para mostrar SQL queries no console
- Formato SQL habilitado para melhor legibilidade

## Arquitetura

O projeto segue uma arquitetura em camadas:

1. **Controller**: Camada de apresentação (REST API)
2. **Negocio/Service**: Camada de lógica de negócio
3. **Repository**: Camada de acesso a dados
4. **Entity**: Entidades JPA
5. **DTO**: Objetos de transferência de dados

## Validações Implementadas

- Validação de dados obrigatórios
- Validação de unicidade (CPF, nomes, etc.)
- Validação de relacionamentos entre entidades
- Validação de estados de pedidos
- Validação de estoque mínimo

## Estados do Pedido

- **REGISTRADO**: Pedido criado pelo cliente
- **PRODUCAO**: Pedido em produção
- **PRONTO**: Pedido pronto para entrega
- **ENTREGA**: Pedido saiu para entrega
- **CONCLUIDO**: Pedido entregue
- **CANCELADO**: Pedido cancelado

## Movimentos de Estoque

- **PRODUCAO**: Saída para produção
- **COMPRA**: Entrada por compra
- **VENCIMENTO**: Saída por vencimento
- **DANIFICADO**: Saída por dano

## Observações

- O projeto está configurado para desenvolvimento
- Para produção, considere configurar um banco de dados persistente
- Implemente autenticação e autorização conforme necessário
- Adicione testes unitários e de integração mais abrangentes

