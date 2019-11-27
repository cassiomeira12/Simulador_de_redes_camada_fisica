/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: CamadaFisicaReceptora
* Funcao: Manipular os Bits de um vetor de inteiros e Decodificalo
***********************************************************************/

package model.camadas;


import view.Painel;
import view.componentes.Grafico;
import java.util.concurrent.Semaphore;

public class CamadaFisicaReceptora {

  public static Semaphore semaphoro;//Semaphoro para aguardar o recebimento de todos os Bits

  /*********************************************
  * Metodo: camadaFisicaReceptora
  * Funcao: Manipular os Bits um vetor de inteiros e codificalo
  * Parametros: quadro : int[]
  * Retorno: void
  *********************************************/
  public static void camadaFisicaReceptora(int[] fluxoBrutoDeBitsPontoB) {
    semaphoro = new Semaphore(0);
    int velocidade = 300;
    System.out.println("CAMADA FISICA RECEPTORA");
    try {

      Painel.CAMADAS_RECEPTORAS.expandirCamadaFisica();
      Painel.CAMADAS_RECEPTORAS.limparTextoCamadas();
      Painel.CAMADAS_RECEPTORAS.camadaFisica("Recebendo Bits\n");

      //Bits enviado do Meio de Comunicacao
      int[] fluxoBrutoDeBits = fluxoBrutoDeBitsPontoB;

      //Trava essa Thread de Camadas ate que passa todos os Bits pelo Grafico
      Painel.GRAFICO.semaphoroFim.acquire();

      

      System.out.println("\n\t***Bits Brutos Manipulados CODIFICADO***");
      Painel.CAMADAS_RECEPTORAS.camadaFisica("\n\nBits Brutos Manipulados [CODIFICADO]\n");
      Thread.sleep(velocidade);
      for (int b : fluxoBrutoDeBits) {
        System.out.print("\t");
        Painel.CAMADAS_RECEPTORAS.camadaFisica(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      }
      System.out.println();

      //Recebendo o tipo de codificacao escolhida pelo Grafico
      Grafico.Codificacao tipoDeCodificacao = Painel.GRAFICO.codificacaoSelecionada();

      switch (tipoDeCodificacao) {
        case CODIFICACAO_BINARIA:
          Painel.CAMADAS_RECEPTORAS.camadaFisica("\n[DECODIFICACAO BINARIA]\n");
          fluxoBrutoDeBits = decodificacaoBinaria(fluxoBrutoDeBits);//DECOFICACAO BINARIA
          break;
        case CODIFICACAO_MANCHESTER:
          Painel.CAMADAS_RECEPTORAS.camadaFisica("\n[DECODIFICACAO MANCHESTER]\n");
          fluxoBrutoDeBits = decodificacaoManchester(fluxoBrutoDeBits);//DECOFICACAO MANCHESTER
          break;
        case CODIFICACAO_MANCHESTER_DIFERENCIAL:
          Painel.CAMADAS_RECEPTORAS.camadaFisica("\n[DECODIFICACAO MANCHESTER DIFERENCIAL]\n");
          fluxoBrutoDeBits = decodificacaoManchesterDiferencial(fluxoBrutoDeBits);//DECOFICACAO MANCHESTER DIFERENCIAL
          break;
      }

      System.out.println("\n\t***Bits Brutos Manipulados DECODIFICADOS***");
      Painel.CAMADAS_RECEPTORAS.camadaFisica("\nBits Brutos Manipulados DECODIFICADOS\n");
      Thread.sleep(velocidade);
      for (int b : fluxoBrutoDeBits) {
        System.out.print("\t");
        Painel.CAMADAS_RECEPTORAS.camadaFisica(imprimirBits(b) + "\n");
        Thread.sleep(velocidade);
      }
      System.out.println();

      //Manipulando o vetor de Bits para um vetor de Inteiros
      int[] quadro = bitsParaInteiros(fluxoBrutoDeBits);//Manipulando os Bits

      System.out.println("\n\tBits de cada Inteiro");
      Painel.CAMADAS_RECEPTORAS.camadaFisica("\nBits de Cada Inteiro\n\n");
      Thread.sleep(velocidade);
      for (int c : quadro) {
        System.out.print("\tInteiro ["+c+"] - ");
        Painel.CAMADAS_RECEPTORAS.camadaFisica("["+c+"] - " + imprimirBits(c) + "\n");
        Thread.sleep(velocidade);
      }
      System.out.println();

      CamadaAplicacaoReceptora.camadaAplicacaoReceptora(quadro);

    } catch (InterruptedException e) {
      System.out.println("[ERRO] - Camada Fisica Receptora");
    }
  }

  /*********************************************
  * Metodo: bitsParaInteiros
  * Funcao: Converter um vetor de inteiros com os Bits Manipulados para um vetor de inteiros
  * Parametros: vetorDeBits : int[]
  * Retorno: int[] vetor de Inteiros
  *********************************************/
  public static int[] bitsParaInteiros(int[] vetorDeBits) {
    int adicionar = 0;//Numero de Inteiros pra adicionar ao Tamanho 
    int reduzir = 0;//Numero de Inteiros pra reduzir ao Tamanho
    int tamanho = vetorDeBits.length;//Tamanho do Vetor de Bits
    //Numero de Bits que o ultimo Inteiro do vetor possui
    int numeroDeBitsUltimoInteiro = Integer.toBinaryString(vetorDeBits[vetorDeBits.length - 1]).length();
    
    //Caso o ultimo numero do Vetor de Bits tiver menos de 32 bits
    //Descobrir quantos Bits tem nesse Interos
    if (numeroDeBitsUltimoInteiro <= 24) {
      
      if (numeroDeBitsUltimoInteiro <= 8) {//Caso tenha 8 bits ou 1 NUMERO
        adicionar += 1;//Adiciona 1 numero ao NOVO TAMANHO
      } else if (numeroDeBitsUltimoInteiro <= 16) {//Caso tenha 16 bits ou 2 NUMEROS
        adicionar += 2;//Adiciona 2 numeros ao NOVO TAMANHO
      } else if (numeroDeBitsUltimoInteiro <= 24) {//Caso tenha 24 bits ou 3 NUMEROS
        adicionar += 3;//Adiciona 3 numeros ao NOVO TAMANHO
      }

      reduzir = 1;//Reduz 1 unidade de tamanho na multiplicacao
    }

    //Calculando NOVO TAMANHO DO VETOR DE INTEIROS
    int novoTamanho = ((tamanho-reduzir) * 4) + adicionar;
    
    //Vetor que armazenas os Inteiros
    int[] vetorDeInteiros = new int[novoTamanho];

    //cria um valor inteiro com 1 no bit mais à esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    int posicaoI = 0;//Indice de posicao de Vetor de Inteiros

    //Percorrendo todo o Vetor de Inteiros com Bits manipulados
    for (int intBits : vetorDeBits) {
      //Inteiro com todos os bits 0s 
      int novoInteiro = 0;//00000000 00000000 00000000 00000000
      
      //Percorrendo os 32 bits do Inteiro
      for (int i = 1; i <= 32; i++) {
        // utiliza displayMask para isolar o bit
        int bit = (intBits & displayMask) == 0 ? 0 : 1;
        novoInteiro <<= 1;//Deslocando 1 bit para a esquerda
        novoInteiro = novoInteiro | bit;//Adicionando novo Bit ao Inteiro
        intBits <<= 1;//Deslocando 1 bit para a esquerda

        //Quando completar os 8 bits de um Inteiro
        if (i%8 == 0 && novoInteiro != 0) {
          vetorDeInteiros[posicaoI] = novoInteiro;//Adicionando no Vetor de Inteiros
          posicaoI++;//Aumentando 1 posicao no Vetor de Inteiros
          novoInteiro = 0;//Zerando bits para ser um novo Inteiro
        }
      }//Terminou de percorrer os 32 bits

    }//Terminou de percorrer o vetor

    return vetorDeInteiros;
  }

  /*********************************************
  * Metodo: decodificacaoBinaria
  * Funcao: Retorna um vetor de bits DECODIFICADOS
  * Parametros: quadro : int[]
  * Retorno: int[]
  *********************************************/
  private static int[] decodificacaoBinaria(int[] quadro) {
    System.out.println("\tDECODIFICACAO_BINARIA");

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
          System.out.print(" "); //Exibe espaço a cada 8 bits
        }

        //Terminou de adicionar os bits no novo Inteiro
        if (i == numeroDeBits) {
          System.out.print("\n\t\tNovo Inteiro: ");
          imprimirBits(novoInteiro);
          vetorCodificado[posicaoQuadro] = novoInteiro;
          System.out.print("\t\t");
        }
      }

      System.out.println();
      posicaoQuadro++;//Passando o proximo Numero
    }//Terminou de percorrer o vetor

    return vetorCodificado;
  }

  /*********************************************
  * Metodo: decodificacaoManchester
  * Funcao: Retorna um vetor de bits DECODIFICADOS
  * Parametros: quadro : int[]
  * Retorno: int[]
  *********************************************/
  private static int[] decodificacaoManchester(int[] quadro) {
    System.out.println("\tDECODIFICACAO_MANCHESTER");

    int adicionar = 0;//Numero de Inteiros pra adicionar ao Tamanho 
    int tamanho = quadro.length;//Tamanho do Vetor de Bits

    if (tamanho % 2 != 0) {//Tamanho impar
      adicionar++;//Aumenta uma unidade de tamanho
    }

    //Calculando o novo tamanho do vetor
    int novoTamanho = (tamanho)/2 + adicionar;

    //Vetor que armazena os inteiros com os bits decodificados
    int[] vetorDecodificado = new int[novoTamanho];
    //Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    int posicaoQuadro = 0;//Indice de posicao do Vetor Quadro
    int posicaoDecodificado = 0;//Indice de posicao do Vetor Codificado

    //Inteiro com todos os bits 0s
    int novoInteiro = 0;//00000000 00000000 00000000 00000000
    int bitsAdicionados = 0;//Bits que foram adicionados dentro do Novo Inteiro

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

      //Percorrendo todos os Bits do Vetor
      for (int i=1; i<=numeroDeBits; i+=2) {
        //Utiliza displayMask para isolar um Bit
        int bit1 = (numero & displayMask) == 0 ? 0 : 1;
        numero <<= 1;//Desloca 1 Bit para a esquerda
        int bit2 = (numero & displayMask) == 0 ? 0 : 1;
        numero <<= 1;//Desloca 1 Bit para a esquerda

        if (bit1 == 1 && bit2 == 0) {//Colocar 1
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 1;//Adicionando o bit [1]
          bitsAdicionados++;
        } else if (bit1 == 0 && bit2 == 1) {//Colocar 0
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 0;//Adicionando o bit [0]
          bitsAdicionados++;
        }
        
        //Caso ja tenha adicionado os 32 bits
        if (bitsAdicionados == 32) {
          vetorDecodificado[posicaoDecodificado] = novoInteiro;//Adicionando no Vetor
          System.out.println("\n\t\tBits Decodificado*********");
          System.out.print("\t\t");
          imprimirBits(novoInteiro);
          posicaoDecodificado++;
          bitsAdicionados = 0;//Zerando bits adicionados
          novoInteiro = 0;//Zerando os bits do Inteiro
        }
      }

      System.out.println();
      posicaoQuadro++;//Passando o proximo Numero
    }

    //Caso o novoInteiro nao teve seus 32 bits preenchidos
    if (novoInteiro != 0) {
      vetorDecodificado[posicaoDecodificado] = novoInteiro;//Adicionando no Vetor
      System.out.println("\n\t\tBits Decodificado*********");
      System.out.print("\t\t");
      imprimirBits(novoInteiro);
    }

    return vetorDecodificado;
  }

  /*********************************************
  * Metodo: decodificacaoManchesterDiferencial
  * Funcao: Retorna um vetor de bits DECODIFICADOS
  * Parametros: quadro : int[]
  * Retorno: int[]
  *********************************************/
  private static int[] decodificacaoManchesterDiferencial(int[] quadro) {
    System.out.println("\tDECODIFICACAO_MANCHESTER_DIFERENCIAL");

    int adicionar = 0;//Numero de Inteiros pra adicionar ao Tamanho 
    int tamanho = quadro.length;//Tamanho do Vetor de Bits

    if (tamanho % 2 != 0) {//Tamanho impar
      adicionar++;//Aumenta uma unidade de tamanho
    }

    //Calculando o novo tamanho do vetor
    int novoTamanho = (tamanho)/2 + adicionar;

    //Vetor que armazena os inteiros com os bits decodificados
    int[] vetorDecodificado = new int[novoTamanho];

    //Cria um valor inteiro com 1 no bit mais a esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
    int posicaoQuadro = 0;//Indice de posicao do Vetor Quadro
    int posicaoDecodificado = 0;//Indice de posicao do Vetor Codificado

    //Iniciando os niveis de Sinal como [ALTO | BAIXO] = 0
    boolean sinal1 = true;//Sinal definido como ALTO
    boolean sinal2 = false;//Sinal definido como BAIXO

    //Inteiro com todos os bits 0s
    int novoInteiro = 0;//00000000 00000000 00000000 00000000

    int bitsAdicionados = 0;//Bits que foram adicionados dentro do Novo Inteiro

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

      //Percorrendo todos os Bits do Vetor
      for (int i=1; i<=numeroDeBits; i+=2) {
        //Utiliza displayMask para isolar um Bit
        boolean bit1 = (numero & displayMask) == 0 ? false : true;
        numero <<= 1;//Desloca 1 Bit para a esquerda
        boolean bit2 = (numero & displayMask) == 0 ? false : true;
        numero <<= 1;//Desloca 1 Bit para a esquerda

        //Se o Par recebido for IGUAL ao anterior, significa que este e 0
        if (bit1 == sinal1 && bit2 == sinal2) {
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 0;//Adicionando o bit [0]
          bitsAdicionados++;
        } else if (bit1 != sinal1 && bit2 != sinal2) {//Se o Par recebido for DIFERENTE ao anterior, significa que este e 1
          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | 1;//Adicionando o bit [1]
          bitsAdicionados++;
        }

        sinal1 = bit1;//Atualizando Sinais para os proximos
        sinal2 = bit2;//Atualizando Sinais para os proximos

        //Caso ja tenha adicionado os 32 bits
        if (bitsAdicionados == 32) {
          vetorDecodificado[posicaoDecodificado] = novoInteiro;//Adicionando no Vetor
          System.out.println("\n\t\tBits Decodificado*********");
          System.out.print("\t\t");
          imprimirBits(novoInteiro);
          posicaoDecodificado++;
          bitsAdicionados = 0;
          novoInteiro = 0;//Zerando os bits do Inteiro
        }
      }

      System.out.println();
      posicaoQuadro++;//Passando o proximo Numero
    }

    if (novoInteiro != 0) {
      vetorDecodificado[posicaoDecodificado] = novoInteiro;//Adicionando no Vetor
      System.out.println("\n\t\tBits Decodificado*********");
      System.out.print("\t\t");
      imprimirBits(novoInteiro);
    }

    return vetorDecodificado;
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