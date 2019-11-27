/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 16/02/18
* Ultima alteracao: 17/02/18
* Nome: Camadas
* Funcao: Mostrar Graficamente as Mensagens de cada Camada
***********************************************************************/

package view.componentes;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.application.Platform;

import img.Imagem;


public class Camadas extends AnchorPane {

  public static final String TRANSMISSOR = "Transmissora";//O Computador eh o Transmissor
  public static final String RECEPTOR = "Receptora";//O Comoputador eh o Receptor

  private String tipo;//O tipo das Camadas
  private int width = 400;//Largura da Imagem
  private int height = 400;//Altura da Imagem
  private int posX, posY;//Posicao X e Y da Imagem

  //Objeto que armazenas todas as imagens do programa
  private Imagem allImage = new Imagem();

  private Accordion camadas;//Componentes pra organizar as Camadas
  private ImageView setaImagem;//ImageView da Seta

  private TitledPane camadaAplicacao;//Camada de Aplicacao
  private TextArea aplicacaoTextArea;//Area de Texto da Camada Aplicacao

  private TitledPane camadaFisica;//Camada Fisica
  private TextArea fisicaTextArea;//Area de Texto da Camada Fisica


  /*********************************************
  * Metodo: Camadas - Construtor
  * Funcao: Constroi objetos da classe Camadas
  * Parametros: tipo : String
  * Retorno: void
  *********************************************/
  public Camadas(String tipo) {
    this.tipo = tipo;
    this.setPrefSize(width, height);

    /*******************************************
    * [ACORDEON]
    ********************************************/
    this.camadas = new Accordion();
    this.camadas.setPrefSize(370,350);
    this.camadas.setLayoutX(30);
    this.camadas.setLayoutY(10);
    this.getChildren().add(camadas);

    /*******************************************
    * [IMAGEVIEW SETA]
    ********************************************/
    this.setaImagem = allImage.getImageView("seta");
    this.setaImagem.setFitWidth(26);
    this.setaImagem.setFitHeight(26);
    this.setaImagem.setLayoutY(10);
    this.setaImagem.setVisible(false);
    this.getChildren().add(setaImagem);

    /*******************************************
    * [CAMADA DE APLICACAO]
    ********************************************/
    this.camadaAplicacao = new TitledPane();
    this.camadaAplicacao.setText("Camada de Aplicacao " + tipo);
    this.aplicacaoTextArea = new TextArea();
    this.aplicacaoTextArea.setEditable(false);//Desativa a edicao
    this.aplicacaoTextArea.setFocusTraversable(false);//Desabilita o foco inicial
    this.camadaAplicacao.setContent(aplicacaoTextArea);
    this.camadas.getPanes().add(camadaAplicacao);//Adicionando no Acordeon

    /*******************************************
    * [CAMADA FISICA]
    ********************************************/
    this.camadaFisica = new TitledPane();
    this.camadaFisica.setText("Camada Fisica " + tipo);
    this.fisicaTextArea = new TextArea();
    this.fisicaTextArea.setEditable(false);//Desativa a edicao
    this.fisicaTextArea.setFocusTraversable(false);//Desabilita o foco inicial
    this.camadaFisica.setContent(fisicaTextArea);
    this.camadas.getPanes().add(camadaFisica);//Adicionando no Acordeon

  }

  /*********************************************
  * Metodo: expandirCamadaAplicacao
  * Funcao: Expande a tela onde aparece as informacoes da Camada Aplicacao
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void expandirCamadaAplicacao() {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          camadas.setExpandedPane(camadaAplicacao);
          int posicaoYSeta = 10;
          setaImagem.setLayoutY(posicaoYSeta);
          setaImagem.setVisible(true);
        } catch (Exception e) {
          System.out.println("[ERRO] - Expandir Camada Aplicacao");
        }
      }
    });
  }

  /*********************************************
  * Metodo: camadaAplicacao
  * Funcao: Exibe um texto na Area da Camada de Aplicacao
  * Parametros: texto : String
  * Retorno: void
  *********************************************/
  public void camadaAplicacao(String texto) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          String textoAntigo = aplicacaoTextArea.getText();
          aplicacaoTextArea.setText(textoAntigo + texto);
          aplicacaoTextArea.appendText("");
        } catch (Exception e) {
          System.out.println("[ERRO] - Adicionar texto na Camada Aplicacao");
        }
      }
    });
  }

  /*********************************************
  * Metodo: expandirCamadaFisica
  * Funcao: Expande a tela onde aparece as informacoes da Camada Fisica
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void expandirCamadaFisica() {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          camadas.setExpandedPane(camadaFisica);
          int posicaoYSeta = 37;
          setaImagem.setLayoutY(posicaoYSeta);
          setaImagem.setVisible(true);
        } catch (Exception e) {
          System.out.println("[ERRO] - Adicionar texto na Camada Aplicacao");
        }
      }
    });
  }

  /*********************************************
  * Metodo: camadaFisica
  * Funcao: Exibe um texto na Area da Camada Fisica
  * Parametros: texto : String
  * Retorno: void
  *********************************************/
  public void camadaFisica(String texto) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          String textoAntigo = fisicaTextArea.getText();
          fisicaTextArea.setText(textoAntigo + texto);
          fisicaTextArea.appendText("");
        } catch (Exception e) {
          System.out.println("[ERRO] - Adicionar texto na Camada Aplicacao");
        }
      }
    });
  }

  /*********************************************
  * Metodo: terminouCamada
  * Funcao: Oculta a Seta desta Camada
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void terminouCamada() {
    this.camadas.setExpandedPane(null);
    this.setaImagem.setVisible(false);
  }

  /*********************************************
  * Metodo: setPosicao
  * Funcao: Definir a posicao desse componente na Interface
  * Parametros: posX : int, posY : int
  * Retorno: void
  *********************************************/
  public void setPosicao(int posX, int posY) {
    this.setLayoutX(posX);
    this.setLayoutY(posY);
  }

  /*********************************************
  * Metodo: limparTextoCamadas
  * Funcao: Limpa os Textos que estao dentro das Camadas
  * Parametros: void
  * Retorno: void
  *********************************************/
  public void limparTextoCamadas() {
    this.aplicacaoTextArea.setText("");
    this.fisicaTextArea.setText("");
  }


}//Fim class