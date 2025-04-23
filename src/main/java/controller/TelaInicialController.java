package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import start.App;


public class TelaInicialController implements Initializable {

    @FXML
    private Button btnClientes;
    @FXML
    private Button btnLivros;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    @FXML
    private void btnClientesOnAction(ActionEvent event) throws IOException {
        App.setRoot("GerenciarClientes");
    }

    @FXML
    private void btnLivrosOnAction(ActionEvent event) throws IOException {
        App.setRoot("GerenciarLivros");
    }
}