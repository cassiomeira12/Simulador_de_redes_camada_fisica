/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: MeioDeComunicacao
* Funcao: Receber os Bits da Camada de Fisica Transmissora e enviar
          para a Camada Fisica Receptora
***********************************************************************/

package model.camadas;

import view.Painel;


public class MeioDeComunicacao {


  /*********************************************
  * Metodo: meioDeComunicacao
  * Funcao: Simular um meio por onde os Bits passam ate chegar na proxima camada
  * Parametros: fluxoBrutoDeBits : int[]
  * Retorno: void
  *********************************************/
  public static void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    System.out.println("\nMEIO DE COMUNICACAO\n");
    try {

      int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
      int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBits.length];

      //System.out.println("ENVIANDO BITS:\n");
      //ENVIANDO OS BITs PARA O GRAFICO
      for (int indicePosicao=0; indicePosicao<fluxoBrutoDeBitsPontoA.length; indicePosicao++) {//Percorrendo o vetor de Inteiros
        int numero = fluxoBrutoDeBitsPontoA[indicePosicao];//Numero com os Bits
        int numeroDeBits = Integer.toBinaryString(numero).length();//Quantidade de Bits que o inteiro possui

        if (numeroDeBits <= 8) {        //Arredondando o numero de Bits para 8
          numeroDeBits = 8;
        } else if (numeroDeBits <= 16) {//Arredondando o numero de Bits para 16
          numeroDeBits = 16;
        } else if (numeroDeBits <= 24) {//Arredondando o numero de Bits para 24
          numeroDeBits = 24;
        } else if (numeroDeBits <= 32) {//Arredondando o numero de Bits para 32
          numeroDeBits = 32;
        }

        numero <<= (32-numeroDeBits);//Deslocando um valor de Bits para a esquerda

        //Inteiro com todos os bits 0s
        int novoInteiro = 0;//00000000 00000000 00000000 00000000
        //Cria um inteiro com 1 no bit mais a esquerda e 0s em outros locais
        int displayMask = 1 << 31;//10000000 00000000 00000000 00000000
        //Percorrendo os 32 bits do numero
        for (int i=1; i<=numeroDeBits; i++) {
          int bit = (numero & displayMask) == 0 ? 0 : 1;//Pegando bit do numero

          //System.out.print(bit + " ");
          /***************************************************************/
          Painel.GRAFICO.entradaDeBit(bit);//ENVIANDO O BIT PARA O GRAFICO
          /***************************************************************/

          novoInteiro <<= 1;//Desloca 1 Bit para a esquerda
          novoInteiro = novoInteiro | bit;//Adicionando novo Bit ao Inteiro
          numero <<= 1;//Desloca 1 Bit para a esquerda

          //Terminou de adicionar os bits no novo Inteiro
          if (i == numeroDeBits) {
            fluxoBrutoDeBitsPontoB[indicePosicao] = novoInteiro;//Adicionando no vetor
          }
        }
      }
      //TERMINOU DE ENVIAR

      Painel.CAMADAS_TRANSMISSORAS.camadaFisica("\nEnviando os Bits\n");
      //Libera o Grafico para mostrar os Bits passando
      Painel.GRAFICO.semaphoroInicio.release();
    

      System.out.println("\n\nMEIO DE COMUNICACAO\n");
      CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);

    } catch (Exception e) {
      System.out.println("[ERRO] - Meio de Comunicacao");
    }
  }

}//Fim class