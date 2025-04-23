package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;

public class ClienteDaoJDBC implements InterfaceDao<Cliente> {

    private Connection conn;

    public ClienteDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
    public void incluir(Cliente entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Cliente (nome, email, telefone, idade, credito) VALUES (?, ?, ?, ?, ?)");
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getEmail());
        ps.setString(3, entidade.getTelefone());
        ps.setInt(4, entidade.getIdade());
        ps.setFloat(5, entidade.getCredito());
        ps.execute();
    }

    @Override
    public void editar(Cliente entidade) throws Exception {
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
    public void excluir(Cliente entidade) throws Exception {
        PreparedStatement ps = conn.prepareStatement("DELETE FROM Cliente WHERE id = ?");
        ps.setInt(1, entidade.getId());
        ps.execute();
    }

    @Override
    public Cliente pesquisarPorId(int id) throws Exception {
        PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM Cliente WHERE id = ?");
        ps1.setInt(1, id);
        ResultSet rs = ps1.executeQuery();
        Cliente c = null;
        if (rs.next()) {
            c = new Cliente();
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
    public List<Cliente> listar(String param) throws Exception {
        PreparedStatement ps = null;
        if(param == ""){
            ps = conn.prepareStatement("SELECT * FROM Cliente");
        }else{
            ps = conn.prepareStatement("SELECT * FROM Cliente WHERE nome LIKE ?");
            ps.setString(1, "%" + param + "%");
        }
        
        ResultSet rs = ps.executeQuery();
        List<Cliente> lista = new ArrayList<>();
        while (rs.next()) {
            Cliente c = new Cliente();
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