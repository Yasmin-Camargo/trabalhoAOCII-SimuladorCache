package cache_simulator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 @author Caroline Camargo e Yasmin Camargo
 * --- Trabalho de Implementação de um Simulador de Caches ---
 *  Disciplina Arquitetura e Organização de Computadores II
 *  Prof. Marcelo Schiavon Porto
 **/

public class cacheL1 {
    //Atributos
    private final int nsets, bsize, assoc, size, nblocks, n_bits_offset, n_bits_indice, n_bits_tag, flagOut;
    private final String subst;
    private int [][]cache_val, cache_tag;
    private int missCompulsorio, missConflito, missCapacidade, hit, acessos, blocosVazios;
    private Queue[] fila;       //fila para politica de substituição FIFO e LRU
                                //OBS.: A cache é endereçada à bytes e o endereço possui 32 bits por padrão
    
    //Construtor
    public cacheL1(int nsets, int bsize, int assoc, String subst, int flagOut) {
        this.nsets = nsets;     //número de conjuntos na cache
        this.bsize = bsize;     //tamanho do bloco
        this.assoc = assoc;     //grau de associatividade
        this.subst = subst;     //política de substituição: Random (R), FIFO (F) ou L (LRU)
        this.flagOut = flagOut; //forma como os dados serão exibidos
        
        nblocks = this.nsets * this.assoc;  //quantidade de blocos
        size = nblocks * this.bsize;        //tamanho total da cache (em bytes) = quantidade de blocos x tamanho do bloco
        
        blocosVazios = nblocks;             //variável de controle para saber quantos blocos ainda não foram escritos na cache
        
        n_bits_offset = (int) ((Math.log(this.bsize)) / Math.log(2) + 1e-10); //Forma de calcular um logaritimo de base 2, devido à aritmética de ponto flutuante imprecisa é acrescentado 1e-10
        n_bits_indice = (int) (Math.log(this.nsets) / Math.log(2) + 1e-10);
        n_bits_tag = 32 - n_bits_offset - n_bits_indice;
        
        cache_val = new int[nsets][assoc];       //matriz que armazena o bit de validade
        cache_tag = new int[nsets][assoc];       //matriz que armazena a tag
        
        if(subst.compareToIgnoreCase("f") == 0 || subst.compareToIgnoreCase("l") == 0){
            fila = new Queue[nsets];
            for (int i = 0; i < nsets; i++) {
                fila[i] = new LinkedList();
            }
        }
    }
    
    //Recebe um endereço da 32 bits para armazenar na memória cache
    //Ordem dos bits: tag - indice - offset
    public void alocaEnderecoCache(int endereco){
        acessos++;
        int tag = endereco >> (n_bits_offset + n_bits_indice);                               // bits que representam a tag 
        int indice = (endereco >> n_bits_offset) & ((int) Math.pow(2, n_bits_indice) - 1);   // bits que representam o índice  
        
        //System.out.println("binario: "+endereco+" \ttag:"+tag + "\tindice" + indice);
        
        int posicaoLivre = -1, bitControle = -1;    // 1 -> endereço está na cache
                                                    // 2 -> endereco não está na cache, mas tem linhas vagas
                                                    // 3 -> endereco não está na cache, mas não tem espaço vazio
        
        for (int i = 0; i < assoc; i++) {           //percorre as linhas/vias da cache para um determinado indice/conjunto
            if (cache_val[indice][i] == 0 && posicaoLivre == -1) { //caso a posição encontrada não tenha sido preenchida ainda
                bitControle = 2;
                posicaoLivre = i; 
            } else if (cache_tag[indice][i] == tag && cache_val[indice][i] != 0 ){          //caso o endereço requisitado esteja na cache   
                hit++;
                bitControle = 1;
                atualizaAcesso(indice, i);
                break;
            } 
        }
        
        if (bitControle == 2) {                             //endereco não está na cache, mas tem linhas vagas
            missCompulsorio++;
            blocosVazios--;
            cache_val[indice][posicaoLivre] = 1;
            cache_tag[indice][posicaoLivre] = tag;
            addHistorico(indice, posicaoLivre);             //adiciona em uma estrutura de dados, caso se tenha trabalhando com fifo ou lru
            
        } else if(bitControle != 1){                        //caso tenha ocorrido um miss de capacidade ou de conflito
            if (verificaBlocosLivres()) {
                missConflito++;
            } else {
                missCapacidade++;
            }
            
            if (assoc == 1) {                               //mapeamento direto
                cache_tag[indice][0] = tag;
                cache_val[indice][0] = 1;
            } else {                                        //mapeamento conjunto associativo ou totalmente direto
                int id = politicaSubstituicao(indice, tag);
                cache_tag[indice][id] = tag;
                cache_val[indice][id] = 1;
            }
        }
    }
    
    //Verifica se a cache já está totalmente preenchida
    private boolean verificaBlocosLivres(){
        if (blocosVazios > 0) {
            return true;
        }
        return false;
    }
    
    //Salva a ordem dos indices em uma estrutura de dados, caso esteja se implementando fifo ou lru
    private void addHistorico(int indice, int pos){
        if (subst.compareToIgnoreCase("f") == 0 || subst.compareToIgnoreCase("l") == 0) {      
            fila[indice].add(pos);                              //salva a posição do elemento da fila
        } 
    }
    
    //determina qual dado sairá da cache (de acordo com a politica de substitução)
    private int politicaSubstituicao(int indice, int tag){
        if (subst.compareToIgnoreCase("f") == 0 || subst.compareToIgnoreCase("l") == 0) { //fifo ou lru
            int linhaRemovida = (int) fila[indice].remove();    //indice do dado que está a mais tempo na cachce 
            fila[indice].add(linhaRemovida);
            return linhaRemovida;
        } else if(subst.equals("R") || subst.equals("r")){      //ramdom
            Random random = new Random();
            int numSorteado = random.nextInt(assoc);            //gera um numero aleatório (indice) entre 0 e o número de vias - 1
            return numSorteado;
        } else {
            System.out.println("Erro: Politica de substituicao invalida");
            System.exit(1);
        }
        return -1;
    }
    
    //pega um dado que está no meio da fila e coloca no final dela 
    private void atualizaAcesso(int indice, int idhit) {
        if (subst.compareToIgnoreCase("l") == 0) {              //utilizado para atualizar a prioridade de um dado para implementação lru
            int novoacesso = -1, tamanho = fila[indice].size();
            for (int i = 0; i < tamanho; i++) {                 //manda todo mundo para o fim da fila, exceto o elemento que deu hit  
                int linhaRemovida = (int) fila[indice].remove(); //indice do dado que está a mais tempo na cache
                if (linhaRemovida == idhit){
                    novoacesso = linhaRemovida;
                } else {
                    fila[indice].add(linhaRemovida);
                }
            }
            fila[indice].add(novoacesso);                       //adiciona o elemento que deu hit no fim da fila
        }
    }
    
    private static double arredondar(double num) {
        return Math.round(num * 10000.0)/10000.0;
    }
    
    private static double arredondar2(double num) {
        return Math.round(num * 100.0)/100.0;
    }
   
    @Override
    public String toString() {
        String saida;
        int totalMisses = missCapacidade + missCompulsorio + missConflito;
        
        if (flagOut == 1){  //formato padrão: Total de acessos, Taxa de hit, Taxa de miss, Taxa de miss compulsório, Taxa de miss de capacidade, Taxa de miss de conflito
            saida = "" + acessos + 
                    ", " + arredondar(((float) hit/acessos)) + 
                    ", " + arredondar((float) totalMisses/acessos) + 
                    ", " + arredondar2((float) missCompulsorio/totalMisses) + 
                    ", " + arredondar2((float) missCapacidade/totalMisses) + 
                    ", " + arredondar2((float) missConflito/totalMisses);
            
        } else {            //formato livre
            saida = "\n   ------------------- INFORMACOES MEMORIA CACHE -------------------\n";
            saida += "\n   Tamanho: "  + size +" bytes" +
                     "\t\t\tNumero de conjuntos: " + nsets + 
                     "\n   Quantidade de blocos: " + nblocks + 
                     "\n   Grau de associtividade: " + assoc ;
            if(assoc == 1) {
                saida += " (mapeamento direto)";
            } else if(nsets == 1){
                saida += " (mapeamento totalmente associativo)";
            } else {
                saida += " (mapeamento associativo por conjunto)";
            }
            saida += 
                     "\n\n   Bits indice:\t" + n_bits_indice + 
                     "\n   Bits ofsset:\t" + n_bits_offset + "\n   Bits tag: \t" + n_bits_tag +
                     "\n\n   --------------------- TAXA DE HITS E MISSES ---------------------\n" +
                     "\n   Acessos: \t" + acessos + "   (100%)" +
                     "\n   Hit:\t\t" + arredondar((float) hit/acessos)*100 + "%" + 
                     "\n   Misses: \t"+ arredondar((float)(totalMisses)/acessos)*100 + "%" + 
                     "\n\n   Miss compulsorio: \t" + missCompulsorio + "\t(" + arredondar2((float) missCompulsorio/totalMisses)*100 + "%)"+ 
                     "\n   Miss de conflito: \t"+ missConflito + "\t(" +arredondar2((float) missConflito/totalMisses)*100 + "%)" + 
                     "\n   Miss de capacidade: \t" + missCapacidade + "\t(" + arredondar2((float) missCapacidade/totalMisses)*100 + "%)\n" ;
        }
        return saida;
    }
}
