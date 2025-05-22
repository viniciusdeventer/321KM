package model;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double valorProduto;
    private int tipoProduto;
    private int status;

    public Produto() {}
    public Produto(int id, String nome, String descricao, double valorProduto, int tipoProduto, int status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valorProduto = valorProduto;
        this.tipoProduto = tipoProduto;
        this.status = status;
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
	
    public String getDesc() {
		return descricao;
	}
	public void setDesc(String descricao) {
		this.descricao = descricao;
	}
	
    public double getValorProduto() {
		return valorProduto;
	}
	public void setValorProduto(double valorProduto) {
		this.valorProduto = valorProduto;
	}
	
    public int getTipoProduto() {
		return tipoProduto;
	}
	public void setTipoProduto(int tipoProduto) {
		this.tipoProduto = tipoProduto;
	}
	
    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
