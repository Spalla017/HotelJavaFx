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
        // Navega para a tela de gerenciamento de livros (a ser implementada)
        // Por enquanto, apenas mostra mensagem de alerta
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Em desenvolvimento");
        alert.setHeaderText(null);
        alert.setContentText("O módulo de gerenciamento de livros está em desenvolvimento.");
        alert.showAndWait();
        
        // Quando a tela de livros for implementada, descomente a linha abaixo:
        // App.setRoot("GerenciarLivros");
    }
}