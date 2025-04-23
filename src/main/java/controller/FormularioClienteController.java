package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Cliente;
import model.dao.ClienteDaoJDBC;
import model.dao.DaoFactory;
import start.App;

public class FormularioClienteController implements Initializable {

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

    private static Cliente clienteSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (clienteSelecionado != null) {
            txtNome.setText(clienteSelecionado.getNome());
            txtEmail.setText(clienteSelecionado.getEmail());
            txtTelefone.setText(clienteSelecionado.getTelefone());
            txtIdade.setText(String.valueOf(clienteSelecionado.getIdade()));
            txtCredito.setText(String.valueOf(clienteSelecionado.getCredito()));
        }
    }

    @FXML
    private void btnCancelarOnAction(ActionEvent event) throws IOException {
        App.setRoot("GerenciarClientes");
        clienteSelecionado = null;
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) throws IOException, Exception {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarAlerta("Nome do cliente é obrigatório!");
            return;
        }
        Cliente cliente = new Cliente();
        cliente.setNome(txtNome.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setTelefone(txtTelefone.getText());

        
        try {
            cliente.setIdade(Integer.parseInt(txtIdade.getText().trim()));
        } catch (NumberFormatException e) {
            mostrarAlerta("Idade inválida. Use um formato numérico válido.");
            return;
        }

        
        try {
            cliente.setCredito(Float.parseFloat(txtCredito.getText().trim().replace(",", ".")));
        } catch (NumberFormatException e) {
            mostrarAlerta("Crédito inválido. Use um formato numérico válido.");
            return;
        }

        try{
            ClienteDaoJDBC dao = DaoFactory.novoClienteDao();
            if (clienteSelecionado == null) {
                dao.incluir(cliente);
            } else {
                cliente.setId(clienteSelecionado.getId());
                dao.editar(cliente);
                clienteSelecionado = null;
            }
            App.setRoot("GerenciarClientes");
        }catch(Exception e) {
            mostrarAlerta("Erro ao salvar cliente: " + e.getMessage());
        }

    }

    private void mostrarAlerta(String mensagem){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public static void setClienteSelecionado(Cliente clienteSelecionado) {
        FormularioClienteController.clienteSelecionado = clienteSelecionado;
    }
}
