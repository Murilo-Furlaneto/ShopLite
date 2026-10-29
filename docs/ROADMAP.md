# ShopLite — roteiro de aprendizado Android

O ShopLite é um projeto de estudo: uma loja pequena usada para praticar desenvolvimento Android nativo com Kotlin e Jetpack Compose. O objetivo não é simular um e-commerce de produção. Checkout, pagamento e pedidos são demonstrações locais, sem cobrança real nem backend próprio.

Este roteiro organiza o estudo em passos curtos. Cada atividade explica o que observar, o que tentar e como saber que terminou. Leia o código existente antes de implementar: algumas atividades são exercícios de exploração e outras propõem uma evolução.

## Como usar este roteiro

1. Escolha uma atividade marcada **A fazer** ou **Em andamento**.
2. Antes de alterar código, escreva com suas palavras o fluxo atual e o comportamento esperado.
3. Faça uma alteração pequena de cada vez e execute o app para observar o resultado.
4. Ao concluir, atualize o status e anote uma coisa que aprendeu e uma dúvida que ficou.

Não precisa completar todas as atividades para o projeto ser válido. As atividades opcionais são trilhas de exploração, não uma lista obrigatória.

## Estado atual observado

| Área | Estado | O que existe hoje |
| --- | --- | --- |
| Catálogo | Implementado | A Home busca produtos, mostra estados de carregamento/erro, pesquisa por título e filtra por categoria. |
| Detalhes e navegação | Implementado | É possível abrir detalhes, ir ao carrinho e navegar entre as telas principais com Navigation 3. |
| Carrinho | Implementado | O carrinho usa Room, permite alterar/remover itens e expõe totais reativos. |
| Cache do catálogo | Implementado | Produtos remotos são guardados em Room; há dados de demonstração para fallback. |
| Favoritos | Implementado | IDs favoritos são guardados em Room e observados pela Home, detalhes e tela de favoritos. |
| Checkout | Protótipo implementado | Formulário com valores de exemplo, seleção visual de pagamento e confirmação local; ainda não valida o endereço nem registra pedido persistente. |
| Perfil e pedidos | Parcial | O perfil mostra um pedido estático; há uma entidade e DAO de pedidos, mas ainda não estão ligados ao banco e ao fluxo do app. |
| Testes | Inicial | Há testes de exemplo gerados pelo Android Studio; ainda não cobrem as regras do ShopLite. |

## Trilha principal

| Ordem | Atividade | Estado | Assunto principal |
| --- | --- | --- | --- |
| 0 | [Conhecer o app e seguir um fluxo](ATIVIDADE_0_CONHECER_O_APP.md) | A fazer | Orientação no projeto e leitura de código |
| 1 | [Compose: estado, eventos e recomposição](ATIVIDADE_1_COMPOSE_ESTADO.md) | A fazer | `@Composable`, estado e UI reativa |
| 2 | [Catálogo: busca, filtros e estados de tela](ATIVIDADE_2_CATALOGO.md) | Em andamento | ViewModel, StateFlow, loading/erro/sucesso |
| 3 | [Navegação e compartilhamento de dados](ATIVIDADE_3_NAVEGACAO.md) | Em andamento | Rotas, back stack e passagem de argumentos |
| 4 | [Carrinho, regras e persistência](FASE_1_ROOM_DATABASE.md) | Implementado; revisar e explicar | Room, DAO, repository, Flow |
| 5 | [Favoritos reativos](FASE_3_FAVORITOS_WISHLIST.md) | Implementado; revisar e explicar | Estado compartilhado entre telas e Room |
| 6 | [Checkout: validar e fechar o fluxo](FASE_2_CHECKOUT_FLUXO.md) | Em andamento | Estado de formulário e fluxo de compra simulado |
| 7 | [Histórico de pedidos](FEATURE_HISTORICO_PEDIDOS.md) | A fazer | Persistência de uma operação completa |
| 8 | [Primeiros testes do projeto](FASE_4_TESTES_AUTOMATIZADOS.md) | A fazer | Testar regras e estados que importam |

“Em andamento” indica que há código para explorar, mas a atividade pede completar ou entender melhor o comportamento. Use os critérios de cada atividade para decidir quando mudar o status.

## Trilhas opcionais

Faça depois de se sentir confortável com a trilha principal. Escolha uma por vez.

- [Filtros avançados e ordenação](FEATURE_FILTROS_AVANCADOS.md) — Bottom Sheet, faixa de preço e ordenação.
- [Tema claro/escuro/sistema](FEATURE_THEME_SWITCHER.md) — DataStore e preferência persistente.
- [Lembrete de carrinho](FEATURE_NOTIFICACOES_WORKMANAGER.md) — WorkManager e notificações locais.
- [Animação de elemento compartilhado](FEATURE_ELEMENTO_COMPARTILHADO.md) — transição entre catálogo e detalhes.

## Um jeito leve de acompanhar o aprendizado

Ao terminar uma atividade, registre em [DIARIO_DE_APRENDIZADO.md](DIARIO_DE_APRENDIZADO.md) (ou em um caderno):

- O que eu queria que o app fizesse?
- Qual dado ou estado mudou?
- Quem observa essa mudança e atualiza a tela?
- O que eu precisei pesquisar ou ainda não entendi?

Você não precisa conhecer todos os nomes de arquitetura de antemão. Primeiro siga o dado pelo código; depois associe os nomes (`ViewModel`, `Flow`, `DAO`) às responsabilidades que você observou.
