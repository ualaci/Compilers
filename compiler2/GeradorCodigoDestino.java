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
arqSaida.write(".limit stack 20\r\n"); // limite da pilha
arqSaida.write(".limit locals 100\r\n"); /* máximo de variáveis locais (deve ser calculado) */
arqSaida.write(processaListaComandos(listaComandos)); /* Aqui que são obtidos os resultados! */
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
referenciaDaVariavel =
CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);

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
saida += "l2i" + "\r\n";
saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
}
// se o comando é Relacional (>/</==/...)
if(com.getTipo() == 'I') { // I - de IF
codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));
saida += codigoExpressao;
System.out.println((LinkedList<Item>)(com.getRef2()));
}
// se o comando é While
if(com.getTipo() == 'W') { // W - de WHILE
codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));
saida += codigoExpressao;
System.out.println((LinkedList<Item>)(com.getRef2()));
}
// se o comando é For
if(com.getTipo() == 'F') { // F - de FOR
codigoExpressao = geraCodigoExpressao((LinkedList<Item>)(com.getRef2()));
saida += codigoExpressao;
System.out.println((LinkedList<Item>)(com.getRef2()));
}
}
return saida;
}
static String geraCodigoExpressao(LinkedList<Item> listaExp) {
String saida = "", var_impressao="";
Item item, _item;
String nomeDaVariavel="", nomeDaVariavel_alfa="";
int referenciaDaVariavel=0, referenciaDaVariavel_alfa=0; //TEM de INICIALIZAR
for(int i = 0; i < listaExp.size(); i++) {
item = listaExp.get(i);
// se é um número
if(item.getTipo() == 'n') saida += "ldc2_w " + item.getvalor() + "\r\n";
// se é uma variável
if(item.getTipo() == 'v') {

nomeDaVariavel = item.getvalor();
referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
saida += "lload " + referenciaDaVariavel + "\r\n"; /* dload - Carrega um doube de uma
variavél local */
}
// se é um operador de adição
if(item.getTipo() == 'o') saida += "ladd "+ "\r\n"; /*dadd - comando de adição de duble - dsub é comando de
subtração de double*/

// se é marcador do laço for para identificar o que deve ser impresso dentro do laço
if (item.getTipo()=='e'){
    var_impressao=item.getvalor();
}
// W/ v-p, v-q, w-<, v-p, h-++
//se é uma comparação do laço while ou for
if( (item.getTipo() == 'w') || (item.getTipo() == 'f') ) { //Primeira parada para resolver os elementos que estão na pilha
    // Já existem dois elmentos na pilha - p, q, por exemplo
    saida += "lstore "+referenciaDaVariavel+ " \r\n";
    //Para pegar o elemento anterior
    _item = listaExp.get(i-2);
    nomeDaVariavel = _item.getvalor();//p
    nomeDaVariavel_alfa = _item.getvalor(); // É a variável que deverá ser atualizada no for
    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
    saida += "lstore "+referenciaDaVariavel+ " \r\n"; // Armazena a primeira variável da comparação
    if (item.getTipo() == 'w') //ramificação do while
        saida += "ramif1: "+ " \r\n";
    else if (item.getTipo() == 'f' ) //ramficação do for
        saida += "ram1: "+ " \r\n";
    //Para carregar o primeiro elemento da comparação
    _item = listaExp.get(i-2);
    nomeDaVariavel = _item.getvalor();//p
    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
    saida += "lload "+ referenciaDaVariavel +" \r\n";
    //Para carregar o segundo elemento da comparação
    _item = listaExp.get(i-1);
    nomeDaVariavel = _item.getvalor();//q
    referenciaDaVariavel = CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
    saida += "lload "+ referenciaDaVariavel +" \r\n";
    saida += "lcmp "+" \r\n";
    if (item.getvalor().equals("<") && (item.getTipo() == 'w') )
        saida += "ifge ramif2 "+ " \r\n"; //se for o laço while
    else if ( item.getTipo()== 'f' )
        saida += "ifgt ram2 "+ " \r\n";

}
//Se é marcador do laço for
if ( item.getTipo()== 'f' ){ //Aqui estará uma nova variável na pilha
referenciaDaVariavel_alfa =
CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel_alfa);
/* Segue a ram1, foi dividido para "esperar" desempilhar a lista de elementos a próxima variável
*/
//Atualiza o i do for
saida+= "lload "+referenciaDaVariavel_alfa + " \r\n";
// Somente para imprimir antes de atualizar o i dentro do for
referenciaDaVariavel =
CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(var_impressao);
saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
saida += "lload " + referenciaDaVariavel + " \r\n";
saida += "l2i " + " \r\n";
saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
//Agora aplica-se o incremento/decremento (++/--)
saida +="lconst_1 "+ " \r\n"; //apenas para carregar a constante 1 que irá somar/subtrair na
variável i
if ( item.getvalor().equals("--") )
saida+="lsub "+ " \r\n";
else
saida+="ladd "+ " \r\n";
saida+="lstore "+referenciaDaVariavel_alfa + " \r\n";
saida+="goto ram1 "+ " \r\n"; //Volta para a ramificação 1 que realiza a verificação de permanência
ou não no laço FOR
saida+="ram2: "+ " \r\n"; // Sai do laço for
saida+="nop "+ " \r\n"; // Nenhuma operação
}
//Se é novo marcador do laço while
if (item.getTipo()=='h'){ //Aqui estará uma nova variável na pilha
referenciaDaVariavel =
CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
/* Segue a ramif1, foi dividido para "esperar" desempilhar da lista de elementos a próxima
variável */
//Somente para imprimir antes de atualizar a variável dentro do laço while
saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
saida += "lload " + referenciaDaVariavel + " \r\n";
saida += "l2i " + " \r\n";
saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
//Agora aplica-se o incremento/++ ou decremento/--
saida += "lconst_1 "+" \r\n"; // apenas para carregar a constante 1, a qual será adicionada ou
subtraída na variável atual da pilha
if ( item.getvalor().equals("--") )
saida += "lsub "+" \r\n";
else
saida += "ladd "+" \r\n";

saida += "lstore " + referenciaDaVariavel + " \r\n"; // Armazena o valor atualizado, ou seja, p++, ou
p--
saida += "goto ramif1 "+" \r\n"; // Volta para a ramificação 1 que realiza a verificação da
permanência ou não no laço while
saida += "ramif2: "+" \r\n";//sai do laço while
saida += "nop " +" \r\n";//Não realiza qualquer ação
}
//se é uma comparação
if(item.getTipo() == 'c') { //Primeira parada para resolver os elmentos que estão na pilha
saida += "lcmp" + " \r\n"; // Compara longs
if (item.getvalor().equals("=="))
saida += "ifeq ramificacao1" + " \r\n";
else if (item.getvalor().equals(">="))
saida += "ifge ramificacao1" + " \r\n";
else if (item.getvalor().equals(">"))
saida += "ifgt ramificacao1" + " \r\n";
else if (item.getvalor().equals("<="))
saida += "ifle ramificacao1" + " \r\n";
else if (item.getvalor().equals("<"))
saida += "iflt ramificacao1" + " \r\n";
else // É diferente (!=)
saida += "ifne ramificacao1" + " \r\n";
}
//se é ramificação do else
if(item.getTipo() == 'i') {
referenciaDaVariavel =
CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
saida += "lconst_1 " + " \r\n"; // Garrega na pilha o número 1
if (item.getvalor().equals ("--"))
saida += "lsub " + " \r\n";
else
saida += "ladd " + " \r\n";
saida += "lstore " + referenciaDaVariavel + " \r\n";
saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
saida += "lload " + referenciaDaVariavel + " \r\n";
saida += "l2i " + " \r\n";
saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
saida += "goto ramificacao2 " + " \r\n";
saida += "ramificacao1: " + " \r\n";
}
// se é ramificação do if
if(item.getTipo() == 'r') {
referenciaDaVariavel =
CompiladorExemploIntermediarioTotal.tabela.consultaReferencia(nomeDaVariavel);
/* Segue a ramificacao1, pois foi dividido para "esperar" desempilhar da lista de elementos a
variável seguinte */
saida += "lconst_1 " + " \r\n"; // Garrega na pilha o número 1
if (item.getvalor().equals ("--"))
saida += "lsub " + " \r\n";

else
saida += "ladd " + " \r\n";
saida += "lstore " + referenciaDaVariavel + " \r\n";
saida += "getstatic java/lang/System/out Ljava/io/PrintStream;\r\n";
saida += "lload " + referenciaDaVariavel + " \r\n";
saida += "l2i " + " \r\n";
saida += "invokevirtual java/io/PrintStream/println(I)V\r\n";
//Situação do goto
saida += "ramificacao2: " + " \r\n";
saida += "nop "+ " \r\n";
}
}
return saida;
}
}

/*
Locais para buscar comandos opcode java / comandos bytecode interpretados pela JVM - São comandos de
baixo nível/assembler:
https://javaalmanac.io/bytecode/
https://en.wikipedia.org/wiki/List_of_Java_bytecode_instructions
java -jar jasmin.jar prog_destino.j
java prog_destino
*/