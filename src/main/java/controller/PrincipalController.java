/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import model.Contato;
import model.dao.ContatoDaoJDBC;
import model.dao.DaoFactory;
import start.App;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnIncluir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField txtFiltro;
    @FXML
    private Button btnFiltrar;
    @FXML
    private Button btnLimpar;

    @FXML
    private TableView<Contato> tblCliente;
    @FXML
    private TableColumn<Contato, String> tblColNome;
    @FXML
    private TableColumn<Contato, String> tblColEmail;
    @FXML
    private TableColumn<Contato, String> tblColTelefone;

    private List<Contato> listaCliente;
    private ObservableList<Contato> observableListCliente;
    @FXML
    private Button btnVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarClientes("");
    }

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        App.setRoot("Formulario");
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) throws IOException {
        Contato clienteSelecionado = tblCliente.getSelectionModel().getSelectedItem();
        FormularioController.setClienteSelecionado(clienteSelecionado);
        App.setRoot("Formulario");
    }

    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Contato clienteSelecionado = tblCliente.getSelectionModel().getSelectedItem();
        ContatoDaoJDBC dao = DaoFactory.novoContatoDao();

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

        try {
            ContatoDaoJDBC dao = DaoFactory.novoContatoDao();
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
