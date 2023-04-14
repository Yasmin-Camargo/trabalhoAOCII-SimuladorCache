# trabalhoAOCII-SimuladorCache
Implementação de um Simulador de Caches desenvolvido em conjunto com @Caroline-Camargo para a disciplina de Arquitetura e Organização de Computadores II, desenvolvido no quarto semestre de Ciência da Computação na UFPel

## Execução
Este simulador de cache é parametrizável quanto ao número de conjuntos, tamanho do bloco, nível de associatividade e política de substituição 
(Considerando que a cache é endereçada à bytes e o endereço possui 32 bits).

A configuração de cache deverá ser repassada por linha de comando com os seguintes parâmetros:

    cache_simulator <nsets> <bsize> <assoc> <substituição> <flag_saida> arquivo_de_entrada

- nsets: número de conjuntos na cache (número total de “linhas” ou “entradas” da cache);
- bsize: tamanho do bloco em bytes;
- assoc: grau de associatividade (número de vias ou blocos que cada conjunto possui);
- substituição: política de substituição, que pode ser Random (R), FIFO (F) ou L (LRU);
- flag_saida: flag que ativa o modo padrão de saída de dados
    -  flag_saida = 1
    ![image](https://user-images.githubusercontent.com/88253809/232159592-bffdaf4a-3024-4cad-8f02-8b220d58659f.png)

        O Formato padrão de saída, na seguinte ordem: Total de acessos, Taxa de hit, Taxa de miss, Taxa de miss compulsório, Taxa de miss de capacidade, Taxa de miss de conflito
    - flag_saida = 0
    ![image](https://user-images.githubusercontent.com/88253809/232159855-93bed274-c44d-4edd-8771-eacc3a01a9cd.png)

        Configurações da cache podem ser realizadas por interface gráfica
- arquivo_de_entrada: arquivo com os endereços para acesso à cache. Podem ser utilizados os seguintes arquivos:
    - \Cache_Simulator\src\cache_simulator\Enderecos\bin_100.bin
    - \Cache_Simulator\src\cache_simulator\Enderecos\bin_10000.bin
    - \Cache_Simulator\src\cache_simulator\Enderecos\vortex.in.sem.persons.bin

## Exemplo de Compilação e execução por terminal
    javac --source-path 'Cache_Simulator/src/' 'Cache_Simulator/src/cache_simulator/cache_simulator.java'

    java -cp Cache_Simulator/src/ cache_simulator/cache_simulator 128 4 1 R 1 .\Cache_Simulator\src\cache_simulator\Enderecos\bin_100.bin
