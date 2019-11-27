/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 12/10/17
* Ultima alteracao: 16/02/2018
* Nome: Classe Imagem
* Funcao: Armazenar todas as Imagens usadas no programa
***********************************************************************/

package img;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
import java.util.List;
import java.util.ArrayList;


public class Imagem {

  //Lista para armazenar as Image usadas no programa
  private List<Image> listaImagens = new ArrayList<>();
  //private Lista<Image> listaImagens = new ListaSEncadeada<>();
  //Vetor com os nomes dos arquivos de imagens
  private String vetor[] = {"transmissor","receptor","msg_transmissor",
                            "msg_receptor","pulso","enviar","seta"};

  /*********************************************
  * Metodo: Imagem - Construtor
  * Funcao: Criar objetos da Classe Imagem
  * Parametros: void
  *********************************************/
  public Imagem() {
    for (int i=0; i<vetor.length; i++) {
      //Inicializando objetos Image e colocando na lista
      try {
        listaImagens.add(new Image(Imagem.class.getResourceAsStream(vetor[i]+".png")));
        // listaImagens.add(new Image(Imagem.class.getResourceAsStream("/img/"+vetor[i]+".png")));
      } catch (Exception e) {
        System.out.println("[Erro] - (Imagem.java): a Imagem ["+vetor[i]+"] nao foi encontrada");
      }
    }
  }

  /*********************************************
  * Metodo: getIcone
  * Funcao: Retornar uma Image do Icone do Programa
  * Parametros: void
  * Retorno: Image
  *********************************************/
  public static Image getIcone() throws Exception {
    return new Image(Imagem.class.getResourceAsStream("icone.png"));
  }

  /*********************************************
  * Metodo: Get
  * Funcao: Retornar uma Image para colocar numa ImageView
  * Parametros: String
  * Retorno: Image
  *********************************************/
  private Image get(String nome) throws Exception {
    int posicao = 0;
    //Buca a posicao da Imagem procurada
    for (;posicao<vetor.length; posicao++) {
      if (vetor[posicao].equals(nome)) {
        break;
      }
    }
    return this.listaImagens.get(posicao);
  }

  /*********************************************
  * Metodo: getImageView
  * Funcao: Retorna uma ImageView
  * Parametros: nomeImagem : String
  * Retorno: ImageView
  *********************************************/
  public ImageView getImageView(String nomeImagem) {
    try {
      return new ImageView(get(nomeImagem));
    } catch (Exception e) {
      System.out.println("[Erro] - (Imagem.java): nao foi possivel encontrar a imagem");
      return null;
    }
  }

  /*********************************************
  * Metodo: Trocar Imagem
  * Funcao: Trocar as Imagens das ImageView do programa
  * Parametros: ImageView String
  * Retorno: void
  *********************************************/
  public synchronized void trocarImagem(ImageView imageView, String nomeImagem) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          imageView.setImage(get(nomeImagem));//Trocando a Imagem da ImageView
        } catch (Exception e) {
          System.out.println("[Erro] - (Imagem.java): nao foi possivel trocar a Imagem ["+nomeImagem+"]");
        }
      }
    });
  }

  /*********************************************
  * Metodo: Mover Imagem
  * Funcao: Move a ImageView para uma nova posicao (X,Y)
  * Parametros: ImageView, posX, posY
  * Retorno: void
  *********************************************/
  public synchronized static void moverImagem(ImageView imageView, int posX, int posY, int ang) {
    Platform.runLater(new Runnable(){
      @Override
      public void run() {
        try {
          imageView.setLayoutX(posX);//Alterando a posicao X
          imageView.setLayoutY(posY);//Alterando a posicao Y
          imageView.setRotate(ang);//Rotacionando a imagem
        } catch (Exception e) {
          System.out.println("[Erro] - (Imagem.java): erro ao mover a imagem " + imageView.toString());
        }
      }
    });
  }

}//Fim class