/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: CamadaFisicaTransmissora
* Funcao: Manipular os Bits de um vetor de inteiros e Codificalo
***********************************************************************/

package model.camadas;


import view.Painel;
import view.componentes.Grafico;

public class CamadaFisicaTransmissora {


  /*********************************************
  * Metodo: camadaFisicaTransmissora
  * Funcao: Manipular os Bits um vetor de inteiros e codificalo
  * Parametros: quadro : int[]
  * Retorno: void
  *********************************************/
  public static void camadaFisicaTransmissora(int[] quadro) {
    int velocidade = 300;
    System.out.println("CAMADA FISICA TRANSMISSORA");
    try {

      Painel.CAMADAS_TRANSMISSORAS.expandirCamadaFisica();
      Painel.CAMADAS_TRANSMISSORAS.camadaFisica("Bits de Cada Inteiro\n\n");
      Thread.sleep(velocidade);
      System.out.println("\n\tBits de cada Inteiro");
      for (int c : quadro) {
        System.out.print("\tInteiro ["+c+"] - ");
        Painel.CAMADAS_TRANSMISSORAS.camadaFisica("["+c+"] - " + imprimirBits(c) + "\n");
        Thread.sleep(velocidade);
      }

      //Manipulando os Bits de cada inteiro para um novo vetor de Bits Bruto
      int[] fluxoBrutoDeBits = inteirosParaBits(quadro);//Manipulando os Bits

      System.out.println("\n\t***Bits Brutos Manipulados***");
      Thread.sleep(velocidade);
      Painel.CAMADAS_TRANSMISSORAS.camadaFisica("\nBits Brutos Manipulados\n");
      for (int b : fluxoBrutoDeBits) {
        System.out.print("\t");
        Painel.CAMADAS_TRANSMISSORAS.camadaFisica(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      }
      System.out.println();
      
      //Recebendo o tipo de codificacao escolhida pelo Grafico
      Grafico.Codificacao tipoDeCodificacao = Painel.GRAFICO.codificacaoSelecionada();
      
      switch (tipoDeCodificacao) {
        case CODIFICACAO_BINARIA:
          Painel.CAMADAS_TRANSMISSORAS.camadaFisica("\n[CODIFICACAO BINARIA]\n");
          fluxoBrutoDeBits = codificacaoBinaria(fluxoBrutoDeBits);//CODIFICACAO BINARIA
          break;
        case CODIFICACAO_MANCHESTER:
          Painel.CAMADAS_TRANSMISSORAS.camadaFisica("\n[CODIFICACAO MANCHESTER]\n");
          fluxoBrutoDeBits = codificacaoManchester(fluxoBrutoDeBits);//CODIFICACAO MANCHESTER
          break;
        case CODIFICACAO_MANCHESTER_DIFERENCIAL:
          Painel.CAMADAS_TRANSMISSORAS.camadaFisica("\n[CODIFICACAO MANCHESTER DIFERENCIAL]\n");
          fluxoBrutoDeBits = codificacaoManchesterDiferencial(fluxoBrutoDeBits);//CODIFICACAO MANCHESTER DIFERENCIAL
          break;
      }

      Thread.sleep(velocidade);
      Painel.CAMADAS_TRANSMISSORAS.camadaFisica("\nBits Brutos Manipulados [CODIFICADO]\n");
      Thread.sleep(velocidade);
      
      System.out.println("\n\t***Bits Brutos Manipulados CODIFICADO***");
      for (int b : fluxoBrutoDeBits) {
        System.out.print("\t");
        Painel.CAMADAS_TRANSMISSORAS.camadaFisica(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      }
      System.out.println();


      MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits);

    } catch (InterruptedException e) {
      System.out.println("[ERRO] - Camada Fisica Transmissora");
    }
  }

  /*********************************************
  * Metodo: inteirosParaBits
  * Funcao: Manipular um vetor de inteiros para um vetor de inteiros com os Bits manipulados
  * Parametros: vetorDeInteiros : int[]
  * Retorno: int[] : vetor com Bits manipulados
  *********************************************/
  public static int[] inteirosParaBits(int[] vetorDeInteiros) {
    //Definindo um novo tamanho para o Vetor com de bits manipulados
    //O novo tamanho do vetor sera o inteiro da divisao por 4
    //Pois em cada inteiro eh possivel colocar 4 bytes de 8 bits, ou seja, 4 numeros
    int novoTamanho = vetorDeInteiros.length/4;
    //Caso o Vetor tenha MAIS de 4 numeros o resto da divisao sera diferente de 0
    //E sera necessario adicionar mais uma posicao no tamanho do Vetor
    if (vetorDeInteiros.length % 4 != 0) {
      novoTamanho++;//Aumentando 1 posicao no tamanho do Vetor
    }
    
    //Vetor que armazena os Bits manipulados
    int[] vetorDeBits = new int[novoTamanho];//Vetor com os bits
      
    //int com todos os 32 bits 0s
    int novoBit = 0;//00000000 00000000 00000000 00000000
    int posicaoV = 0;//Indice de posicao do Vetor de INTEIROS
    int posicaoR = 0;//Indice de posicao do Vetor de BITS
      
    //Percorrendo todo o Vetor de Inteiros para pegar os respectivos Bits
    while (posicaoV < vetorDeInteiros.length) {
      novoBit <<= 8;//Deslocando 8 bits para a Esquerda
      novoBit = novoBit | vetorDeInteiros[posicaoV];//Adicionando os bits do Vetor ao novoBit
      
      //Verificando se ja adicionou os 32 bits no Inteiro
      if ((posicaoV+1) % 4 == 0) {
        vetorDeBits[posicaoR] = novoBit;//Adicionando o Inteiro com 32 bits no Vetor de Bits
        novoBit = 0;//Limpando novoBit para colocar os bits do outros numeros
        posicaoR++;//Aumentando o indice do Vetor Resultado em 1 posicao
      }

      posicaoV++;//Passando para o proximo Inteiro
    }//Fim do While

    //Caso o novoBit nao tenha preenchido seus 32 bits sera diferente de zero
    if (novoBit != 0) {
      vetorDeBits[posicaoR] = novoBit;//Adicionando o Inteiro com os bits no Vetor de Bits
    }

    return vetorDeBits;
  }

  /*********************************************
  * Metodo: codificacaoBinaria
  * Funcao: Retorna um vetor de bits na CODIFICACAO Binaria
  * Parametros: quadro : int[]
  * Retorno: int[]
  *********************************************/
  private static int[] codificacaoBinaria(int[] quadro) {
    System.out.println("\tCODIFICACAO_BINARIA");

    int[] vetorCodificado = new int[quadro.length];

    //Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    int posicaoQuadro = 0;//Indice de posicao do Vetor Quadro
    int posicaoCodificado = 0;//Indice de posicao do Vetor Codificado

    //Percorrendo todo o Vetor de Inteiros para pegar os respectivos Bits
    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];//Numero do qual sera copiado os Bits
      
      int numeroDeBits = Integer.toBinaryString(numero).length();//Quantidade de Bits que o inteiro possui
      System.out.println("\t\tNumero de Bits " + numeroDeBits);

      if (numeroDeBits <= 8) {        //Arredondando o numero de Bits para 8
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {//Arredondando o numero de Bits para 16
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {//Arredondando o numero de Bits para 24
        numeroDeBits = 24;
      } else if (numeroDeBits <= 32) {//Arredondando o numero de Bits para 32
        numeroDeBits = 32;
      }

      System.out.println("\t\tNumero Arredondando " + numeroDeBits);
      System.out.println("\t\tDeslocar " + (32-numeroDeBits) + " bits a esquerda");

      numero <<= (32-numeroDeBits);//Deslocando um valor de Bits para a esquerda
      System.out.println("\t\tBits do numero: ");
      System.out.print("\t\t");
      imprimirBits(numero);

      System.out.println("\n\t\tBit a Bit do Numero");
      System.out.print("\t\t");

      //Inteiro com todos os bits 0s
      int novoInteiro = 0;//00000000 00000000 00000000 00000000

      //Percorrendo todos os Bits do Vetor
      for (int i=1; i<=numeroDeBits; i++) {
        //Utiliza displayMask para isolar um Bit
        int bit = (numero & displayMask) == 0 ? 0 : 1;
        System.out.print(bit);

        novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
        novoInteiro = novoInteiro | bit;//Adicionando novo Bit ao Inteiro
        numero <<= 1;//Desloca 1 Bit para a esquerda

        if (i % 8 == 0) {
          System.out.print(" ");//Exibe espaço a cada 8 bits
        }

        //Terminou de adicionar os bits no novo Inteiro
        if (i == numeroDeBits) {
          System.out.print("\n\t\tNovo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoQuadro] = novoInteiro;//Adicionando no vetor
          System.out.print("\t\t");
        }
      }

      System.out.println();
      posicaoQuadro++;//Passando o proximo Numero
    }//Terminou de percorrer o vetor

    return vetorCodificado;
  }

  /*********************************************
  * Metodo: codificacaoManchester
  * Funcao: Retorna um vetor de bits na CODIFICACAO Manchester
  * Parametros: quadro : int[]
  * Retorno: int[]
  *********************************************/
  private static int[] codificacaoManchester(int[] quadro) {
    System.out.println("\tCODIFICACAO_MANCHESTER");

    int reduzir = 0;//Numero de Inteiros pra reduzir ao Tamanho
    int tamanho = quadro.length;//Tamanho do Vetor de Bits
    //Numero de Bits que o ultimo Inteiro de vetor possui
    int numeroDeBitsUltimoInteiro = Integer.toBinaryString(quadro[quadro.length - 1]).length();
    
    if (numeroDeBitsUltimoInteiro <= 16) {
      reduzir = 1;
    }

    //Calculando o novo tamanho do vetor
    int novoTamanho = (quadro.length*2) - reduzir;

    //Vetor que armazena os inteiros com os bits codificados
    int[] vetorCodificado = new int[novoTamanho];

    //Cria um valor inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    int posicaoQuadro = 0;//Indice de posicao do Vetor Quadro
    int posicaoCodificado = 0;//Indice de posicao do Vetor Codificado

    //Inteiro com todos os bits 0s
    int novoInteiro = 0;//00000000 00000000 00000000 00000000

    //Percorrendo todo o Vetor de Inteiros para pegar os respectivos Bits
    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];//Numero do qual sera copiado os Bits
      
      int numeroDeBits = Integer.toBinaryString(numero).length();//Quantidade de Bits que o inteiro possui
      System.out.println("\t\tNumero de Bits " + numeroDeBits);

      if (numeroDeBits <= 8) {        //Arredondando o numero de Bits para 8
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {//Arredondando o numero de Bits para 16
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {//Arredondando o numero de Bits para 24
        numeroDeBits = 24;
      } else if (numeroDeBits <= 32) {//Arredondando o numero de Bits para 32
        numeroDeBits = 32;
      }

      System.out.println("\t\tNumero Arredondando " + numeroDeBits);
      System.out.println("\t\tDeslocar " + (32-numeroDeBits) + " bits a esquerda");

      numero <<= (32-numeroDeBits);//Deslocando um valor de Bits para a esquerda
      System.out.println("\t\tBits do numero: ");
      System.out.print("\t\t");
      imprimirBits(numero);

      System.out.println("\n\t\tBit a Bit do Numero");
      System.out.print("\t\t");

      //Percorrendo todos os Bits do Vetor
      for (int i=1; i<=numeroDeBits; i++) {
        //Utiliza displayMask para isolar um Bit
        int bit = (numero & displayMask) == 0 ? 0 : 1;

        if (bit == 1) {//Colocar 10
          System.out.print("10");
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 1;//Adicionando o bit [1]
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 0;//Adicionando o bit [0]
        } else if (bit == 0) {//Colocar 01
          System.out.print("01");
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 0;//Adicionando o bit [0]
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 1;//Adicionando o bit [1]
        }

        numero <<= 1;//Desloca 1 Bit para a esquerda

        if (i % 4 == 0) {
          System.out.print(" ");
        }

        if (i == 16) {//Verificando se percorreu a metade dos bits do Inteiro
          System.out.print("\n\t\tNovo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;//Adicionando no Vetor
          novoInteiro = 0;//Zerando os bits do Inteiro
          posicaoCodificado++;
          System.out.println("\t\tBit a Bit do Numero");
          System.out.print("\t\t");
        
        } else if (i == numeroDeBits) {//Terminou de adicionar os bits no novo Inteiro
          System.out.print("\n\t\tNovo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;//Adicionando no Vetor
          novoInteiro = 0;//Zerando os bits do Inteiro
          posicaoCodificado++;
          System.out.print("\t\t");
        }
      }

      System.out.println();
      posicaoQuadro++;//Passando o proximo Numero
    }

    return vetorCodificado;
  }

  /*********************************************
  * Metodo: codificacaoManchesterDiferencial
  * Funcao: Retorna um vetor de bits na CODIFICACAO Manchester Diferencial
  * Parametros: quadro : int[]
  * Retorno: int[]
  *********************************************/
  private static int[] codificacaoManchesterDiferencial(int[] quadro) {
    System.out.println("\tCODIFICACAO_MANCHESTER_DIFERENCIAL");

    int reduzir = 0;//Numero de Inteiros pra reduzir ao Tamanho
    int tamanho = quadro.length;//Tamanho do Vetor de Bits
    //Numero de Bits que o ultimo Inteiro de vetor possui
    int numeroDeBitsUltimoInteiro = Integer.toBinaryString(quadro[quadro.length - 1]).length();
    
    if (numeroDeBitsUltimoInteiro <= 16) {
      reduzir = 1;
    }

    //Calculando o novo tamanho do vetor
    int novoTamanho = (quadro.length*2) - reduzir;

    //Vetor que armazena os inteiros com os bits codificados
    int[] vetorCodificado = new int[novoTamanho];

    //Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    int posicaoQuadro = 0;//Indice de posicao do Vetor Quadro
    int posicaoCodificado = 0;//Indice de posicao do Vetor Codificado

    //Iniciando os niveis de Sinal como [ALTO | BAIXO] = 0
    boolean sinal1 = true;//Sinal definido como ALTO
    boolean sinal2 = false;//Sinal definido como BAIXO

    //Inteiro com todos os bits 0s
    int novoInteiro = 0;//00000000 00000000 00000000 00000000

    //Percorrendo todo o Vetor de Inteiros para pegar os respectivos Bits
    while (posicaoQuadro < quadro.length) {
      int numero = quadro[posicaoQuadro];//Numero do qual sera copiado os Bits
      
      int numeroDeBits = Integer.toBinaryString(numero).length();//Quantidade de Bits que o inteiro possui
      System.out.println("\t\tNumero de Bits " + numeroDeBits);

      if (numeroDeBits <= 8) {        //Arredondando o numero de Bits para 8
        numeroDeBits = 8;
      } else if (numeroDeBits <= 16) {//Arredondando o numero de Bits para 16
        numeroDeBits = 16;
      } else if (numeroDeBits <= 24) {//Arredondando o numero de Bits para 24
        numeroDeBits = 24;
      } else if (numeroDeBits <= 32) {//Arredondando o numero de Bits para 32
        numeroDeBits = 32;
      }

      System.out.println("\t\tNumero Arredondando " + numeroDeBits);
      System.out.println("\t\tDeslocar " + (32-numeroDeBits) + " bits a esquerda");

      numero <<= (32-numeroDeBits);//Deslocando um valor de Bits para a esquerda
      System.out.println("\t\tBits do numero: ");
      System.out.print("\t\t");
      imprimirBits(numero);

      System.out.println("\n\t\tBit a Bit do Numero");
      System.out.print("\t\t");

      //Percorrendo todos os Bits do Vetor
      for (int i=1; i<=numeroDeBits; i++) {
        //Utiliza displayMask para isolar um Bit
        int bit = (numero & displayMask) == 0 ? 0 : 1;

        if (bit == 0) {//BIT 0 INVERTE O NIVEL DE SINAL
          sinal1 = sinal1;//Invertendo o sinal do Nivel Anterior
          sinal2 = sinal2;//Invertendo o sinal do Nivel Anterior
        } else if (bit == 1) {//BIT 1 MANTEM O NIVEL DE SINAL
          sinal1 = !sinal1;//Mantendo o sinal do Nivel Anterior
          sinal2 = !sinal2;//Mentando o sinal do Nivel Anterior 
        }

        //VERIFICANDO OS NIVEIS DE SINAIS
        if (sinal1 && !sinal2) {//Colocar 10
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 1;//Adicionando o bit [1]
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 0;//Adicionando o bit [0]
        } else if (!sinal1 && sinal2) {//Colocar 01
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 0;//Adicionando o bit [0]
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 1;//Adicionando o bit [1]
        }

        numero <<= 1;//Desloca 1 Bit para a esquerda

        if (i == 16) {//Verificando se percorreu a metade dos bits do Inteiro
          System.out.print("\n\t\tNovo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;//Adicionando no Vetor
          novoInteiro = 0;//Zerando os bits do Inteiro
          posicaoCodificado++;
          System.out.println("\t\tBit a Bit do Numero");
          System.out.print("\t\t");
        
        } else if (i == numeroDeBits) {//Terminou de adicionar os bits no novo Inteiro
          System.out.print("\n\t\tNovo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoCodificado] = novoInteiro;//Adicionando no Vetor
          novoInteiro = 0;//Zerando os bits do Inteiro
          posicaoCodificado++;
          System.out.print("\t\t");
        }
      }

      System.out.println();
      posicaoQuadro++;//Passando o proximo Numero
    }

    return vetorCodificado;
  }

  /*********************************************
  * Metodo: imprimirBits
  * Funcao: Imprime na tela os Bits de um numero Inteiro
  * Parametros: numero : int
  * Retorno: void
  *********************************************/
  public static String imprimirBits(int numero) {
    String bits = "";
    //Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    //Para cada bit exibe 0 ou 1
    for (int bit=1; bit<=32; bit++) {
      //Utiliza displayMask para isolar o bit
      System.out.print((numero & displayMask) == 0 ? '0' : '1');
      bits += (numero & displayMask) == 0 ? '0' : '1';
      numero <<= 1;//Desloca o valor uma posicao para a esquerda
      if ( bit % 8 == 0 ) {
        System.out.print(" ");//Exibe espaco a cada 8 bits
        bits += " ";
      }
    }
    System.out.println();
    return bits;
  }

}//Fim class
