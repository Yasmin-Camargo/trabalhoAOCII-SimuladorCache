
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
            
            nsets = 2;    //número de conjuntos na cache
            bsize = 1;      //tamanho do bloco
            assoc = 8;      //grau de associatividade
            subst = "L";    //política de substituição
            flagOut = 1;
            arquivoEntrada = "C:\\Users\\carol\\Documents\\NetBeansProjects\\Cache_Simulator\\src\\cache_simulator\\Enderecos\\bin_100.bin";
            
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
           
            /* 4294967295;
            
            cache1.alocaEnderecoCache(999999999);   //miss compulsório
            cache1.alocaEnderecoCache(100);         //miss compulsório
            cache1.alocaEnderecoCache(999999999);   //hit
            cache1.alocaEnderecoCache(33333333);    //miss conflito
            cache1.alocaEnderecoCache(100);         //hit
            cache1.alocaEnderecoCache(999999999);   //hit
            cache1.alocaEnderecoCache(509867000);   //miss conflito
            cache1.alocaEnderecoCache(999999999);   //miss conflito
            cache1.alocaEnderecoCache(999999999);   //hit
            System.out.println(cache1); */
            /*
            
            System.out.println(byteArquivo);
                if (cont == 1){
                    byte1 = byteArquivo;
                } else if (cont == 2){
                    byte2 = byteArquivo;
                }else if (cont == 3){
                    byte3 = byteArquivo;
                }else if (cont == 4){
                    byte4 = byteArquivo;
                    cont = 0;
                    cache1.alocaEnderecoCache(byte1, byte2, byte3,byte4);
                }
                cont++;
            
            String linha;
            int num = arquivoLeitura.read();
            
            System.out.println(Integer.parseInt(arquivoLeitura.readLine(), 2));
            
            
            while ((linha = arquivoLeitura.readLine()) != null) { //lê de linha em linha
                System.out.println(linha);
            }
            arquivoLeitura.close();
            */
        } catch (IOException e) {
            System.out.println("\nErro: ao manipular o arquivo");
        }

        // Seu codigo vai aqui
    }
}
