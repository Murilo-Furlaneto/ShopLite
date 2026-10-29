# Desafio opcional — lembrete local de carrinho

**Status:** A fazer
**Pré-requisitos:** Atividade 4; noções básicas de permissões Android

## Objetivo

Explorar trabalho que pode continuar agendado pelo sistema e notificações locais. Use o recurso apenas como simulação de lembrete, sem enviar dados para servidor.

## Desafio

1. Pesquise o modelo de trabalho adequado no WorkManager para uma tarefa adiável.
2. Agende um lembrete demonstrativo quando houver itens no carrinho.
3. Cancele ou atualize o lembrete se o carrinho for esvaziado.
4. Crie o canal de notificação antes de publicar a notificação.
5. Trate a permissão de notificações em versões Android que a exigem e explique ao usuário por que ela é solicitada.
6. Teste o agendamento, cancelamento e abertura do app pela notificação.

## Questões para estudar

- O que significa uma tarefa ser adiável? Por que não usar um timer simples dentro da tela?
- Como evitar agendar vários lembretes duplicados?
- Qual texto deixa claro que este lembrete vem do app e pode ser desativado?

## Critério de conclusão

O lembrete é agendado e cancelado de acordo com o estado do carrinho, e o app lida com permissão negada sem falhar.

Consulte a documentação oficial atual do Android ao iniciar. Os exemplos antigos de Worker/ícone foram omitidos porque APIs e requisitos de notificação variam conforme a versão Android.
