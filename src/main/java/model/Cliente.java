package model;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private int idade;
    private float credito;

    public Cliente() {
    }

    public Cliente(String nome, String email, String telefone, int idade, float credito) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.idade = idade;
        this.credito = credito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float getCredito() {
        return credito;
    }

    public void setCredito(float credito) {
        this.credito = credito;
    }

    @Override
    public String toString() {
        return id + " / " + nome + " / " + email + " / " + telefone + " / " + idade + " / " + credito;
    }
}