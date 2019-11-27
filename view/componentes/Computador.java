/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 23/12/17
* Ultima alteracao: 17/02/18
* Nome: Computador
* Funcao: Enviar ou Receber uma mensagem 
***********************************************************************/

package view.componentes;


import img.Imagem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import util.Alerta;
import model.camadas.AplicacaoTransmissora;
import view.Painel;


public class Computador extends AnchorPane {

  public static final String TRANSMISSOR = "transmissor";//O Computador eh o Transmissor
  public static final String RECEPTOR = "receptor";//O Comoputador eh o Receptor

  private String tipo;//O tipo do computador
  private int width = 230;//Largura da Imagem
  private int height = 190;//Altura da Imagem
  private int posX, posY;//Posicao X e Y da Imagem

  private ImageView computadorImage;//ImageView do Computador
  private TextArea textArea;//Area onde o texto sera adicionado
  private TextField mensagemText;//Campo onde digita a mensagem
  private Button enviarButton;//Botao para enviar mensagem

  private Imagem allImage = new Imagem();


  /*********************************************
  * Metodo: Computador - Construtor
  * Funcao: Constroi objetos da classe Computador
  * Parametros: tipo : String [TRANSMISSOR/RECEPTOR]
  * Retorno: void
  *********************************************/
  public Computador(String tipo) {
    this.tipo = tipo;//Atribuindo o tipo do Computador
    this.setPrefSize(width, height);

    this.computadorImage = new ImageView();//Inicializando a ImageView do Computador
    this.allImage.trocarImagem(this.computadorImage, this.tipo);//Adicionando a Imagem do Computador
    this.computadorImage.setPreserveRatio(true);
    this.computadorImage.setFitHeight(160);//Adicionando tamanho da Imagem
    this.computadorImage.setLayoutX(18);//Adicionando posicao X
    this.computadorImage.setLayoutY(0);//Adicionando posicao Y
    this.getChildren().add(computadorImage);//Adicionando ao Painel

    this.textArea = new TextArea();//Inicializando o TextArea
    this.textArea.setPrefSize(170, 109);
    this.textArea.setLayoutX(31);//Adicionando posicao X
    this.textArea.setLayoutY(10);//Adicionando posicao Y
    this.textArea.setWrapText(true);//O texto quebra a linha automaticamente
    this.textArea.setEditable(false);//Desabilita a edicao do componente
    this.textArea.setFocusTraversable(false);//Desabilita o foco inicial
    this.getChildren().add(textArea);

    this.mensagemText = this.textFieldMensagem(0,160);//Inicializnado TextField de entrada de texto
    this.getChildren().add(mensagemText);

    this.enviarButton = this.buttonEnviar(204,160);
    this.getChildren().add(enviarButton);


    //Ocultar entrada de mensagem caso este computador seja um RECEPTOR
    if (tipo.equals(Computador.RECEPTOR)) {
      this.mensagemText.setVisible(false);//Ocultando compo de texto
      this.enviarButton.setVisible(false);//Ocultando botao
    }
  }

  /*********************************************
  * Metodo: enviarMensagem
  * Funcao: Envia uma mensagem para o outro computador
  * Parametros: mensagem : String
  * Retorno: void
  *********************************************/
  private void enviarMensagem(String mensagem) {
    //Verificando se nenhum texto foi digitado
    if (mensagem.isEmpty()) {
      Alerta.erro("Erro ao enviar mensagem","Erro: O campo de mensagem esta vazio");
    } else {
      Painel.GRAFICO.desativarBoxCodificacao();//Desativando a opcao de alterar Tipo de Codificacao
      adicionarMensagem(mensagem);//Adiciona a mensagem enviada no Computador Transmissor

      //Thread para rodar as funcoes das Camadas fora da Thread da Interface Grafica (Para nao travar)
      new Thread() {
        @Override
        public void run() {
          AplicacaoTransmissora.aplicacaoTransmissora(mensagem);
        }
      }.start();
    
    }
  }

  /*********************************************
  * Metodo: adicionarMensagem
  * Funcao: Adiciona uma mensagem ao TextArea
  * Parametros: mensagem : String
  * Retorno: void
  *********************************************/
  public void adicionarMensagem(String mensagem) {
    String textoAntigo = this.textArea.getText();
    this.textArea.setText(textoAntigo + mensagem + "\n\n");//Limpa a Tela do Computador
    this.textArea.appendText("");//Descendo Scroll da tela do Computador
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
  * Metodo: textFieldMensagem
  * Funcao: Cria um TextField para entrada de texto
  * Parametros: posX : int, posY : int
  * Retorno: TextField
  *********************************************/
  private TextField textFieldMensagem(int posX, int posY) {
    TextField entradaTexto = new TextField();//Criando uma nova entrada de Texto
    entradaTexto.setPrefWidth(230);//Adicionando Largura
    entradaTexto.setPrefHeight(26);//Adicionando Altura
    entradaTexto.setLayoutX(posX);//Adicioanndo posicao X
    entradaTexto.setLayoutY(posY);//Adicionando posicao Y
    entradaTexto.setFocusTraversable(false);//Desabilita o foco inicial
    entradaTexto.setPromptText("Digite e aperte ENTER");//Adicionando texto base
    entradaTexto.setPadding(new Insets(0,30,0,5));
    //entradaTexto.setAlignment(Pos.CENTER);//Deixa o texto centralizado

    //Adiciona um funcao ao apertar botao [ENTER]
    entradaTexto.setOnKeyReleased((KeyEvent key) -> {
      if (key.getCode() == KeyCode.ENTER) {
        this.enviarMensagem(entradaTexto.getText());
        entradaTexto.setText("");//Limpa a caixa de Texto
      }
    });

    return entradaTexto;
  }

  /*********************************************
  * Metodo: buttonEnviar
  * Funcao: Cria um Button para enviar a Mensagem
  * Parametros: posX : int, posY : int
  * Retorno: Button
  *********************************************/
  private Button buttonEnviar(int posX, int posY) {
    Button botao = new Button();//Criando um novo botao
    botao.setLayoutX(posX);//Adicionando posicao X
    botao.setLayoutY(posY);//Adicionando posicao Y
    botao.setMinWidth(26);//Adicionando Largura Minima
    botao.setMinHeight(26);//Adicionando Altura Minima
    botao.setMaxWidth(26);//Adicionando Largura Maxima
    botao.setMaxHeight(26);//Adicionando Altura Maxima

    botao.setOnAction((ActionEvent e) -> { 
      this.enviarMensagem(mensagemText.getText());
      this.mensagemText.setText("");//Limpa a caixa de Texto
    });//Atribuindo acao no botao

    botao.setGraphic(allImage.getImageView("enviar"));

    return botao;
  }

}//Fim class