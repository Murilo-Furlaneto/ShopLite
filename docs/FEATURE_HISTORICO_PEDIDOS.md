# Atividade 7 — histórico de pedidos

**Status:** A fazer
**Tempo sugerido:** 2–4 horas
**Pré-requisitos:** Atividades 4 e 6

## Objetivo

Completar uma operação com persistência local: converter o checkout válido em pedido salvo e mostrar os pedidos na tela de perfil.

## O que já existe e o que falta

Existem `OrderEntity` e `OrderDao` em `features/order/data/local`, mas a entidade não está incluída na lista de entidades de `AppDatabase`; o DAO também não é fornecido por `DatabaseModule` nem conectado ao checkout ou perfil. O perfil atualmente exibe um pedido fixo. Portanto, ainda não há histórico funcional.

## Caminho sugerido

1. Inspecione `OrderSummary`, `OrderEntity` e `OrderDao`. Decida quais dados mínimos compõem um pedido para esta demonstração.
2. Inclua a entidade no banco e exponha o DAO pela configuração de dependências.
3. Crie um repositório de pedidos com operações de salvar e observar uma lista.
4. Ao confirmar um checkout válido, salve um pedido antes de limpar o carrinho e navegar para sucesso.
5. Troque o cartão estático do perfil por uma lista observada. Mostre um estado vazio quando não houver pedidos.
6. Faça mais de um pedido de demonstração e confira a ordem e os dados após reiniciar o app.

## Perguntas de projeto

- O histórico precisa guardar somente ID, valor e pagamento, ou também os itens comprados? Para o histórico continuar fiel, quais dados precisam ser copiados para o pedido?
- O que deve ocorrer se falhar a gravação do pedido: limpar o carrinho ou manter os itens para tentar novamente?
- Como ordenar os pedidos? Que informação permite fazer essa ordenação?

## Concluído quando

Um pedido confirmado aparece no perfil após a navegação e continua aparecendo depois de fechar e reabrir o app. Um checkout inválido não cria pedido.

## Escopo

Use dados fictícios. Não integre pagamento nem colete endereço real. Essa atividade serve para estudar persistência e fluxo de dados local.
