# Atividade 2 — catálogo: busca, filtros e estados de tela

**Status:** Em andamento
**Tempo sugerido:** 1–2 horas
**Pré-requisitos:** Atividades 0 e 1

## O que já existe

`ProductViewModel` busca produtos pelo repositório, guarda categoria e texto de busca em `StateFlow`, combina esses valores e publica `ProductUiState`. A Home exibe carregamento, erro ou produtos.

## Objetivo

Aprender a seguir uma operação assíncrona e distinguir os estados de uma tela.

## Explore

1. Siga `ProductViewModel.fetchProducts()` até `ProductsRepositoryImpl.fetchProducts()`.
2. Identifique a tentativa de rede, a leitura do cache local e o fallback de demonstração.
3. Veja onde a lista filtrada é calculada e onde `ProductUiState` é transformado em UI.
4. Desligue a rede e reabra/recarregue o catálogo. Registre o comportamento observado.

## Exercício

Melhore a experiência para uma busca sem resultados. A tela deve distinguir pelo menos:

- carregando produtos;
- erro ao carregar;
- catálogo carregado sem produtos que correspondam à busca;
- resultados encontrados.

Se achar útil, adicione um botão para limpar a busca no estado sem resultados.

## Concluído quando

Cada estado tem uma apresentação compreensível, e você consegue explicar onde os dados são carregados, filtrados e exibidos.

## Perguntas

- A busca é feita no servidor ou na lista já carregada? Como você sabe?
- Por que o erro de rede pode não impedir a exibição de produtos?
- O que o usuário deveria ver se a API e o cache falharem na primeira execução?

## Extensão opcional

Consulte [Filtros avançados e ordenação](FEATURE_FILTROS_AVANCADOS.md) depois de entender o filtro simples por categoria e título.
