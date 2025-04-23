package model.dao;

public class DaoFactory {
    public static ClienteDaoJDBC novoClienteDao() throws Exception{
        return new ClienteDaoJDBC();
    }
    
    public static LivroDaoJDBC novoLivroDao() throws Exception{
        return new LivroDaoJDBC();
    }
}