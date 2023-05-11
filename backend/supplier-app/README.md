# Service API

API de cadastro de prestadores de serviços 

## Tecnologias

* Java 17
* Maven
* Quarkus
* PostgreSQL

## Model
- - - -
### Company
```json
{
  "id": 1,
  "cpf": "000.000.000-00",
  "type": "PF",
  "name": "John Doe",
  "cep": "00000-000",
  "email": "example@example.com",
  "active": true
}
```
#### Tipo de dados Company

* id: Long
* cpf: String(CPF válido incluindo pontuação, Obrigatório quando type = "PF")
* cnpj: String(CNPJ válido incluindo pontuação, Obrigatório quando type = "PJ")
* type: String("PF", "PJ"")
* email: String (Email válido)
* active Boolean

## Request DTO
- - - -
### CompanyDTO
```json
{
  "cpf": "000.000.000-00",
  "type": "PF",
  "name": "John Doe",
  "cep": "00000-000",
  "email": "example@example.com"
}
```
#### Tipo de dados CompanyDTO

* cpf: String(CPF válido incluindo pontuação, Obrigatório quando type = "PF")
* cnpj: String(CNPJ válido incluindo pontuação, Obrigatório quando type = "PJ")
* type: String("PF", "PJ"")
* email: String (Email válido)


## Response Errors

### ResponseError
```json
{
  "reference": "https://docs.oracle.com/en",
  "type": "Not Found Error",
  "status": 404,
  "details": "The queried element does not exists",
  "errors": [{
    "entity": "Company",
    "reason": "Company with 10 id not found"
  }]
}
```
#### Tipo de dados ResponseError

* reference: String(URL pattern) - *Link para a página da documentação do erro*
* type: String - *Tipo de erro encontrado*
* status: Integer - *Código de status enviado* 
* details: String - *Detalhes adicionais do erro*
* errors: List<BusinessError> - *Lista de erros ocorridos*


## Company Endpoints

| Método | Rota               | Status        | Resposta                                                              |
|--------|--------------------|---------------|-----------------------------------------------------------------------|
| GET    | /v1/companies      | 200           | Retorna um JSON com todos os serviços cadastrados                     |
| GET    | /v1/companies/{id} | 200, 404      | Retorna um JSON com os dados do serviço com o id especificado na rota |
| POST   | /v1/companies      | 201, 400, 422 | Retorna um JSON com os dados cadastrados                              |
| DELETE | /v1/companies/{id} | 204, 404, 409 | Sem retorno                                                           |
| PUT    | /v1/companies/{id} | 201, 404, 422 | Retorna um JSON com os dados atualizados                              |