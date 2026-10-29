# Regras de negócio do carrinho

Este arquivo registra o comportamento esperado do carrinho do ShopLite. Use-o para entender o código e para decidir o que testar; não é uma lista de requisitos para um e-commerce real.

## Produtos e quantidades

- Ao adicionar um produto que não está no carrinho, ele entra com quantidade 1.
- Ao adicionar um produto que já está no carrinho, a quantidade daquele item aumenta em 1.
- O botão `+` aumenta a quantidade em 1.
- O botão `−` reduz a quantidade em 1; se estava em 1, o item é removido.
- A ação de remover exclui o item independentemente da quantidade.
- Limpar o carrinho remove todos os itens.

## Valores

- Subtotal do item = preço unitário × quantidade.
- Total do carrinho = soma dos subtotais.
- Badge da Home = soma das quantidades, não o número de produtos diferentes.

## Estados visíveis

- Sem itens: mostrar estado vazio e ação para voltar ao catálogo.
- Com itens: mostrar lista, quantidade por produto, subtotal de cada linha, total e ação de checkout.
- Durante a leitura inicial, a UI pode estar carregando; depois deve refletir os dados emitidos pelo repositório.

## Como confirmar o comportamento

1. Adicione o mesmo produto duas vezes: deve haver uma linha com quantidade 2.
2. Aumente e diminua a quantidade; confira subtotal, total e badge.
3. Diminua uma unidade quando a quantidade for 1: o item deve sumir.
4. Feche e reabra o app: os itens restantes devem continuar no carrinho, pois hoje ele usa Room.

As regras são aplicadas em `features/cart/data/repository/CartRepositoryImpl.kt`; a UI fica em `features/cart/ui/CartScreen.kt` e o estado derivado em `CartViewModel.kt`.
