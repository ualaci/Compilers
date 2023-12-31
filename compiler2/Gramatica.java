/* Gramatica.java */
/* Generated By:JavaCC: Do not edit this line. Gramatica.java */
    import java.io.*;
    public class Gramatica implements GramaticaConstants {
    static Tabela tabela = new Tabela();
    public static void main(String args[]) throws ParseException {
    Gramatica analisador = null;
    try {
    analisador = new Gramatica(new FileInputStream("Entrada.txt"));
    Gramatica.inicio();
    System.out.println(tabela.toString());
    System.out.println("Analise lexica, sintatica e semantica sem erros!");
    }
    catch(FileNotFoundException e) {
    System.out.println("Erro: arquivo n\u00c3\u00a3o encontrado");
    }
    catch(TokenMgrError e) {
    System.out.println("Erro l\u00c3\u00a9xico\u005cn" + e.getMessage());
    }
    catch(ParseException e) {
    System.out.println("Erro sint\u00c3\u00a1tico\u005cn" + e.getMessage());
    }
    }

  static final public void inicio() throws ParseException {
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case EXIBE:
      case IDENT:{
        ;
        break;
        }
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      linhaComando();
    }
    jj_consume_token(0);
  }

  static final public void linhaComando() throws ParseException {
    comando();
    jj_consume_token(PT_VIRG);
  }

  static final public void comando() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXIBE:{
      exibe();
      break;
      }
    case IDENT:{
      atribui();
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void exibe() throws ParseException {
    jj_consume_token(EXIBE);
    jj_consume_token(IDENT);
  }

  static final public void atribui() throws ParseException {Simbolo simb; Token t;
    t = jj_consume_token(IDENT);
simb = new Simbolo(t.image);
    tabela.inclui(simb);
    jj_consume_token(ATRIB);
    expressao();
  }

  static final public void expressao() throws ParseException {
    fator();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ADICAO:{
        ;
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        break label_2;
      }
      jj_consume_token(ADICAO);
      fator();
    }
  }

  static final public void fator() throws ParseException {Token t;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IDENT:{
      t = jj_consume_token(IDENT);
if(!tabela.isExiste(t.image))
    System.out.println("Erro sem\u00c3\u00a2ntico \u005cn A vari\u00c3\u00a1vel "+t.image+
    "n\u00c3\u00a3o foi inicializada");
      break;
      }
    case UM:
    case DOIS:
    case TRES:
    case QUATRO:
    case CINCO:
    case SEIS:
    case SETE:
    case OITO:
    case NOVE:
    case DEZ:
    case VINTE:
    case TRINTA:{
      numero();
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void numero() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case DEZ:
    case VINTE:
    case TRINTA:{
      dezena();
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case UM:
      case DOIS:
      case TRES:
      case QUATRO:
      case CINCO:
      case SEIS:
      case SETE:
      case OITO:
      case NOVE:{
        unidade();
        break;
        }
      default:
        jj_la1[4] = jj_gen;
        ;
      }
      break;
      }
    case UM:
    case DOIS:
    case TRES:
    case QUATRO:
    case CINCO:
    case SEIS:
    case SETE:
    case OITO:
    case NOVE:{
      unidade();
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void unidade() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case UM:{
      jj_consume_token(UM);
      break;
      }
    case DOIS:{
      jj_consume_token(DOIS);
      break;
      }
    case TRES:{
      jj_consume_token(TRES);
      break;
      }
    case QUATRO:{
      jj_consume_token(QUATRO);
      break;
      }
    case CINCO:{
      jj_consume_token(CINCO);
      break;
      }
    case SEIS:{
      jj_consume_token(SEIS);
      break;
      }
    case SETE:{
      jj_consume_token(SETE);
      break;
      }
    case OITO:{
      jj_consume_token(OITO);
      break;
      }
    case NOVE:{
      jj_consume_token(NOVE);
      break;
      }
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void dezena() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case DEZ:{
      jj_consume_token(DEZ);
      break;
      }
    case VINTE:{
      jj_consume_token(VINTE);
      break;
      }
    case TRINTA:{
      jj_consume_token(TRINTA);
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public GramaticaTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x60,0x60,0x100,0x3ffc40,0x7fc00,0x3ffc00,0x7fc00,0x380000,};
   }

  /** Constructor with InputStream. */
  public Gramatica(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Gramatica(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new GramaticaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Gramatica(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new GramaticaTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Gramatica(GramaticaTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(GramaticaTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk_f() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[22];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 22; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

    }
