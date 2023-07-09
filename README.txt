# README - Programa de Escalonamento de Processos

Este programa implementa um escalonador de processos com diferentes algoritmos de escalonamento. Ele permite a execução dos seguintes algoritmos:

1. FCFS (First-Come, First-Served)
2. SJF Preemptivo (Shortest Job First - Preemptivo)
3. SJF Não Preemptivo (Shortest Job First - Não Preemptivo)
4. Prioridade Preemptivo
5. Prioridade Não Preemptivo
6. Round Robin

## Requisitos

Para executar o programa, você precisará ter o seguinte:

- Java Development Kit (JDK) instalado em seu sistema
- Um ambiente de desenvolvimento Java, como o Eclipse ou o IntelliJ IDEA (opcional)

## Como Executar o Programa

1. Abra um ambiente de linha de comando (terminal ou prompt de comando).
2. Navegue até o diretório onde o arquivo `Main.java` está localizado.
3. Compile o arquivo `Main.java` digitando o seguinte comando:

   javac Main.java

4. Após a compilação bem-sucedida, execute o programa digitando o seguinte comando:

   java Main

5. O programa será executado e solicitará a inserção de dados.
6. Digite o número correspondente à quantidade de processos com os quais você deseja trabalhar e pressione Enter.
7. Digite "S" ou "N" para definir se os dados dos processos serão gerados aleatoriamente. Digite S para Sim ou N para Não.
8. Caso tenha optado por inserir os dados manualmente, defina um número inteiro para os atributos de cada processo: Tempo de Execução, Tempo de Chegada e Prioridade, respectivamente, para cada um dos processos definidos no passo 6.
9. O programa exibirá os processos e um menu com as opções disponíveis.
10. Digite o número correspondente à opção desejada e pressione Enter para selecionar o algoritmo de escalonamento.
11. Siga as instruções exibidas na tela para fornecer os detalhes necessários sobre os processos.
12. O programa exibirá a execução do escalonamento de processos e, ao final, mostrará o tempo médio de espera.
13. Você pode selecionar outras opções no menu, como imprimir a lista de processos, popular a lista de processos novamente ou sair do programa.
14. Para sair do programa, selecione a opção "9 - Sair" no menu.

## Observações

- Certifique-se de ter as permissões necessárias para compilar e executar o programa no diretório escolhido.
- Certifique-se de fornecer os dados corretos para os processos, como tempos de execução, tempos de chegada e prioridades, quando solicitado.
- O programa foi desenvolvido em Java e pode ser executado em qualquer sistema operacional compatível com Java.
- O programa possui um delay de 200 milisegundos para impressão de cada tempo apenas para efeito estético.