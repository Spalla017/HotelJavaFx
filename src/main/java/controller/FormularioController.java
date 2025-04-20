/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Contato;
import model.dao.ContatoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class FormularioController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;
    
    private static Contato clienteSelecionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (clienteSelecionado != null){
            txtNome.setText(clienteSelecionado.getNome());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtTelefone.setText(clienteSelecionado.getTelefone());
        }
    }    

    @FXML
    private void btnCancelarOnAction(ActionEvent event) throws IOException {
        App.setRoot("Principal");
        clienteSelecionado = null;
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) throws IOException, Exception {
        Contato cliente = new Contato();
        cliente.setNome(txtNome.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setTelefone(txtTelefone.getText());
        ContatoDaoJDBC dao = DaoFactory.novoContatoDao();
        if(clienteSelecionado == null){
            dao.incluir(cliente);
        }else{
            cliente.setId(clienteSelecionado.getId());
            dao.editar(cliente);
            clienteSelecionado = null;
        }
        App.setRoot("Principal");
    }

    public static Contato getClienteSelecionado() {
        return clienteSelecionado;
    }

    public static void setClienteSelecionado(Contato clienteSelecionado) {
        FormularioController.clienteSelecionado = clienteSelecionado;
    }
    
    
    
}
