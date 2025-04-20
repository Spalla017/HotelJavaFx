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

public class FormularioController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtIdade;
    @FXML
    private TextField txtCredito;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;
    
    private static Contato clienteSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (clienteSelecionado != null){
            txtNome.setText(clienteSelecionado.getNome());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtTelefone.setText(clienteSelecionado.getTelefone());
            txtIdade.setText(String.valueOf(clienteSelecionado.getIdade()));
            txtCredito.setText(String.valueOf(clienteSelecionado.getCredito()));
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
        
        // Parse idade para int - verificando se há valor
        try {
            cliente.setIdade(Integer.parseInt(txtIdade.getText().trim()));
        } catch (NumberFormatException e) {
            cliente.setIdade(0); // Valor padrão se estiver vazio ou não for um número
        }
        
        // Parse credito para float - verificando se há valor
        try {
            cliente.setCredito(Float.parseFloat(txtCredito.getText().trim().replace(",", ".")));
        } catch (NumberFormatException e) {
            cliente.setCredito(0.0f); // Valor padrão se estiver vazio ou não for um número
        }
        
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