
package cache_simulator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author Caroline Camargo e Yasmin Camargo
 * --- Trabalho de Implementação de um Simulador de Caches ---
 *  Disciplina Arquitetura e Organização de Computadores II
 *  Prof. Marcelo Schiavon Porto
 **/

public class cache_simulator {
    public static void main(String[] args) {
        int nsets, bsize, assoc, flagOut;
        String arquivoEntrada, subst;
        
        frameCache frame = new frameCache();
        frame.setVisible(true);
        
        if (args.length != 6) {
            /*System.out.println(args.length);
            System.out.println("Numero de argumentos incorreto. Utilize:");
            System.out.println("java cache_simulator <nsets> <bsize> <assoc> <substituição> <flag_saida> arquivo_de_entrada");
            System.exit(1);*/
            
            nsets = 2;    //número de conjuntos na cache
            bsize = 1;      //tamanho do bloco
            assoc = 8;      //grau de associatividade
            subst = "l";    //política de substituição
            flagOut = 0;
            arquivoEntrada = "..\\Cache_Simulator\\src\\cache_simulator\\Enderecos\\bin_100.bin";
            
        } else {
            nsets = Integer.parseInt(args[0]);
            bsize = Integer.parseInt(args[1]);
            assoc = Integer.parseInt(args[2]);
            subst = args[3];
            flagOut = Integer.parseInt(args[4]);
            arquivoEntrada = args[5];
        }
        
        execucao(nsets, bsize, assoc, subst, flagOut, arquivoEntrada, frame);
        frame.inicializaComponentes(nsets, bsize, assoc, subst, flagOut, arquivoEntrada);
    }
    public static void execucao(int nsets, int bsize, int assoc, String subst, int flagOut, String arquivoEntrada, frameCache frame){
        cacheL1 cache1 = new cacheL1(nsets, bsize, assoc, subst, flagOut);
        
        //Abre o arquivo
        File arquivo = new File(arquivoEntrada);
        if (!arquivo.exists()) {
            System.out.println("\nErro: falha ao abrir o arquivo");
        }
                
        try {
            FileInputStream arquivoLeitura = new FileInputStream(arquivo); 
            byte[] b = new byte[4];                                     //vetor que irá armazenar 4 bytes
            while (arquivoLeitura.read(b) != -1) {                      //lê de 4 em 4 bytes o arquivo
                //System.out.println(ByteBuffer.wrap(b).getInt()+" ");
                cache1.alocaEnderecoCache(ByteBuffer.wrap(b).getInt()); //manda o número inteiro correspondente ao 4 bytes
            }
            
            System.out.println(cache1); 
            frame.atualizaLog(cache1.toString());
            
        } catch (IOException e) {
            System.out.println("\nErro: ao manipular o arquivo");
        }
    }
}
