# Desafio opcional — tema claro, escuro e sistema

**Status:** A fazer
**Pré-requisitos:** Atividade 1

## Objetivo

Aprender a guardar uma preferência simples e usar essa preferência para configurar o tema na raiz do Compose.

## Desafio

1. Crie uma escolha com três opções: claro, escuro e seguir o sistema.
2. Guarde a opção escolhida com Preferences DataStore.
3. Observe a preferência na raiz do app e passe o tema correspondente ao tema Compose.
4. Ofereça a escolha em uma tela simples de configurações.
5. Feche e abra o app para conferir se a preferência permanece.

## Conceitos para observar

- DataStore publica valores de forma assíncrona; entenda como o valor inicial é representado enquanto a preferência é lida.
- `isSystemInDarkTheme()` é útil somente para a opção “seguir sistema”.
- A tela não precisa conhecer os detalhes de leitura e gravação do DataStore.

## Critérios de conclusão

As três opções funcionam, a opção escolhida persiste e “seguir sistema” responde à configuração do aparelho.

Consulte a documentação oficial atual do Android para versão e configuração da biblioteca quando iniciar esta atividade; o exemplo antigo de versão fixa foi removido para evitar dependência desatualizada.
