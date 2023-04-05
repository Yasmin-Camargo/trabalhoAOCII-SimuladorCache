
package cache_simulator;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author Caroline e Yasmin
 */

public class cache_simulator {
    public static void main(String[] args) {
        int nsets, bsize, assoc, flagOut;
        String arquivoEntrada, subst;
        
        if (args.length != 6) {
            /*System.out.println(args.length);
            System.out.println("Numero de argumentos incorreto. Utilize:");
            System.out.println("java cache_simulator <nsets> <bsize> <assoc> <substituição> <flag_saida> arquivo_de_entrada");
            System.exit(1);*/
            
            nsets = 1;    //número de conjuntos na cache
            bsize = 4;      //tamanho do bloco
            assoc = 32;      //grau de associatividade
            subst = "l";    //política de substituição
            flagOut = 1;
            arquivoEntrada = "C:\\Users\\carol\\Documents\\NetBeansProjects\\Cache_Simulator\\src\\cache_simulator\\Enderecos\\vortex.in.sem.persons.bin";
            
        } else {
            nsets = Integer.parseInt(args[0]);
            bsize = Integer.parseInt(args[1]);
            assoc = Integer.parseInt(args[2]);
            subst = args[3];
            flagOut = Integer.parseInt(args[4]);
            arquivoEntrada = args[5];
        }
        
        System.out.printf("nsets = %d\n", nsets);
        System.out.printf("bsize = %d\n", bsize);
        System.out.printf("assoc = %d\n", assoc);
        System.out.printf("subst = %s\n", subst);
        System.out.printf("flagOut = %d\n", flagOut);
        System.out.printf("arquivo = %s\n", arquivoEntrada);
        
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
            
        } catch (IOException e) {
            System.out.println("\nErro: ao manipular o arquivo");
        }
    }
}
