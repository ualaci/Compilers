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

            catch(IOException e) {System.out.println("Problema no arquivo 'prog_destino.j'");}
            catch(Exception e) {System.out.println(e.getMessage());}
      }

      static String processaListaComandos(LinkedList<Comando> listaComandos) {
            String output = "";
            String variableName;
            String codigoExpressao;
            Comando com;
            int Vreference;
            int resultComparacao;
            
            for(int i = 0; i < listaComandos.size(); i++) {
                  com = (Comando)listaComandos.get(i);
                  variableName = (String)com.getRef1();
                  Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);

                  // se o comando é uma atribuição
                  if(com.getTipo() == 'A') {
                        codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));
                        output += codigoExpressao;
                        output += "lstore " + Vreference + "\r\n";
                  }

                  // se o comando é uma exibição
                  if(com.getTipo() == 'E') {
                        output += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        output += "lload " + Vreference + "\r\n";
                        output += "l2i" + Vreference + "\r\n";
                        output += "invokevirtual java/io/PrintStream/println(I)V\r\n";
                  }

                // se o comando é Relacional (>/</==/...)
                  if(com.getTipo() == 'I') { // I - de IF
                      codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));                
                      output += codigoExpressao;  
                      System.out.println((LinkedList<Item>)(com.getRef2()));
                  }

                  // se o comando é While 
                  if(com.getTipo() == 'W') { // W - de WHILE
                      codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));                        
                      output += codigoExpressao;  
                      System.out.println((LinkedList<Item>)(com.getRef2()));
                  }

                  if(com.getTipo() == 'F'){ // F - de FOR
                      codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));                        
                      output += codigoExpressao;  
                      //System.out.println((LinkedList<Item>)(com.getRef2()));
                  }                  
            }
            return output;
      }

      static String geraCodigoExpressao(LinkedList<Item> listaExp) {
            Item item, _item;
            String output = "";
            String variableName="";
            String variable2 ="";
            int Vreference = 0;
            int Vreference2 = 0;

            for(int i = 0; i < listaExp.size(); i++) {
                  item = listaExp.get(i);

                  // caso seja número
                  if(item.getTipo() == 'n') output += "ldc2_w " + item.getvalor() + "\r\n";
                        

                   // caso seja uma variável
                  if(item.getTipo() == 'v') {
                        variableName = item.getvalor();
                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);
                        output += "lload " + Vreference + "\r\n"; /* dload - Carrega um doube de uma variavél local  */
                  }
                 
                // caso seja um operador de adição
                  if(item.getTipo() == 'o')
                        output += "ladd"+ "\r\n"; /*dadd - comando de adição de duble  - dsub é comando de subtração de double*/

                  // W/v-p, v-q, w-<, v-p, h-++
                  // F/v-i, v-q, f++, e-i
                  //Tratando caso do for e while simultaneos
                 if(item.getTipo() == 'w' || (item.getTipo() == 'f')) {   //Primeira parada para resolver os elementos que estão na pilha

                        if(item.getTipo() == 'w') output += "lstore " + Vreference + "\r\n";
                        
                        // pegando o elemento anterior
                        _item = listaExp.get(i-2);
                        variableName = _item.getvalor();

                        // primeiro indice do for:   for(i to ....)7
                        variable2 = _item.getvalor();

                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);

                        if(item.getTipo() == 'w') output += "lstore " + Vreference + "\r\n"; // para armazenar a primeira variavel da comparacao
                        if(item.getTipo() == 'f') output += "ram1:" + "\r\n";
                        if(item.getTipo() == 'w') output += "ramif1: " + "\r\n";

                        //Para carregar o primeiro elemento da comparacao
                        _item = listaExp.get(i-2);
                        variableName = _item.getvalor(); //p
                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);
                        output += "lload "+ Vreference + "\r\n";

                        //Para carregar o segundo elemento da comparacao
                        _item = listaExp.get(i-1);
                        variableName = _item.getvalor(); //q
                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);
                        output += "lload "+ Vreference + "\r\n";
                        output+= "lcmp " + "\r\n";

                        // caso a comparacao seja realizada dentro de um while
                        if(item.getTipo()=='w' && item.getvalor().equals("<")) output+= "ifge ramif2" + "\r\n";
                        
                        // caso a comparacao seja realizada dentro de um for
                        else if(item.getTipo()=='f') output+= "ifgt ram2" + "\r\n";

                 }

                 // Se eh novo marcador do laço while
                 if(item.getTipo()=='h'){ // Aqui tera uma nova variavel na pilha
                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);
                        /* Segue a ramif1, foi dividido para esperar desempilhar da lista de elementos a proxima variavel*/

                        //sequencia de comandos reponsavel por imprimir antes de atualizar a variavel dentro do laco while
                        output += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        output += "lload "+ Vreference + "\r\n";
                        output += "l2i " + "\r\n";
                        output += "invokevirtual java/io/PrintStream/println(I)V\r\n";

                        //Agora aplicamos o incremento h++

                        output += "lconst_1 " + "\r\n"; // Apenas para carregar a constante 1, que sera add ou subtraida na variavel atual da pilha

                        if(item.getvalor().equals("--")) output += "lsub" + "\r\n";
                        else output += "ladd" + "\r\n";

                        output += "lstore " + Vreference + "\r\n"; //Armazena o valor atualizado da variavel (p++ ou p--)
                        output += "goto ramif1 " + "\r\n"; // Volta para a ramif1 que realiza a verificacao da permanencia do while
                        output += "ramif2: " + "\r\n"; 
                        output += "nop " + "\r\n"; // output do laco while
                 }


                 // v-i     v-num     f-aditivo  marcador do laco for
                 if(item.getTipo() == 'f'){
                        
                        Vreference2 = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variable2);
                        output += "lload "+ Vreference2 + "\r\n";

                        output += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        output += "lload "+ Vreference2 + "\r\n";
                        output += "l2i " + "\r\n";
                        output += "invokevirtual java/io/PrintStream/println(I)V\r\n";
                        
                        //Tratando incremento
                        output += "lconst_1 " + "\r\n";

                        if(item.getvalor().equals("--"))
                              output += "lsub" + "\r\n";
                        else
                              output += "ladd" + "\r\n";

                        output += "lstore " + Vreference2 + "\r\n"; //Armazena o valor atualizado da variavel (p++ ou p--)

                        //verificando a permanencia no laco for
                        output += "goto ram1 " + "\r\n";
                        output += "ram2:" + "\r\n";
                        output += "nop" + "\r\n";
                  }

                 //caso seja uma comparação
                 if(item.getTipo() == 'c') {   //Primeira parada para resolver os elmentos que estão na pilha
                  System.out.println(item.getvalor());

                        output+= "lcmp" + "\r\n"; // Compara Longs

                        if(item.getvalor()=="==") output+= "ifeq ramificacao1" + "\r\n"; //

                        else if(item.getvalor().equals(">=")) output+= "ifge ramificacao1" + "\r\n"; //

                        else if(item.getvalor().equals(">"))  output+= "ifgt ramificacao1" + "\r\n"; //

                        else if(item.getvalor().equals("<=")) output+= "ifle ramificacao1" + "\r\n"; //

                        else if(item.getvalor().equals("<")) output+= "iflt ramificacao1" + "\r\n"; //

                        else if(item.getvalor().equals("!=")) output+= "ifne ramificacao1" + "\r\n"; //
                 }        

                  //caso seja ramificação do else
                  if(item.getTipo() == 'i') {   
                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);                                    
               	      output += "lconst_1 " + "\r\n";

                        if(item.getvalor().equals("--")) output+= "lsub " + "\r\n";

                        else
                        
                        output += "ladd " + "\r\n";
                        output += "lstore " + Vreference + "\r\n";
                        output += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        output += "lload "+ Vreference + "\r\n";
                        output += "l2i " + "\r\n";
                        output += "invokevirtual java/io/PrintStream/println(I)V\r\n";
                        output += "goto ramificacao2 " + "\r\n";
                        output += "ramificacao1: " + "\r\n";
                 }   
  
                  // caso seja ramificação do if
                  if(item.getTipo() == 'r') {   
                        Vreference = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(variableName);                             
                        /* Segue a ramificacao1, pois foi dividido para "esperar" desempilhar da lista de elementos variavel seguinte */
                        output += "lconst_1 " + "\r\n";

                        if(item.getvalor().equals("--")) output+= "lsub " + "\r\n";
                        else output+= "ladd " + "\r\n";

                        output += "lstore " + Vreference + "\r\n";
                        output += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
                        output += "lload "+ Vreference + "\r\n";
                        output += "l2i " + "\r\n";
                        output += "invokevirtual java/io/PrintStream/println(I)V\r\n";

                        // Caso do GoTo

                        output += "ramificacao2: " + "\r\n";
                        output += "nop" + "\r\n";
                  }  
            }
            return output;
      }
}


/* 
Locais para buscar comandos opcode java / comandos bytecode interpretados pela JVM - São comandos de baixo nível/assembler:
https://javaalmanac.io/bytecode/
https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions

java -jar jasmin.jar prog_destino.j
java prog_destino
   */