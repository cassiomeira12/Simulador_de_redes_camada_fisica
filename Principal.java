/***********************************************************************
* Autor: Cassio Meira Silva
* Matricula: 201610373
* Inicio: 24/10/17
* Ultima alteracao: 17/02/18
* Nome: Principal
* Funcao: Chamar Tela do Programa
***********************************************************************/

import view.TelaInicial;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;
import img.Imagem;

public class Principal extends Application {

  @Override
  public void start(Stage palco) {
    
    TelaInicial.show(palco);//Chamando Tela do Programa

    try {
      palco.getIcons().add(Imagem.getIcone());//Adicionando Icone ao Programa
    } catch (Exception e) {
      System.out.println("[Erro]: Imagem do Icone nao foi encontrada");
    }
    
    /*********************************************
    * Metodo: setOnCloseRequest
    * Funcao: Finaliza o programa por completo ao Fechar
    * Parametros: EventHandler
    * Retorno: void
    *********************************************/
    palco.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent t) {
        t.consume();
        palco.close();
        System.exit(0);
      }
    });
    
  }//Fim start

  public static void main(String[] args) {
    Application.launch(args);
  }

}//Fim class