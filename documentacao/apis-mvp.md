
após fazer login, entra e lista organizações do usuário logado.

- GET api/me/organizations

se não tem organizações, a tela sugere que o usuário crie uma nova organização.

- POST api/organizations
- GET api/organizations/:id
- PUT api/organizations/:id

Se tem organização vinculada, seta o orgid na sessão e usa para requests posteriores

com o organization definido, lista associados da organização
- GET api/users-transactions
  - lista usuarios e lista de transações do usuario
  - deve respeitar filtros ANO/MES/DATA e between datas
  - response 
[ 
  {
    "user_id": 1,
    "user_name": "John Doe",
    "transactions": [
      {
        "transaction_id": 101,
        "amount": 150.00,
        "date": "2024-06-01",
        "tags": ["tag1", "tag2"]
      },
      {
        "transaction_id": 102,
        "amount": 200.00,
        "date": "2024-06-15",
        "tags": ["tag3"]
      }
    ]
  },
  {
    "user_id": 2,
    "user_name": "Jane Smith",
    "transactions": [
      {
        "transaction_id": 103,
        "amount": 300.00,
        "date": "2024-06-10",
        "tags": ["tag4"]
      }
    ]
  }
 

depois de listar transações por usuario
da pra clicar no usuario e ver todas transacoes daquele usuario
da pra adicionar uma transação para o usuário
- POST api/users/:user_id/transactions
- GET api/users/:user_id/transactions
  - retorna mesmo objeto de transações do usuário, mas filtrado pelo user_id
