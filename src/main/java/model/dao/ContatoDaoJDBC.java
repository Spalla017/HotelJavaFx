package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Contato;

public class ContatoDaoJDBC implements InterfaceDao<Contato> {

    private Connection conn;

    public ContatoDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
    public void incluir(Contato entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Cliente (nome, email, telefone, idade, credito) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEmail());
        ps.setString(3, entidade.getTelefone());
        ps.setInt(4, entidade.getIdade());
        ps.setFloat(5, entidade.getCredito());
        ps.execute();
    }

    @Override
    public void editar(Contato entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("UPDATE Cliente SET nome = ?, email = ?, telefone = ?, idade = ?, credito = ? WHERE id = ?");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEmail());
        ps.setString(3, entidade.getTelefone());
        ps.setInt(4, entidade.getIdade());
        ps.setFloat(5, entidade.getCredito());
        ps.setInt(6, entidade.getId());
        ps.execute();
    }

    @Override
    public void excluir(Contato entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Cliente WHERE id = ?");
        ps.setInt(1, entidade.getId());
        ps.execute();
    }

    @Override
    public Contato pesquisarPorId(int id) throws Exception {
        PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Cliente WHERE id = ?");
        ps1.setInt(1, id);
        ResultSet rs = ps1.executeQuery();
        Contato c = null;
        if (rs.next()) {
            c = new Contato();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setEmail(rs.getString("email"));
            c.setTelefone(rs.getString("telefone"));
            c.setIdade(rs.getInt("idade"));
            c.setCredito(rs.getFloat("credito"));
        }
        return c;
    }

    @Override
    public List<Contato> listar(String param) throws Exception {
        PreparedStatement ps = null;
        if(param == ""){
            ps = conn.prepareStatement("SELECT * FROM Cliente");
        }else{
            ps = conn.prepareStatement("SELECT * FROM Cliente WHERE nome LIKE ?");
            ps.setString(1, "%" + param + "%");
        }
        
        ResultSet rs = ps.executeQuery();
        List<Contato> lista = new ArrayList<>();
        while (rs.next()) {
            Contato c = new Contato();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setEmail(rs.getString("email"));
            c.setTelefone(rs.getString("telefone"));
            c.setIdade(rs.getInt("idade"));
            c.setCredito(rs.getFloat("credito"));
            lista.add(c);
        }
        return lista;
    }
}