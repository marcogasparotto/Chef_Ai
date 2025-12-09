public class Ingrediente {
    private String nome; // Atributo privado
    private String quantidade; // Atributo privado

    // Construtor
    public Ingrediente(String nome, String quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    // Métodos de acesso (Encapsulamento)
    public String getNome() {
        return nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return nome + " (" + quantidade + ")";
    }
}