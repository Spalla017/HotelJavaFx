package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cliente;
import model.dao.ClienteDaoJDBC;
import model.dao.DaoFactory;
import start.App;

public class GerenciarClientesController implements Initializable {

    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnVoltar;
    @FXML
    private Button btnFiltrar;
    @FXML
    private TextField txtFiltro;
    
    

    @FXML
    private TableView<Cliente> tblCliente;
    @FXML
    private TableColumn<Cliente, String> tblColNome;
    @FXML
    private TableColumn<Cliente, String> tblColEmail;
    @FXML
    private TableColumn<Cliente, String> tblColTelefone;
    @FXML
    private TableColumn<Cliente, Integer> tblColIdade;
    @FXML
    private TableColumn<Cliente, Float> tblColCredito;

    private List<Cliente> listaCliente;
    private ObservableList<Cliente> observableListCliente;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarClientes("");
    }

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        App.setRoot("FormularioCliente");
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) throws IOException {
        Cliente clienteSelecionado = tblCliente.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            FormularioClienteController.setClienteSelecionado(clienteSelecionado);
            App.setRoot("FormularioCliente");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um cliente para editar.");
            alert.showAndWait();
        }

    }

    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Cliente clienteSelecionado = tblCliente.getSelectionModel().getSelectedItem();

        if (clienteSelecionado != null) {
            ClienteDaoJDBC dao = DaoFactory.novoClienteDao();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Aviso");
            alert.setContentText("Confirma exclusão de " + clienteSelecionado.getNome() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    dao.excluir(clienteSelecionado);
                } catch (Exception e) {
                    String mensagem = "Ocorreu um erro " + e.getMessage();
                    Alert alertErro = new Alert(Alert.AlertType.INFORMATION);
                    alertErro.setTitle("Aviso");
                    alertErro.setContentText(mensagem);
                    alertErro.showAndWait();
                }
            }
            carregarClientes("");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um cliente para excluir.");
            alert.showAndWait();
        }

    }

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
        carregarClientes(txtFiltro.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        carregarClientes("");
        txtFiltro.clear();
    }

    public void carregarClientes(String param) {
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        tblColTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        tblColIdade.setCellValueFactory(new PropertyValueFactory<>("Idade"));
        tblColCredito.setCellValueFactory(new PropertyValueFactory<>("Credito"));

        try {
            ClienteDaoJDBC dao = DaoFactory.novoClienteDao();
            listaCliente = dao.listar(param);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        observableListCliente = FXCollections.observableArrayList(listaCliente);
        tblCliente.setItems(observableListCliente);
    }

    @FXML
    private void btnVoltarOnAction(ActionEvent event) throws IOException {
        App.setRoot("TelaInicial");
    }
}
