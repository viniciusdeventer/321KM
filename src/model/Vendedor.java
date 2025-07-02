package model;

public class Vendedor {
	private int id;
	private String nome;
	private String telefone;
	private String email;
	private double txComissao;
	private int status;
	
    public Vendedor() {}
    public Vendedor(int id,  String nome, String telefone, String email, double txComissao, int status) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.txComissao = txComissao;
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
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public double getTxComissao() {
		return txComissao;
	}
	
	public void setTxComissao(double txComissao) {
		this.txComissao = txComissao;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}