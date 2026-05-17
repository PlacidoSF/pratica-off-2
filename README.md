# Projeto Offline 2 - Estrutura de Dados II

Este repositório contém a implementação do sistema de buscas de filmes solicitado como Projeto Offline 2 para a disciplina de Estrutura de Dados II.

O projeto adota uma arquitetura simplificada de Cliente-Servidor para simular o comportamento de um banco de dados e de uma memória cache, priorizando a eficiência nas buscas por ID.

## Estruturas Utilizadas
* **Cliente (Cache):** Gerenciado através de uma **Árvore AVL** aliada a uma Fila para controle da política de substituição FIFO (First-In, First-Out).
* **Servidor (Banco de Dados):** Gerenciado através de uma **Tabela Hash** (utilizando o método da divisão e encadeamento exterior) e uma Lista Ligada padrão como armazenamento físico.

## Como Executar
O projeto não exige configurações complexas ou dependências externas. Para rodar o sistema:

1. Clone este repositório.
2. Abra o projeto na sua IDE de preferência.
3. Compile e execute o arquivo `App.java` (a classe principal do projeto).
4. O sistema será iniciado diretamente no terminal.