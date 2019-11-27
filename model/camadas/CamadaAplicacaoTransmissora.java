/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: CamadaAplicacaoTransmissora
* Funcao: Converter uma String (Mensagem) para um vetor de inteiros com 
          base nos valores da tabela ASCII de cada caractere 
***********************************************************************/

package model.camadas;

import view.Painel;


public class CamadaAplicacaoTransmissora {


  /*********************************************
  * Metodo: camadaAplicacaoTransmissora
  * Funcao: Converter uma String para um vetor de Inteiros
  * Parametros: mensagem : String
  * Retorno: void
  *********************************************/
  public static void camadaAplicacaoTransmissora(String mensagem) {
    int velocidade = 300;
    System.out.println("CAMADA APLIACAO TRANSMISSORA");
    try {
      
      Painel.CAMADAS_TRANSMISSORAS.expandirCamadaAplicacao();
      Painel.CAMADAS_TRANSMISSORAS.limparTextoCamadas();
      Painel.CAMADAS_TRANSMISSORAS.camadaAplicacao("Mensagem: [" + mensagem + "]\n\n");
      Thread.sleep(velocidade);
      
      //Vetor de Caracteres da Mensagem
      char[] arrayCaracteres = mensagem.toCharArray();
      //Vetor pra armazenar os valores da Tabela [ASCII] de cada Caractere
      int[] quadro = new int[mensagem.length()];

      //Convertendo os caracteres em valores inteiros referente ao codigo ASCII
      for (int i=0; i<arrayCaracteres.length; i++) {
        quadro[i] = (int) arrayCaracteres[i];//Adicionando o codigo [ASCII] de cada Caractere
        System.out.println("\tCaractere ["+arrayCaracteres[i]+"] = " + quadro[i]);
        Painel.CAMADAS_TRANSMISSORAS.camadaAplicacao("Caractere ["+arrayCaracteres[i]+"] = ASCII [" + quadro[i] + "]\n");
        Thread.sleep(velocidade);
      }

      CamadaFisicaTransmissora.camadaFisicaTransmissora(quadro);

    } catch (InterruptedException e) {
      System.out.println("[ERRO] - Camada Aplicacao Tranmissora");
    }
  }

}//Fim class
