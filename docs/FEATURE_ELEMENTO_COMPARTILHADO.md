# Desafio opcional — animação de elemento compartilhado

**Status:** A fazer
**Pré-requisitos:** Atividades 1 e 3

## Objetivo

Explorar uma transição visual em que a imagem de um produto parece sair do cartão na Home e continuar na tela de detalhes.

## Ponto de partida

`navigation/Routes.kt` já envolve o `NavDisplay` em `SharedTransitionLayout`, mas os cartões e a imagem de detalhes ainda não declaram elementos compartilhados. O layout por si só não cria a transição.

## Desafio

1. Confirme como a versão atual de Navigation 3 e Compose expõe o escopo de transição para cada destino.
2. Use uma chave estável baseada no ID do produto tanto na imagem do cartão quanto na imagem de detalhes.
3. Aplique o modifier de elemento compartilhado nos dois lados da transição.
4. Teste entrar nos detalhes e voltar, inclusive após rolagem da lista.
5. Garanta que o app continue utilizável se a imagem ainda estiver carregando.

## Critério de conclusão

A imagem do mesmo produto anima entre Home e detalhes nos dois sentidos sem associar a transição a outro cartão.

As APIs de transição evoluem; consulte a documentação da versão instalada em vez de copiar cegamente exemplos de outra versão.
