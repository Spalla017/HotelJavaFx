package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import start.App;

/**
 * FXML Controller class
 *
 * @author Spalla's Library
 */
public class TelaInicialController implements Initializable {

    @FXML
    private Button btnClientes;
    @FXML
    private Button btnLivros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialização, se necessário
    }    

    @FXML
    private void btnClientesOnAction(ActionEvent event) throws IOException {
        // Navega para a tela de gerenciamento de clientes
        App.setRoot("Principal");
    }

    @FXML
    private void btnLivrosOnAction(ActionEvent event) throws IOException {
        // Navega para a tela de gerenciamento de livros
        App.setRoot("GerenciarLivros");
    }
}