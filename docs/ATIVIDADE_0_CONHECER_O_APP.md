# Atividade 0 — conhecer o app e seguir um fluxo

**Status:** A fazer
**Tempo sugerido:** 30–60 minutos
**Pré-requisitos:** nenhum

## Objetivo

Formar um mapa mental do app antes de tentar mudar várias coisas ao mesmo tempo.

## Explore

1. Abra o projeto no Android Studio e execute o app.
2. Na Home, pesquise um produto e selecione uma categoria.
3. Abra os detalhes, adicione o produto ao carrinho e volte.
4. Abra o carrinho, altere a quantidade e observe os totais.
5. Favorite um item e localize-o na tela de favoritos.
6. Abra o perfil e o checkout. Observe o que é real no app e o que ainda é apenas demonstração.

## Siga o código

- Comece em `MainActivity.kt`: como o app entra no Compose?
- Siga para `navigation/Routes.kt`: onde as telas são ligadas às rotas?
- Escolha o fluxo “adicionar ao carrinho” e siga-o da tela de detalhes até `CartRepositoryImpl`.
- Encontre onde os itens do carrinho são lidos de Room e observados pela UI.

## Perguntas para responder com suas palavras

- Que arquivos desenham a tela e quais guardam ou transformam dados?
- O que acontece quando uma rota é adicionada à pilha? E quando o botão Voltar é usado?
- Que comportamento continua depois de fechar e reabrir o app?
- Quais partes usam uma API e quais são apenas locais/de demonstração?

## Concluído quando

Você consegue desenhar ou explicar o caminho de um produto desde a Home até o carrinho, citando os arquivos principais, sem precisar decorar todos os detalhes.

## Extensão opcional

Faça um diagrama simples: `Composable → ViewModel → Repository → DAO/API`. Ajuste as setas se o fluxo observado for diferente.
