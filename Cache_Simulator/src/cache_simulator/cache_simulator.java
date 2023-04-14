
package cache_simulator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.swing.JOptionPane;

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
        
        frameCache frame = new frameCache(); //interface gráfica
        
        if (args.length != 6) {
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
        
        if (flagOut == 1){  //execução pelo terminal
            System.out.println( execucao(nsets, bsize, assoc, subst, flagOut, arquivoEntrada));
            System.exit(0);
        } else{ //execução pela interface gráfica
            frame.inicializaComponentes(nsets, bsize, assoc, subst, flagOut, arquivoEntrada);
            frame.atualizaLog(execucao(nsets, bsize, assoc, subst, flagOut, arquivoEntrada));
            frame.setVisible(true);
        }  
    }
    
    public static String execucao(int nsets, int bsize, int assoc, String subst, int flagOut, String arquivoEntrada){
        cacheL1 cache1 = new cacheL1(nsets, bsize, assoc, subst, flagOut);
        
        //Abre o arquivo
        File arquivo = new File(arquivoEntrada);
        if (!arquivo.exists()) {
            if (flagOut == 0){
                JOptionPane.showMessageDialog(null, "Erro: falha ao abrir o arquivo");
            } else{
                System.out.println("\nErro: falha ao abrir o arquivo");
            }
        } else{
            try {
                FileInputStream arquivoLeitura = new FileInputStream(arquivo);
                byte[] b = new byte[4];                                     //vetor que irá armazenar 4 bytes
                while (arquivoLeitura.read(b) != -1) {                      //lê de 4 em 4 bytes o arquivo
                    //System.out.println(ByteBuffer.wrap(b).getInt()+" ");
                    cache1.alocaEnderecoCache(ByteBuffer.wrap(b).getInt()); //manda o número inteiro correspondente ao 4 bytes
                }
                arquivoLeitura.close();
            } catch (IOException e) {
                System.out.println("\nErro: ao manipular o arquivo");
            }
            return(cache1.toString());
        }
         return("");
    }
}
