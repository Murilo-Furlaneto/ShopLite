# Atividade 6 — checkout: validar e fechar o fluxo

**Status:** Em andamento
**Tempo sugerido:** 2–3 horas
**Pré-requisitos:** Atividades 3 e 4

## Objetivo

Praticar estado de formulário e conectar carrinho, confirmação e tela de sucesso. Este é um checkout **simulado**: não existe pagamento real, serviço de pedidos remoto ou cobrança.

## O que existe hoje

`CheckoutScreen` mostra campos de endereço que começam preenchidos com valores de exemplo, permite selecionar Pix/cartão/boleto e apresenta o total. Ao confirmar, gera um identificador local, limpa o carrinho e navega para `OrderSuccessScreen`.

Ainda faltam validação do formulário e gravação de um pedido. A forma de pagamento e o endereço selecionados não são incluídos nos argumentos da tela de sucesso.

## Parte A — estado e validação

1. Leia `ShippingAddress` e `PaymentMethod` em `features/checkout/domain/entity/OrderSummary.kt`.
2. Decida quais campos são obrigatórios para a demonstração e quais regras simples serão aplicadas.
3. Inicialize o formulário vazio (ou deixe explícito que os valores são dados de demonstração).
4. Mostre erros junto aos campos e desabilite a confirmação enquanto houver campos inválidos.
5. Teste formulário vazio, parcialmente preenchido e válido.

Não implemente uma consulta real de CEP ou processamento de cartão; isso exigiria serviços e escopo que o projeto não tem.

## Parte B — conclusão coerente

Passe para a tela de sucesso os dados que ela precisa mostrar, como ID e total. Se decidir também exibir pagamento/endereço, modele essa decisão e faça os dados atravessarem o fluxo explicitamente.

Observe a ordem da confirmação: o app limpa o carrinho e navega. Quando o histórico de pedidos estiver implementado, a confirmação deve só ser considerada concluída depois que o pedido local for salvo.

## Concluído quando

- O checkout não pode ser confirmado com campos inválidos.
- Um checkout válido leva à tela de sucesso.
- O total exibido corresponde ao carrinho.
- O carrinho fica vazio depois de uma confirmação bem-sucedida.
- Você consegue explicar quais dados foram mantidos na UI, quais foram passados pela navegação e quais foram persistidos.

## Extensão opcional

Adicione uma etapa de revisão antes da confirmação com itens, quantidades e total. Evite coletar dados reais de pagamento.
