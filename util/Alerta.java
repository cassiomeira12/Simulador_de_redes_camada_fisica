/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 01/02/17
* Ultima alteracao: 16/02/18
* Nome: Alerta
* Funcao: Exibir alertas para o usuario
***********************************************************************/

package util;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Alerta {


  /*********************************************
  * Metodo: Alerta - Construtor
  * Funcao: Constroi objetos da classe Alerta
  * Parametros: void
  * Retorno: void
  *********************************************/
  public Alerta() {

  }

  /*********************************************
  * Metodo: Alerta - Construtor
  * Funcao: Constroi objetos da classe Alerta
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void alerta(String titulo, String cabecalho, String mensagem) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(titulo);
    alert.setHeaderText(cabecalho);
    alert.setContentText(mensagem);
    //alert.setGraphic(new ImageView(getClass().getResource("/img/erro.png").toString()));
    alert.show();
  }

  /*********************************************
  * Metodo: Alerta - Construtor
  * Funcao: Constroi objetos da classe Alerta
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void erro(String titulo, String cabecalho, String mensagem) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(titulo);
    alert.setHeaderText(cabecalho);
    alert.setContentText(mensagem);
    //alert.setGraphic(new ImageView(getClass().getResource("/img/erro.png").toString()));
    alert.show();
  }

  /*********************************************
  * Metodo: Alerta - Construtor
  * Funcao: Constroi objetos da classe Alerta
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void erro(String titulo, String mensagem) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(titulo);
    //alert.setHeaderText(cabecalho);
    alert.setContentText(mensagem);
    //alert.setGraphic(new ImageView(getClass().getResource("/img/erro.png").toString()));
    alert.show();
  }

  /*********************************************
  * Metodo: Alerta - Construtor
  * Funcao: Constroi objetos da classe Alerta
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void confirmacao(String titulo, String mensagem) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensagem);
    alert.show();


    // Optional<ButtonType> result = alert.showAndWait();
    // if (result.get() == ButtonType.OK){
    //     // ... user chose OK
    // } else {
    //     // ... user chose CANCEL or closed the dialog
    // }
  }

  /*********************************************
  * Metodo: Alerta - Construtor
  * Funcao: Constroi objetos da classe Alerta
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void informacao(String titulo, String mensagem) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensagem);
    alert.show();
  }

}//Fim class