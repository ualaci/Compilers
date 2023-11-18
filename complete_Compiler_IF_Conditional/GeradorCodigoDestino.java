import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class GeradorCodigoDestino {
      public static void geraCodigoAssembler(LinkedList<Comando> listaComandos) {             
            BufferedWriter arqSaida;
            
            try {                 
                  arqSaida = new BufferedWriter(new FileWriter("prog_destino.j"));	
                  arqSaida.write(".source prog_destino.java\r\n");	
                  arqSaida.write(".class public prog_destino\r\n");
                  arqSaida.write(".super java/lang/Object\r\n");
                  arqSaida.write(".method public <init>()V\r\n");
                  arqSaida.write(".limit stack 1\r\n");
                  arqSaida.write(".limit locals 1\r\n");
                  arqSaida.write("aload_0\r\n");
                  arqSaida.write("invokespecial java/lang/Object/<init>()V\r\n");
                  arqSaida.write("return\r\n");
                  arqSaida.write(".end method\r\n");
                  arqSaida.write(".method public static main([Ljava/lang/String;)V\r\n");
                  arqSaida.write(".limit stack 20\r\n");  // limite da pilha
                  arqSaida.write(".limit locals 100\r\n"); /* máximo de variáveis locais (deve ser calculado) */	
                  
	arqSaida.write(processaListaComandos(listaComandos)); /* Aqui que são obtidos os resultados!  */
	
                  arqSaida.write("return\r\n");
                  arqSaida.write(".end method\r\n");
	
                  arqSaida.close();
	
                  
            }
            catch(IOException e) {
                  System.out.println("Problema no arquivo 'prog_destino.j'");
            }
            catch(Exception e) {
                  System.out.println(e.getMessage());
            }
      }

      static String processaListaComandos(LinkedList<Comando> listaComandos) {
            String saida = "";
            Comando com;
            String nomeDaVariavel, codigoExpressao;
            int referenciaDaVariavel;
            int resultComparacao;
            
            for(int i = 0; i < listaComandos.size(); i++) {
                  com = (Comando)listaComandos.get(i);
                  nomeDaVariavel = (String)com.getRef1();
                  referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);

                  // se o comando é uma atribuição
                  if(com.getTipo() == 'A') {
                        codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));
                        saida += codigoExpressao;
                        saida += "lstore " + referenciaDaVariavel + "\r\n";
                  }

                  // se o comando é uma exibição
                  if(com.getTipo() == 'E') {
                        saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        saida += "lload " + referenciaDaVariavel + "\r\n";
                        saida += "l2i" + referenciaDaVariavel + "\r\n";
                        saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
                  }

                // se o comando é Relacional (>/</==/...)
                  if(com.getTipo() == 'I') { // I - de IF
                      codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));                
                      saida += codigoExpressao;  
                      System.out.println((LinkedList<Item>)(com.getRef2()));
                  }
            }
            return saida;
      }
      static String geraCodigoExpressao(LinkedList<Item> listaExp) {
            String saida = "";
            Item item;
            String nomeDaVariavel="";
            int referenciaDaVariavel;
            for(int i = 0; i < listaExp.size(); i++) {
                  item = listaExp.get(i);

                  // se é um número
                  if(item.getTipo() == 'n')
                        saida += "ldc2_w " + item.getvalor() + "\r\n";

                   // se é uma variável
                  if(item.getTipo() == 'v') {
                         nomeDaVariavel = item.getvalor();
                         referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
                        saida += "lload " + referenciaDaVariavel + "\r\n"; /* dload - Carrega um doube de uma variavél local  */
                  }
                 
                // se é um operador de adição
                  if(item.getTipo() == 'o')
                        saida += "ladd"+ "\r\n"; /*dadd - comando de adição de duble  - dsub é comando de subtração de double*/
                 
                 //se é uma comparação
                 if(item.getTipo() == 'c') {   //Primeira parada para resolver os elmentos que estão na pilha
                  System.out.println(item.getvalor());
                        saida+= "lcmp" + "\r\n"; // Compara Longs
                        if(item.getvalor()=="==")
                              saida+= "ifeq ramificacao1" + "\r\n"; //
                        else if(item.getvalor().equals(">="))
                              saida+= "ifge ramificacao1" + "\r\n"; //
                        else if(item.getvalor().equals(">"))
                              saida+= "ifgt ramificacao1" + "\r\n"; //
                        else if(item.getvalor().equals("<=")) 
                              saida+= "ifle ramificacao1" + "\r\n"; //
                        else if(item.getvalor().equals("<")) 
                              saida+= "iflt ramificacao1" + "\r\n"; //
                        else if(item.getvalor().equals("!=")) 
                              saida+= "ifne ramificacao1" + "\r\n"; //
                 }        

                  //se é ramificação do else
                  if(item.getTipo() == 'i') {   
                        referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);                                    
               	      saida += "lconst_1 " + "\r\n";
                        if(item.getvalor().equals("--"))
                              saida+= "lsub " + "\r\n";
                        else
                        
                        saida+= "ladd " + "\r\n";
                        saida += "lstore " + referenciaDaVariavel + "\r\n";
                        saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        saida += "lload "+ referenciaDaVariavel + "\r\n";
                        saida += "l2i " + "\r\n";
                        saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
                        saida += "goto ramificacao2 " + "\r\n";
                        saida += "ramificacao1: " + "\r\n";
                 }   
  
                  // se é ramificação do if
                  if(item.getTipo() == 'r') {   
                        referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);                             
                        /* Segue a ramificacao1, pois foi dividido para "esperar" desempilhar da lista de elementos variavel seguinte */
                        saida += "lconst_1 " + "\r\n";
                        if(item.getvalor().equals("--"))
                              saida+= "lsub " + "\r\n";
                        else
                              saida+= "ladd " + "\r\n";

                        saida += "lstore " + referenciaDaVariavel + "\r\n";
                        saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        saida += "lload "+ referenciaDaVariavel + "\r\n";
                        saida += "l2i " + "\r\n";
                        saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";

                        // Situacao do goto

                        saida += "ramificacao2: " + "\r\n";
                        saida += "nop" + "\r\n";
                  }  
            }
            return saida;
      }
}


/* 
Locais para buscar comandos opcode java / comandos bytecode interpretados pela JVM - São comandos de baixo nível/assembler:
https://javaalmanac.io/bytecode/
https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions

java -jar jasmin.jar prog_destino.j
java prog_destino
   */