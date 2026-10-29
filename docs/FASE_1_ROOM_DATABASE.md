# Atividade 4 — carrinho, regras e persistência com Room

**Status:** Implementado; revisar e explicar
**Tempo sugerido:** 1–2 horas
**Pré-requisitos:** Atividades 0, 1 e 3

## Objetivo

Entender como uma regra de negócio sai da ação na tela, passa pelo repositório e altera os dados persistidos em Room. Room é uma camada local sobre SQLite; observe o fluxo do projeto antes de adicionar mais abstrações.

## O que já existe

- `CartItemEntity` representa uma linha da tabela do carrinho.
- `CartDao` lê itens como `Flow` e oferece operações de inserção e remoção.
- `CartRepositoryImpl` converte entidade para modelo de domínio e executa as regras de quantidade.
- `CartViewModel` transforma os itens em `CartUiState` com totais.
- A tela observa esse estado e apresenta vazio ou itens.

O projeto também usa Room para cache de produtos e favoritos. A configuração está em `core/database/AppDatabase.kt` e `core/di/DatabaseModule.kt`. Ela usa os imports `androidx.room3.*` e as dependências Room 3 declaradas no catálogo Gradle. Os exemplos antigos com `androidx.room.*` e versões 2.x não correspondem ao setup atual; siga o código e as versões do repositório.

## Explore

1. Leia `features/cart/regras_de_negocio.md` e compare cada regra com `CartRepositoryImpl`.
2. Siga `onIncrementQuantity` da tela até o DAO.
3. Localize o `Flow` do DAO e acompanhe como ele atualiza o `StateFlow` do repositório e o estado do ViewModel.
4. Adicione um produto, encerre o processo do app e abra novamente. Observe se o dado continua.
5. Repita incrementando, decrementando até remover e limpando o carrinho.

## Exercício de entendimento

Sem alterar código, desenhe o caminho de `+1` e responda:

- Quem decide a nova quantidade?
- Quem grava a mudança?
- Como a tela descobre que a gravação mudou os dados?
- Por que Room persiste após fechar o app, enquanto uma lista em memória não persistiria?

## Extensão opcional

Adicione um limite demonstrativo de quantidade, como máximo de 5 por produto. Decida a regra primeiro, implemente-a em um único lugar e apresente um feedback quando o limite for atingido.

## Concluído quando

Você consegue explicar o caminho completo e confirmar que as operações do carrinho continuam corretas após reabrir o aplicativo.
