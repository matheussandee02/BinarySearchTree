import java.util.Map;
import java.util.HashMap;

class ArvoreBinariaBusca {
  private Node raiz;
  private int totalLinhas;
  private int totalPalavrasDistintas;
  private String palavraMaisLonga;

  public void inserir(Palavra palavra) {
    raiz = inserirRec(raiz, palavra);
  }

  public Node inserirRec(Node raiz, Palavra palavra) {
    if (raiz == null) {
      raiz = new Node(palavra);
      return raiz;
    }

    int comparacao = palavra.palavra.compareTo(raiz.palavra.palavra);
    if (comparacao < 0) {
      raiz.esquerda = inserirRec(raiz.esquerda, palavra);
    } else if (comparacao > 0) {
      raiz.direita = inserirRec(raiz.direita, palavra);
    } else {
      raiz.palavra.ocorrencias++;
    }

    return raiz;
  }

  public void exibeEmOrdem() {
    exibeEmOrdemRec(raiz);
  }

  private void exibeEmOrdemRec(Node raiz) {
    if (raiz != null) {
      exibeEmOrdemRec(raiz.esquerda);
      System.out.println(raiz.palavra.palavra + " - " + raiz.palavra.ocorrencias);
      exibeEmOrdemRec(raiz.direita);
    }
  }

  public int[] contarPalavras() {
    int totalPalavras = contarPalavrasRec(raiz);
    return new int[] { totalPalavras, totalLinhas, totalPalavrasDistintas, palavraMaisLonga.length() };
  }

  private int contarPalavrasRec(Node raiz) {
    if (raiz == null) {
      return 0;
    }

    return 1 + contarPalavrasRec(raiz.esquerda) + contarPalavrasRec(raiz.direita);
  }

  public int buscarPalavra(String palavra) {
    return buscarPalavraRec(raiz, palavra);
  }

  private int buscarPalavraRec(Node raiz, String palavra) {
    if (raiz == null) {
      return 0;
    }

    int comparacao = palavra.compareTo(raiz.palavra.palavra);
    if (comparacao < 0) {
      return buscarPalavraRec(raiz.esquerda, palavra);
    } else if (comparacao > 0) {
      return buscarPalavraRec(raiz.direita, palavra);
    } else {
      return raiz.palavra.ocorrencias;
    }
  }

  public void setDadosContagem(int totalLinhas, int totalPalavrasDistintas, String palavraMaisLonga) {
    this.totalLinhas = totalLinhas;
    this.totalPalavrasDistintas = totalPalavrasDistintas;
    this.palavraMaisLonga = palavraMaisLonga;
  }

  public void contarFrequenciaLetras() {
    Map<Character, Integer> frequenciaLetras = new HashMap<>();
    contarFrequenciaLetrasRec(raiz, frequenciaLetras);

    for (char letra = 'a'; letra <= 'z'; letra++) {
      int frequencia = frequenciaLetras.getOrDefault(letra, 0);
      System.out.println("A letra '" + letra + "' aparece " + frequencia + " vezes.");
    }
  }

  private void contarFrequenciaLetrasRec(Node raiz, Map<Character, Integer> frequenciaLetras) {
    if (raiz != null) {
      for (char caractere : raiz.palavra.palavra.toCharArray()) {
        if (Character.isLetter(caractere)) {
          char lowercaseLetra = Character.toLowerCase(caractere);
          int frequencia = frequenciaLetras.getOrDefault(lowercaseLetra, 0);
          frequenciaLetras.put(lowercaseLetra, frequencia + 1);
        }
      }
      contarFrequenciaLetrasRec(raiz.esquerda, frequenciaLetras);
      contarFrequenciaLetrasRec(raiz.direita, frequenciaLetras);
    }
  }
}