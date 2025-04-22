package model.dao;

public class DaoFactory {
    public static ContatoDaoJDBC novoContatoDao() throws Exception{
        return new ContatoDaoJDBC();
    }
    
    public static LivroDaoJDBC novoLivroDao() throws Exception{
        return new LivroDaoJDBC();
    }
}