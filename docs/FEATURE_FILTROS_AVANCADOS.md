# Desafio opcional — filtros avançados e ordenação

**Status:** A fazer
**Pré-requisitos:** Atividade 2

## Objetivo

Combinar filtros de preço, categoria e busca, e oferecer ordenação explícita dos resultados.

## Ponto de partida

`ProductFilter.kt` já declara `ProductFilter` e `SortOrder`, mas esses tipos ainda não participam do `ProductViewModel` nem da Home. Atualmente só há busca por título e seleção de categoria.

## Desafio

1. Defina o estado de filtro e ordenação que a tela precisa apresentar.
2. Combine esse estado com produtos, categoria e consulta de busca.
3. Adicione um botão que abre um `ModalBottomSheet` com faixa de preço e escolhas de ordenação.
4. Permita aplicar, cancelar ou limpar filtros (decida e documente o comportamento).
5. Mostre quando filtros ativos deixarem a lista vazia.

## Critérios de conclusão

- Categoria, faixa de preço e texto de busca funcionam ao mesmo tempo.
- Ordenação por preço crescente e decrescente altera a ordem visível.
- Limpar filtros devolve o catálogo esperado.
- Você consegue explicar qual estado representa as escolhas temporárias do Bottom Sheet e quando elas passam a valer.

## Extensão opcional

Adicione ordenação por avaliação. `RangeSlider` e `ModalBottomSheet` são componentes de UI; o cálculo que filtra/ordena a lista deve continuar fácil de entender isoladamente.
