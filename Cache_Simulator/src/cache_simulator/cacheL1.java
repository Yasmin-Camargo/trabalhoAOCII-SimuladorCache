package cache_simulator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * @author Caroline e Yasmin
 */
public class cacheL1 {
    //Atributos
    private final int nsets, bsize, assoc, size, nblocks, n_bits_offset, n_bits_indice, n_bits_tag, flagOut;
    private final String subst;
    private int [][]cache_val, cache_tag;
    private int missCompulsorio, missConflito, missCapacidade, hit, acessos;
    
    private Queue[] fila;       //fila para politica de substituição FIFO
    
    //Construtor
    public cacheL1(int nsets, int bsize, int assoc, String subst, int flagOut) {
        this.nsets = nsets;     //número de conjuntos na cache
        this.bsize = bsize;     //tamanho do bloco
        this.assoc = assoc;     //grau de associatividade
        this.subst = subst;     //política de substituição: Random (R), FIFO (F) ou L (LRU)
        this.flagOut = flagOut; //forma como os dados serão exibidos
        
        nblocks = this.nsets * this.assoc;  //quantidade de blocos
        size = nblocks * this.bsize;        //tamanho total da cache (em bytes) = quantidade de blocos x tamanho do bloco
        
        n_bits_offset = (int) ((Math.log(this.bsize)) / Math.log(2) + 1e-10); //Forma de calcular um logaritimo de base 2, devido à aritmética de ponto flutuante imprecisa é acrescentado 1e-10
        n_bits_indice = (int) (Math.log(this.nsets) / Math.log(2) + 1e-10);
        n_bits_tag = 32 - n_bits_offset - n_bits_indice;
        
        cache_val = new int[nsets][assoc];       //matriz que armazena o bit de validade
        cache_tag = new int[nsets][assoc];       //matriz que armazena a tag
        
        if(subst.equals("F") || subst.equals("f") || subst.equals("L") || subst.equals("l")){
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
        if (indice > 0){ 
            indice--;
        }
        //System.out.println("binario: "+endereco+" \ttag:"+tag + "\tindice" + indice);
        
        int posicaoLivre = -1, bitControle = -1;    // 1 -> endereço está na cache
                                                    // 2 -> endereco não está na cache, mas tem linhas vagas
                                                    // 3 -> endereco não está na cache, mas não tem espaço vazio
        
        for (int i = 0; i < assoc; i++) {           //percorre as linhas/vias da cache para um determinado indice/conjunto
            if (cache_val[indice][i] == 0 && posicaoLivre == -1) { //caso a posição encontrada não tenha sido preenchida ainda
                bitControle = 2;
                posicaoLivre = i;
            } else if (cache_tag[indice][i] == tag){ //caso o endereço requisitado esteja na cache   
                hit++;
                bitControle = 1;
                atualizaAcesso(indice, i);
                break;
            } 
        }
        
        if (bitControle == 2) { //endereco não está na cache, mas tem linhas vagas
            missCompulsorio++;
            cache_val[indice][posicaoLivre] = 1;
            cache_tag[indice][posicaoLivre] = tag;
            addHistorico(indice, posicaoLivre); //adiciona em uma estrutura de dados, caso se tenha trabalhando com fifo ou lru
            
        } else if(bitControle != 1){    //caso tenha ocorrido um miss de capacidade ou de conflito
            if (assoc == 1) {           //mapeamento direto
                missConflito++;
                cache_val[indice][0] = 1;
                cache_tag[indice][0] = tag;
            } else if(nsets != 1){     //mapeamento conjunto associativo
                if (verificaLinhasLivres()) {
                    missConflito++;
                } else {
                    missCapacidade++;
                }
                politicaSubstituicao(indice, tag);
                
            } else {                    //mapeamento totalmente associativo
                missCapacidade++;
                politicaSubstituicao(indice, tag);
            }
        }
    }
    
    //Verifica se a cache já está totalmente preenchida
    private boolean verificaLinhasLivres(){
        for (int i = 0; i < nblocks; i++) {
            for (int j = 0; j < assoc; j++) {
                if (cache_val[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Salva a ordem dos indices em uma estrutura de dados, caso esteja se implementando fifo ou lru
    private void addHistorico(int indice, int pos){
        if (subst.equals("F") || subst.equals("f") || subst.equals("L") || subst.equals("l")) {       //fifo
            fila[indice].add(pos);                          //salva a posição do elemento da fila
        } 
    }
    
    //determina qual dado sairá da cache (de acordo com a politica de substitução)
    private void politicaSubstituicao(int indice, int tag){
        if (subst.equals("F") || subst.equals("f") || subst.equals("L") || subst.equals("l")) {       //fifo ou lru
            int linhaRemovida = (int) fila[indice].remove();//indice do dado que está a mais tempo na cachce 
            cache_tag[indice][linhaRemovida] = tag;
            cache_val[indice][linhaRemovida] = 1;
            fila[indice].add(linhaRemovida);
            
        } else if(subst.equals("R") || subst.equals("r")){  //ramdom
            Random random = new Random();
            int numSorteado = random.nextInt(assoc); //gera um numero aleatório (indice) entre 0 e o número de vias - 1
            cache_tag[indice][numSorteado] = tag;
            cache_val[indice][numSorteado] = 1;
            
        } else {
            System.err.println("Erro: Politica de substituicao invalida");
            System.exit(1);
        }
    }
    
    private void atualizaAcesso(int indice, int idhit) {
        int novoacesso = -1;
        if (subst.equals("L") || subst.equals("l")){
            if (!fila[indice].isEmpty()) {          //verifica se a fila contem algum elemento
                int tamanho = fila[indice].size();
                for (int i = 0; i < tamanho; i++) { //manda todo mundo para tras da fila, exceto o elemento que deu hit  
                    int linhaRemovida = (int) fila[indice].remove();//indice do dado que está a mais tempo na cache
                    if (linhaRemovida == idhit){
                        novoacesso = linhaRemovida;
                    } else {
                        fila[indice].add(linhaRemovida);
                    }
                }
                if (novoacesso == -1) {
                    System.out.println("deu ruim");
                } else {
                    fila[indice].add(novoacesso);       //adiciona o elemento que deu hit no fim da fila
                }
            }
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
            saida = "\n\n" + acessos + 
                    ", " + arredondar(((float) hit/acessos)) + 
                    ", " + arredondar((float) totalMisses/acessos) + 
                    ", " + arredondar2((float) missCompulsorio/totalMisses) + 
                    ", " + arredondar2((float) missCapacidade/totalMisses) + 
                    ", " + arredondar2((float) missConflito/totalMisses);
            
        } else {            //formato livre
            saida = "\n\n--------------- INFORMACOES MEMORIA CACHE ---------------";
            saida += "\nTamanho: "  + size +"\t\t\t\t\t(em bytes)" +
                     "\nNumero de conjuntos: " + nsets + 
                     "\nGrau de associtividade: " + assoc ;
            if(assoc == 1) {
                saida += " (mapeamento direto)";
            } else if(nsets == 1){
                saida += " (mapeamento totalmente associativo)";
            } else {
                saida += " (mapeamento associativo por conjunto)";
            }
            saida += "\nQuantidade de blocos: " + nblocks + 
                     "\nBits indice: " + n_bits_indice + 
                     "\nBits ofsset: " + n_bits_offset + "\nBits tag: " + n_bits_tag +
                     "\n--------------- TAXA DE HITS E MISSES ---------------\n" +
                     "\nAcessos:" + acessos +"\nHit:" + arredondar((float) hit/acessos) + 
                     "\nMisses: "+ arredondar((float)(totalMisses)/acessos) + 
                     "\nMiss compulsorio: " + arredondar((float) missCompulsorio/totalMisses) + 
                     "\nMiss de conflito: "+ arredondar((float) missConflito/totalMisses) + 
                     "\nMiss de capacidade: " + arredondar((float) missCapacidade/totalMisses);
        }
        return saida;
    }
}
