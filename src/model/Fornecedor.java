package model;

public class Fornecedor {
    private int id;
    private String nome;
    private String inscricao;
    private String fantasia;
    private String telefone;
    private String email;
    private String endereco;
    private String numero;
    private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
    private int status;

    public Fornecedor() {}
    public Fornecedor(int id, String nome, String inscricao, String fantasia, String telefone, String email, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String cep, int status) {
    	this.id = id;
    	this.nome = nome;
    	this.inscricao = inscricao;
    	this.fantasia = fantasia;
    	this.telefone = telefone;
    	this.email = email;
    	this.endereco = endereco;
    	this.numero = numero;
    	this.complemento = complemento;
    	this.bairro = bairro;
    	this.cidade = cidade;
    	this.estado = estado;
    	this.cep = cep;
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
	
    public String getInscricao() {
		return inscricao;
	}
	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}
	
	public String getFantasia() {
		return fantasia;
	}
	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
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
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCEP() {
		return cep;
	}
	public void setCEP(String cep) {
		this.cep = cep;
	}
	
    public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
