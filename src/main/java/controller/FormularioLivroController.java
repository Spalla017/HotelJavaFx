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
import model.Livro;
import model.dao.LivroDaoJDBC;
import model.dao.DaoFactory;
import start.App;

public class FormularioLivroController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtEditora;
    @FXML
    private TextField txtEdicao;
    @FXML
    private TextField txtPreco;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGravar;
    
    private static Livro livroSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (livroSelecionado != null) {
            txtNome.setText(livroSelecionado.getNome());
            txtAutor.setText(livroSelecionado.getAutor());
            txtEditora.setText(livroSelecionado.getEditora());
            txtEdicao.setText(livroSelecionado.getEdicao());
            txtPreco.setText(String.valueOf(livroSelecionado.getPreco()));
        }
    }    

    @FXML
    private void btnCancelarOnAction(ActionEvent event) throws IOException {
        App.setRoot("GerenciarLivros");
        livroSelecionado = null;
    }

    @FXML
    private void btnGravarOnAction(ActionEvent event) throws IOException {
        if (txtNome.getText().trim().isEmpty()) {
            mostrarAlerta("Nome do livro é obrigatório!");
            return;
        }
        
        Livro livro = new Livro();
        livro.setNome(txtNome.getText());
        livro.setAutor(txtAutor.getText());
        livro.setEditora(txtEditora.getText());
        livro.setEdicao(txtEdicao.getText());
        
        // Parse preço para float - verificando se há valor
        try {
            livro.setPreco(Float.parseFloat(txtPreco.getText().trim().replace(",", ".")));
        } catch (NumberFormatException e) {
            mostrarAlerta("Preço inválido. Use um formato numérico válido.");
            return;
        }
        
        try {
            LivroDaoJDBC dao = DaoFactory.novoLivroDao();
            if (livroSelecionado == null) {
                dao.incluir(livro);
            } else {
                livro.setId(livroSelecionado.getId());
                dao.editar(livro);
                livroSelecionado = null;
            }
            App.setRoot("GerenciarLivros");
        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar livro: " + e.getMessage());
        }
    }
    
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static Livro getLivroSelecionado() {
        return livroSelecionado;
    }

    public static void setLivroSelecionado(Livro livro) {
        livroSelecionado = livro;
    }
}