# Atividade 1 — Compose: estado, eventos e recomposição

**Status:** A fazer
**Tempo sugerido:** 1–2 horas
**Pré-requisitos:** Atividade 0

## Objetivo

Entender a ideia central do Compose: a interface é desenhada a partir do estado atual; quando o estado observado muda, a interface pode ser composta novamente.

## Explore antes de editar

Em `features/product/ui/home/HomeScreen.kt`, localize `HomeScreen` e `HomeContent`.

- Quais valores vêm do ViewModel?
- Quais callbacks representam eventos do usuário?
- Por que `HomeContent` recebe valores e funções em vez de buscar dependências por conta própria?
- Encontre o campo de busca e siga `onSearchQueryChange` até `ProductViewModel`.

## Exercício

1. Escolha um texto ou estado visível na Home.
2. Localize de onde esse valor vem e qual ação pode alterá-lo.
3. Faça uma mudança pequena na mensagem exibida para um estado existente, como carregamento, erro ou lista vazia.
4. Execute o app e observe em que condição a mensagem aparece.
5. Desfaça ou mantenha a mudança com uma descrição clara do novo comportamento.

Não comece criando uma arquitetura nova. O exercício é reconhecer o caminho **estado → UI** e **evento → atualização do estado**.

## Concluído quando

Você consegue explicar por que digitar na busca atualiza a lista sem manipular cada cartão manualmente, e consegue apontar a origem do estado e o callback responsável.

## Extensão opcional

Adicione uma mensagem de “nenhum resultado” quando a busca não encontrar produtos. Pense se esse estado é diferente de “nenhum produto carregado” e onde é mais fácil expressá-lo.
