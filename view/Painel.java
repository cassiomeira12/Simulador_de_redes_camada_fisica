/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 12/12/17
* Ultima alteracao: 17/02/18
* Nome: Painel
* Funcao: Classe usada para colocar os componentes de Interface
***********************************************************************/

package view;

import javafx.scene.layout.AnchorPane;
import img.Imagem;
import view.componentes.*;
import util.Alerta;


public class Painel extends AnchorPane {

  //Objeto que armazenas todas as imagens do programa
  private Imagem allImage = new Imagem();
  public static Computador COMPUTADOR_TRANSMISSOR;
  public static Computador COMPUTADOR_RECEPTOR;
  public static Camadas CAMADAS_TRANSMISSORAS;
  public static Camadas CAMADAS_RECEPTORAS;
  public static Grafico GRAFICO;


  /*********************************************
  * Metodo: Painel - Construtor
  * Funcao: Cria os componentes para a interface do programa
  * Parametros: void
  * Retorno: void
  *********************************************/
  public Painel() {
    this.construirInterface();//Inicializa os Componentes de Interface
  }//Fim do Construtor


  /*********************************************
  * Metodo: Construir Interface
  * Funcao: Iniciarliza os Componentes presente no Painel
  * Parametros: void
  * Retorno: void
  *********************************************/
  private void construirInterface() {
    
    COMPUTADOR_TRANSMISSOR = new Computador(Computador.TRANSMISSOR);
    COMPUTADOR_TRANSMISSOR.setPosicao(100,20);
    this.getChildren().add(COMPUTADOR_TRANSMISSOR);

    COMPUTADOR_RECEPTOR = new Computador(Computador.RECEPTOR);
    COMPUTADOR_RECEPTOR.setPosicao(900,20);
    this.getChildren().add(COMPUTADOR_RECEPTOR);

    CAMADAS_TRANSMISSORAS = new Camadas(Camadas.TRANSMISSOR);
    CAMADAS_TRANSMISSORAS.setPosicao(10,220);
    this.getChildren().add(CAMADAS_TRANSMISSORAS);

    CAMADAS_RECEPTORAS = new Camadas(Camadas.RECEPTOR);
    CAMADAS_RECEPTORAS.setPosicao(797,220);
    this.getChildren().add(CAMADAS_RECEPTORAS);

    GRAFICO = new Grafico();
    GRAFICO.setPosicao(413,445);
    this.getChildren().add(GRAFICO);

  }//Fim construirInterface

}//Fim class