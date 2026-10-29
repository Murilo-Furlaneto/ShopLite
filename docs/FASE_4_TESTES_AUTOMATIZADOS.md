# Atividade 8 — primeiros testes do projeto

**Status:** A fazer
**Tempo sugerido:** 2–3 horas
**Pré-requisitos:** Atividades 1, 4 e 6

## Objetivo

Aprender a transformar uma regra ou comportamento esperado em uma verificação repetível. Comece testando lógica pequena; não tente cobrir o app todo de uma vez.

## Situação atual

O Gradle inclui JUnit para testes locais e bibliotecas de teste Compose para instrumentação. Os arquivos de teste existentes são exemplos padrão e ainda não verificam as regras do ShopLite. As dependências de `kotlinx-coroutines-test`, MockK e Turbine citadas em rascunhos anteriores ainda não estão declaradas no projeto. Não copie um teste que instancia `CartRepositoryImpl()` sem argumentos: a implementação atual recebe um `CartDao`.

## Parte A — uma regra simples

Crie um teste local para `CartItem.subtotal` em `app/src/test`. Verifique um caso comum (preço × quantidade) e um caso de quantidade 1. Use a estrutura de testes já existente e rode pelo Android Studio.

Antes de escrever, anote: dado um produto com preço X e quantidade Y, qual resultado espero? O teste deve expressar essa expectativa sem precisar abrir uma tela.

## Parte B — tela Compose

Escolha um Composable que recebe estado e callbacks. Se ainda não houver uma função de conteúdo isolada para a tela que você escolheu, essa pode ser parte do exercício: separar a apresentação testável dos pontos que obtêm ViewModels.

Escreva um teste que configura a tela com estado vazio e verifica uma mensagem visível. Depois, configure estado com conteúdo e verifique um elemento do conteúdo.

## Parte C — fluxos (depois)

Quando os testes simples estiverem funcionando, explore testes de corrotinas/Flow com `kotlinx-coroutines-test` e, se precisar, Turbine. Primeiro adicione as dependências compatíveis com o projeto e aprenda o que cada uma resolve. Para testar um repositório ligado a Room, considere um banco em memória; para testar ViewModel, use um repositório falso simples antes de introduzir uma biblioteca de mocks.

## Concluído quando

- Existe ao menos um teste local para uma regra de domínio.
- Existe ao menos um teste Compose para um estado visível.
- Você sabe executar cada tipo e entende se ele roda na JVM ou em dispositivo/emulador.
- Os nomes dos testes descrevem o cenário e o resultado esperado.

## Lembrete

Testes são exercícios de clareza sobre o comportamento. Não é necessário medir cobertura nem testar cada detalhe visual nesta etapa.
