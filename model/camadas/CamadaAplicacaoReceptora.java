/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: CamadaAplicacaoReceptora
* Funcao: Converter um vetor de inteiros para uma String (mensagem) com
          base nos valores da tabela ASCII de cada caractere
***********************************************************************/

package model.camadas;

import view.Painel;


public class CamadaAplicacaoReceptora {


  /*********************************************
  * Metodo: camadaAplicacaoReceptora
  * Funcao: Converter uma String para um vetor de Inteiros
  * Parametros: mensagem : String
  * Retorno: void
  *********************************************/
  public static void camadaAplicacaoReceptora(int[] quadro) {
    int velocidade = 300;
    System.out.println("CAMADA APLICACAO RECEPTORA");
    try {
      Painel.CAMADAS_RECEPTORAS.expandirCamadaAplicacao();

      //Mensagem que foi recebida
      String mensagem = "";
      //Vetor de Caracteres da Mensagem
      char[] arrayCaracteres = new char[quadro.length];

      //Adicionando o caractere referente ao valor inteiro da Tabela [ASCII]
      for (int i=0; i<quadro.length; i++) {
        arrayCaracteres[i] = (char) quadro[i];//Adicionando o caractere com base no codigo ASCII
        System.out.println("\tCaractere ["+arrayCaracteres[i]+"] = " + quadro[i]);
        Painel.CAMADAS_RECEPTORAS.camadaAplicacao("Caractere ["+arrayCaracteres[i]+"] = [ASCII] " + quadro[i] + "\n");
        mensagem += arrayCaracteres[i];//Concatenando caractere na String
        Thread.sleep(velocidade);
      }

      Painel.CAMADAS_RECEPTORAS.camadaAplicacao("\nMensagem: [" + mensagem + "]");
      Thread.sleep(velocidade);

      Painel.CAMADAS_RECEPTORAS.terminouCamada();
      AplicacaoReceptora.aplicacaoReceptora(mensagem);

    } catch (InterruptedException e) {
      System.out.println("[ERRO] - Camada Aplicacao Receptora");
    }
  }

}//Fim class