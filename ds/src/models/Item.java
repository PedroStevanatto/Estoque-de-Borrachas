package models; //indica que o arquivo esta na pasta config

public class Item {
    //define os atributos dos objetos que estao no banco de dados
    private int id;
    private String marca;
    private String borracha;
    private String tipo;
    private boolean esponja;
    private String preco;

    // Construtores
    public Item() {

    }

    public Item(String marca, String borracha, String tipo, boolean esponja, String preco) {
        this.marca = marca;
        this.borracha = borracha;
        this.tipo = tipo;
        this.esponja = esponja;
        this.preco = preco;
    }

    public Item(int id, String marca, String borracha, String tipo, boolean esponja, String preco) {
        this.id = id;
        this.marca = marca;
        this.borracha = borracha;
        this.tipo = tipo;
        this.esponja = esponja;
        this.preco = preco;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    // ID somente leitura, sem setter

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getBorracha() {
        return borracha;
    }

    public void setBorracha(String borracha) {
        this.borracha = borracha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getEsponja() {
        return esponja;
    }

    public void setEsponja(boolean esponja){
        this.esponja = esponja;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco){
        this.preco = preco;
    }

    // toString

    @Override
    public String toString() {
        return "Item [id=" + id + ", marca=" + marca + ", borracha=" + borracha + ", tipo=" + tipo + ", esponja=" + esponja + ", preco=" + preco + "]";
    }
}
