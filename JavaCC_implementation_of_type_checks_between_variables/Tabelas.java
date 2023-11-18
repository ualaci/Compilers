import java.util.HashMap;
import java.util.Map;

public class Tabelas {

Map <String, Variaveis> Mapa = new HashMap <String, Variaveis>();
//Fator de ampliação do tamanho do vetor, 16, há um fator 0,75

public void insere (String chave, String valor, String tipo)
{
	Variaveis Objeto = new Variaveis();
	Objeto.setNome(chave);
	Objeto.setTipo(tipo);
	Objeto.setValor(valor);

	Mapa.put(chave, Objeto);

}
public void imprime()
{
	System.out.println ("Chave	Valor	Tipo");
	
	for(String key:  Mapa.keySet()  ){
	   //Captura o valor e o tipo a partir da chave
	String value = Mapa.get(key).getValor();
	String tipo= Mapa.get(key).getTipo();
	System.out.println(key +"	"+ value + "	"+ tipo);


	}

}

public String pesquisa_valor (String chave)
{
	String Nome = Mapa.get(chave).getValor();
return Nome;

}

public String pesquisa_tipo (String chave)
{
	String tipo = Mapa.get(chave).getTipo();
return tipo;

}


}