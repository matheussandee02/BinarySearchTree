import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    ArvoreBinariaBusca abb = new ArvoreBinariaBusca();
    String nomeArquivo = "src/main/java/letraMusica.txt";

    Scanner scanner = new Scanner(System.in);
    int opcao = 0;

    do {
      exibirMenu();
      opcao = scanner.nextInt();

      switch (opcao) {
        case 1:
          carregarTexto(abb, nomeArquivo);
          System.out.println("Texto Carregado");
          break;
        case 2:
          int[] dados = abb.contarPalavras();
          System.out.println("Número total de palavras: " + dados[0]);
          System.out.println("Total de linhas no texto: " + dados[1]);
          System.out.println("Total de palavras distintas no texto: " + dados[2]);
          System.out.println("Palavra mais longa do texto: " + dados[3]);
          break;
        case 3:
          System.out.print("Digite a palavra que será buscada: ");
          String palavraBusca = scanner.next();
          int ocorrencias = abb.buscarPalavra(palavraBusca);
          if (ocorrencias > 0) {
            System.out.println("A palavra '" + palavraBusca + "' aparece " + ocorrencias + " vezes.");
          } else {
            System.out.println("A palavra '" + palavraBusca + "' não existe.");
          }
          break;
        case 4:
          System.out.println("Conteúdo da árvore em ordem alfabética:");
          abb.exibeEmOrdem();
          break;
        case 5:
          System.out.println("A frequência de cada letra no texto:");
          abb.contarFrequenciaLetras();
        case 6:
          System.out.println("Programa encerrado.");
          break;
        default:
          System.out.println("Opção digitada não existe, digite novamente.");
      }
    } while (opcao != 6);
    scanner.close();
  }

  public static void exibirMenu() {
    System.out.println("---- MENU ----");
    System.out.println("1- Carregar o texto");
    System.out.println("2- Contador de palavras");
    System.out.println("3- Busca por palavra");
    System.out.println("4- Exibição do texto");
    System.out.println("5- Frequência de letras:");
    System.out.println("6- Encerrar");
    System.out.print("Escolha uma opção: ");
  }

  public static void carregarTexto(ArvoreBinariaBusca abb, String nomeArquivo) {
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(nomeArquivo));
      String linha;
      int totalLinhas = 0;
      Map<String, Boolean> palavrasDistintas = new HashMap<>();
      String palavraMaisLonga = "";

      while ((linha = br.readLine()) != null) {
        totalLinhas++;
        linha = linha.replaceAll("[^a-zA-Z\\s]", ""); // Remove pontuações
        linha = linha.toLowerCase(); // Converte para letras minúsculas

        String[] palavras = linha.split("\\s+"); // Divide a linha em palavras separadas por espaços

        for (String palavra : palavras) {
          if (!palavra.isEmpty()) {
            palavrasDistintas.put(palavra, true);
            if (palavra.length() > palavraMaisLonga.length()) {
              palavraMaisLonga = palavra;
            }
            abb.inserir(new Palavra(palavra));
          }
        }
      }

      int totalPalavrasDistintas = palavrasDistintas.size();
      abb.setDadosContagem(totalLinhas, totalPalavrasDistintas, palavraMaisLonga);
    } catch (IOException e) {
      System.out.println("Erro ao ler o arquivo.");
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          System.out.println("Erro ao fechar o BufferedReader.");
        }
      }
    }
  }
}
