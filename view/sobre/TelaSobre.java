/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 08/11/17
* Ultima alteracao: 18/01/18
* Nome: Tela Sobre
* Funcao: Mostra as informcoes deste trabalho
***********************************************************************/

package view.sobre;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class TelaSobre extends AnchorPane {

  private Label nomeLabel;//Label para Mostrar o Nome
  private Label matriculaLabel;//Label para Mostrar o numero de Matricula
  private Label disciplinaLabel;//Label para Mostrar a Disciplina

  private TextArea descricaoTextArea;//TextArea para colocar a Descricao do Trabalho


  /*********************************************
  * Metodo: TelaSobre - Construtor
  * Funcao: Constroi objetos da classe TelaSobre
  * Parametros: void
  * Retorno: void
  *********************************************/
  public TelaSobre() {

    /*******************************************
    * Adicionar VBox
    ********************************************/
    VBox vBox = new VBox();
    vBox.setSpacing(20);//Adicionando espacamento
    vBox.setPadding(new Insets(10,0,0,0));//Adicionando padding interno
    vBox.setPrefSize(350,110);//Adicionando tamanho
    vBox.setLayoutX(25);//Adicionando posicao X
    vBox.setLayoutY(14);//Adicionando posicao Y
    this.getChildren().add(vBox);

    /*******************************************
    * Adicionar HBox 1
    ********************************************/
    HBox hBox1 = new HBox();
    hBox1.setSpacing(30);//Adicioandno espacamento
    vBox.getChildren().add(hBox1);

    /*******************************************
    * Adicionar Label
    ********************************************/
    Label label1 = new Label("Aluno:");
    hBox1.getChildren().add(label1);

    this.nomeLabel = new Label();
    this.nomeLabel.setText(Informacoes.NOME);//Adicionando o Nome pela Clesse Informacoes
    hBox1.getChildren().add(this.nomeLabel);


    /*******************************************
    * Adicionar HBox 2
    ********************************************/
    HBox hBox2 = new HBox();
    hBox2.setSpacing(10);//Adicioandno espacamento
    vBox.getChildren().add(hBox2);

    /*******************************************
    * Adicionar Label
    ********************************************/
    Label label2 = new Label("Matricula:");
    hBox2.getChildren().add(label2);

    this.matriculaLabel = new Label();
    this.matriculaLabel.setText(Informacoes.MATRICULA);//Adicionando a Matricula pela Clesse Informacoes
    hBox2.getChildren().add(this.matriculaLabel);


    /*******************************************
    * Adicionar HBox 3
    ********************************************/
    HBox hBox3 = new HBox();
    hBox3.setSpacing(5);//Adicioandno espacamento
    vBox.getChildren().add(hBox3);

    /*******************************************
    * Adicionar Label
    ********************************************/
    Label label3 = new Label("Disciplina:");
    hBox3.getChildren().add(label3);

    this.disciplinaLabel = new Label();
    this.disciplinaLabel.setText(Informacoes.DISCIPLINA);//Adicionando a Disciplina pela Clesse Informacoes
    hBox3.getChildren().add(this.disciplinaLabel);


    /*******************************************
    * Adicionar TextArea
    ********************************************/
    this.descricaoTextArea = new TextArea();
    this.descricaoTextArea.setLayoutX(25);//Adicionando posicao X
    this.descricaoTextArea.setLayoutY(130);//Adicionando posicao Y
    this.descricaoTextArea.setPrefSize(350,150);//Adicionando tamanho
    this.descricaoTextArea.setEditable(false);//Desabilitando a edicao de texto
    this.descricaoTextArea.setFocusTraversable(false);//Desativando o foco inicial
    this.getChildren().add(this.descricaoTextArea);
    this.descricaoTextArea.setText(Informacoes.DESCRICAO);
  }

}//Fim class