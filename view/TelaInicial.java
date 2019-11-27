/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 08/11/17
* Ultima alteracao: 16/02/18
* Nome: Tela Inicial
* Funcao: Mostar a inicial do Programa
***********************************************************************/

package view;

import view.sobre.*;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TelaInicial {

  private static final String NOME_PROGRAMA = Informacoes.TRABALHO;//Titulo da Janela
  private static final int PAINEL_WIDTH  = 1200;//Largura da Tela
  private static final int PAINEL_HEIGHT = 680;//Altura da Tela
  private static final boolean MAXIMIZAR_JANELA = false;//Desativando opcao de Maximizar a tela

  /*********************************************
  * Metodo: show
  * Funcao: Mostrar Tela do Programa
  * Parametros: void
  * Retorno: void
  *********************************************/
  public static void show(Stage palco) {

    palco.setTitle(NOME_PROGRAMA);//Adicsionando titulo 
    palco.setResizable(MAXIMIZAR_JANELA);//Definir se a tela pode ser Maximizada

    /*******************************************
    * Menu [Arquivo]
    ********************************************/
    Menu arquivoMenu = new Menu("Arquivo");//Menu Arquivo
    MenuItem sairItem = new MenuItem("Sair");//Item Sair
    sairItem.setOnAction((ActionEvent e) -> { menuSairItem(palco); });//Atribuindo acao no botao
    arquivoMenu.getItems().addAll(sairItem);//Adicionando itens de Menu

    /*******************************************
    * Menu [Ajuda]
    ********************************************/
    Menu ajudaMenu = new Menu("Ajuda");//Menu Arquivo
    MenuItem sobreItem = new MenuItem("Sobre");//Item Sobre
    sobreItem.setOnAction((ActionEvent e) -> { menuSobreItem(); });//Atribuindo acao no botao
    ajudaMenu.getItems().addAll(sobreItem);//Adicionando itens de Menu

    /*******************************************
    * Barra de Menu do Programa
    ********************************************/
    MenuBar barraMenu = new MenuBar();//Barra de Menu
    barraMenu.setPrefHeight(30);//Adicionando altura

    barraMenu.getMenus().addAll(arquivoMenu, ajudaMenu);//Adicionando Menus na Barra de Menus

    /*******************************************
    * Bloco Principal da Interface
    ********************************************/
    VBox blocoTela = new VBox();//Bloco principal para a interface

    /*******************************************
    * Painel da Interface
    ********************************************/
    Painel painel = new Painel();//Painel que contem os item da Interface

    blocoTela.getChildren().addAll(barraMenu, painel);//Adicionando Menu ao Bloco principal

    blocoTela.setPrefSize(PAINEL_WIDTH, PAINEL_HEIGHT);//Adicionando tamanho ao Painel
    palco.setScene(new Scene(blocoTela, PAINEL_WIDTH, PAINEL_HEIGHT));//Adicionando Cena ao Palco
    palco.centerOnScreen();//Inicializando no centro da Tela
    palco.show();//Mostrando o Palco

  }//Fim show

  /*********************************************
  * Metodo: menuSairItem
  * Funcao: Executa os comandos do Botao [Sair] do Menu
  * Parametros: void
  * Retorno: void
  *********************************************/
  private static void menuSairItem(Stage palco) {
    palco.close();//Fechando palco da aplicacao
    System.exit(0);//Encerrando programa
  }

  /*********************************************
  * Metodo: menuSobreItem
  * Funcao: Executa os comandos do Botao [Sobre] do Menu
  * Parametros: void
  * Retorno: void
  *********************************************/
  private static void menuSobreItem() {
    Stage palco = new Stage();
    palco.setTitle("Sobre");//Adicionando titulo 
    palco.setResizable(false);//Definir se a tela pode ser Maximizada
    palco.initModality(Modality.APPLICATION_MODAL);//Impede de clicar na tela em plano de fundo
    palco.centerOnScreen();

    TelaSobre tela = new TelaSobre();//Painel da tela
    tela.setPrefSize(400,300);//Adicionando tamanho ao Painel

    palco.setScene(new Scene(tela,400,300));//Adicionando Cena ao Palco
    palco.showAndWait();//Mostrando o Palco
  }

}//Fim class