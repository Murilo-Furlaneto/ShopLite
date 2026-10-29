# Atividade 5 — favoritos reativos

**Status:** Implementado; revisar e explicar
**Tempo sugerido:** 1 hora
**Pré-requisitos:** Atividades 1, 3 e 4

## Objetivo

Observar o mesmo estado sendo usado em telas diferentes e persistido localmente.

## O que existe hoje

Os favoritos são armazenados como IDs em Room. `WishlistRepositoryImpl` observa o DAO e publica um `StateFlow<Set<Int>>`; Home, detalhes e Wishlist consultam esses IDs para desenhar o coração e montar a lista.

No código, o pacote está escrito `whishlist` (com h extra). Para estudar, siga os nomes reais dos arquivos; uma possível renomeação pode ficar para uma atividade de organização futura.

## Explore

1. Favorite um produto na Home e abra detalhes: o estado aparece atualizado?
2. Desfavorite nos detalhes e retorne à Home e à Wishlist.
3. Feche e reabra o app. O favorito permanece?
4. Siga `toggleFavorite` até o DAO e depois acompanhe o fluxo de IDs de volta para a UI.

## Exercício de entendimento

Explique por que guardar apenas IDs é suficiente para marcar produtos como favoritos, mas a Wishlist ainda precisa obter os objetos `Product` para mostrar os cartões. Compare a solução atual com a ideia de guardar o produto inteiro.

## Concluído quando

Você confirmou que o estado é compartilhado entre telas e persiste após reiniciar o app, e consegue apontar como isso acontece.

## Extensão opcional

Adicione animação pequena ao alternar o ícone, usando APIs de animação do Compose. Mantenha o estado favorito como a fonte da verdade; a animação deve apenas representar a mudança.
