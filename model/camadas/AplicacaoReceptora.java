/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/18
* Ultima alteracao: 17/02/18
* Nome: AplicacaoReceptora
* Funcao: Receber o envio de uma Mensagem da Camda de Aplicacao Receptora
***********************************************************************/

package model.camadas;


import view.Painel;


public class AplicacaoReceptora {


  /*********************************************
  * Metodo: aplicacaoReceptora
  * Funcao: Envia uma String (Mensagem) para a Interface Grafica
  * Parametros: mensagem : String
  * Retorno: void
  *********************************************/
  public static void aplicacaoReceptora(String mensagem) {
    System.out.println("APLICACAO RECEPTORA");
    try {

      System.out.println("\tMensagem: ["+mensagem+"]");
      
      Painel.COMPUTADOR_RECEPTOR.adicionarMensagem(mensagem);//Adicionando mensagem na interface grafica

    } catch (Exception e) {
      System.out.println("[ERRO] - Aplicacao Receptora");
    }
  }

}//Fim class