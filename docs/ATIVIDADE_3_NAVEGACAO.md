# Atividade 3 — navegação e compartilhamento de dados

**Status:** Em andamento
**Tempo sugerido:** 1 hora
**Pré-requisitos:** Atividades 0 e 1

## Objetivo

Entender como o usuário muda de tela e como uma tela de destino recebe os dados necessários.

## Explore

Abra `navigation/AppPages.kt` e `navigation/Routes.kt`.

1. Liste as rotas e as telas correspondentes.
2. Siga o clique de um produto da Home até `DetailsRoute` e `DetailScreen`.
3. Siga o botão Voltar e veja como `backStack` é alterada.
4. Observe como carrinho e favoritos podem ser acessados em mais de uma tela.

## Exercício

Adicione uma navegação pequena e reversível: por exemplo, um botão “ver favoritos” em uma tela que ainda não tenha esse atalho. Use as rotas existentes e confirme que o Voltar retorna ao lugar esperado.

## Concluído quando

Você consegue explicar a diferença entre uma rota (qual tela/dados abrir) e a pilha de navegação (por onde o usuário passou), e consegue seguir um argumento da rota até a tela.

## Reflexão

O produto é passado como argumento para detalhes. Que problema poderia surgir se a tela de detalhes precisasse sempre buscar o produto de novo? Em que situação uma tela deveria receber apenas um ID?
