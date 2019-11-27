/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: AplicacaoTransmissora
* Funcao: Iniciar o envio de uma Mensagem para a Camda de Aplicacao Transmissora
***********************************************************************/

package model.camadas;


public class AplicacaoTransmissora {


  /*********************************************
  * Metodo: aplicacaoTransmissora
  * Funcao: Recebe uma String da Interface Grafica e envia para a Camada de Aplicacao Transmissora
  * Parametros: mensagem : String
  * Retorno: void
  *********************************************/
  public static void aplicacaoTransmissora(String mensagem) {
    System.out.println("\n\nAPLICACAO TRANSMISSORA");
    try {
    
      System.out.println("\tMensagem: ["+mensagem+"]");

      CamadaAplicacaoTransmissora.camadaAplicacaoTransmissora(mensagem);

    } catch (Exception e) {
      System.out.println("[ERRO] - Aplicacao Transmissora");
    }
  }

}//Fim class