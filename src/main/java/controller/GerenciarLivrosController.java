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
import model.Livro;
import model.dao.LivroDaoJDBC;
import model.dao.DaoFactory;
import start.App;

public class GerenciarLivrosController implements Initializable {

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
    private Button btnVoltar;

    @FXML
    private TableView<Livro> tblLivros;
    @FXML
    private TableColumn<Livro, String> tblColNome;
    @FXML
    private TableColumn<Livro, String> tblColAutor;
    @FXML
    private TableColumn<Livro, String> tblColEditora;
    @FXML
    private TableColumn<Livro, String> tblColEdicao;
    @FXML
    private TableColumn<Livro, Float> tblColPreco;

    private List<Livro> listaLivros;
    private ObservableList<Livro> observableListLivros;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarLivros("");
    }

    @FXML
    private void btnIncluirOnAction(ActionEvent event) throws IOException {
        App.setRoot("FormularioLivro");
    }

    @FXML
    private void btnEditarOnAction(ActionEvent event) throws IOException {
        Livro livroSelecionado = tblLivros.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            FormularioLivroController.setLivroSelecionado(livroSelecionado);
            App.setRoot("FormularioLivro");
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um livro para editar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void btnExcluirOnAction(ActionEvent event) throws Exception {
        Livro livroSelecionado = tblLivros.getSelectionModel().getSelectedItem();
        if (livroSelecionado == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um livro para excluir.");
            alert.showAndWait();
            return;
        }
        
        LivroDaoJDBC dao = DaoFactory.novoLivroDao();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        alert.setContentText("Confirma exclusão do livro '" + livroSelecionado.getNome() + "'?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                dao.excluir(livroSelecionado);
                carregarLivros(""); 
            } catch (Exception e) {
                String mensagem = "Ocorreu um erro: " + e.getMessage();
                Alert alertErro = new Alert(Alert.AlertType.ERROR);
                alertErro.setTitle("Erro");
                alertErro.setHeaderText(null);
                alertErro.setContentText(mensagem);
                alertErro.showAndWait();
            }
        }
    }

    @FXML
    private void btnFiltrarOnAction(ActionEvent event) {
        carregarLivros(txtFiltro.getText());
    }

    @FXML
    private void btnLimparOnAction(ActionEvent event) {
        carregarLivros("");
        txtFiltro.clear();
    }

    @FXML
    private void btnVoltarOnAction(ActionEvent event) throws IOException {
        App.setRoot("TelaInicial");
    }

    public void carregarLivros(String param) {
        tblColNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tblColAutor.setCellValueFactory(new PropertyValueFactory<>("Autor"));
        tblColEditora.setCellValueFactory(new PropertyValueFactory<>("Editora"));
        tblColEdicao.setCellValueFactory(new PropertyValueFactory<>("Edicao"));
        tblColPreco.setCellValueFactory(new PropertyValueFactory<>("Preco"));

        try {
            LivroDaoJDBC dao = DaoFactory.novoLivroDao();
            listaLivros = dao.listar(param);
        } catch (Exception e) {
            System.out.println("Erro ao carregar livros: " + e.getMessage());
            e.printStackTrace();
        }

        observableListLivros = FXCollections.observableArrayList(listaLivros);
        tblLivros.setItems(observableListLivros);
    }
}