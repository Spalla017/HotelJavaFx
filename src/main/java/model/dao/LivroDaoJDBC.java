package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Livro;

public class LivroDaoJDBC implements InterfaceDao<Livro> {

    private Connection conn;

    public LivroDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
    public void incluir(Livro entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO livros (nome, autor, editora, edicao, preco) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getAutor());
        ps.setString(3, entidade.getEditora());
        ps.setString(4, entidade.getEdicao());
        ps.setFloat(5, entidade.getPreco());
        ps.execute();
    }

    @Override
    public void editar(Livro entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("UPDATE livros SET nome = ?, autor = ?, editora = ?, edicao = ?, preco = ? WHERE id = ?");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getAutor());
        ps.setString(3, entidade.getEditora());
        ps.setString(4, entidade.getEdicao());
        ps.setFloat(5, entidade.getPreco());
        ps.setInt(6, entidade.getId());
        ps.execute();
    }

    @Override
    public void excluir(Livro entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM livros WHERE id = ?");
        ps.setInt(1, entidade.getId());
        ps.execute();
    }

    @Override
    public Livro pesquisarPorId(int id) throws Exception {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM livros WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        Livro livro = null;
        if (rs.next()) {
            livro = new Livro();
            livro.setId(rs.getInt("id"));
            livro.setNome(rs.getString("nome"));
            livro.setAutor(rs.getString("autor"));
            livro.setEditora(rs.getString("editora"));
            livro.setEdicao(rs.getString("edicao"));
            livro.setPreco(rs.getFloat("preco"));
        }
        return livro;
    }

    @Override
    public List<Livro> listar(String param) throws Exception {
        PreparedStatement ps = null;
        if(param == ""){
            ps = conn.prepareStatement("SELECT * FROM livros");
        }else{
            ps = conn.prepareStatement("SELECT * FROM livros WHERE nome LIKE ?");
            ps.setString(1, "%" + param + "%");
        }
        
        ResultSet rs = ps.executeQuery();
        List<Livro> lista = new ArrayList<>();
        while (rs.next()) {
            Livro livro = new Livro();
            livro.setId(rs.getInt("id"));
            livro.setNome(rs.getString("nome"));
            livro.setAutor(rs.getString("autor"));
            livro.setEditora(rs.getString("editora"));
            livro.setEdicao(rs.getString("edicao"));
            livro.setPreco(rs.getFloat("preco"));
            lista.add(livro);
        }
        return lista;
    }
}