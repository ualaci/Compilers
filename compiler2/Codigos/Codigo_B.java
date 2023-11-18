import java.util.HashMap;
import java.util.Map;
public class Tabelas {
Map<String, Variaveis> Mapa = new HashMap<String, Variaveis>(); /* Fator
de ampliação do tamanho do "vetor", 16, o fator 0,75 --> 16*0,75=12, o
tamanho vai para 32*/
public void insere(String chave, String valor, String Tipo){
Variaveis Objeto = new Variaveis();
Objeto.setNome(chave);
Objeto.setTipo(Tipo);
Objeto.setValor(valor);
Mapa.put(chave, Objeto);
}
//////////////////////////////////////
public void imprime (){
System.out.println("Chave Valor Tipo ");
for (String key :Mapa.keySet()) {
//Capturamos o valor e o tipo a partir da chave
String value = Mapa.get(key).getValor();
String tipo = Mapa.get(key).getTipo();
System.out.println(key + " " + value + " "+ tipo);
}
}
///////////////////////////////////
public String pesquisa_valor (String chave){
String Nome=Mapa.get(chave).getValor();
return Nome;
}
////////////////////////////////////
public String pesquisa_tipo (String chave){
String Nome=Mapa.get(chave).getTipo();
return Nome;
}
}