package model;

import java.util.Date;

public class ProdutosCompra {
	private int idCompra;
	private int idProduto;
	private String nomeProduto;
	private int quantidade;
	private double valorProduto;
	private double desconto;
	private Date dataInicio;
	private Date dataFim;
	private String observacao;
	private int status;
	
	public ProdutosCompra() {}
	public ProdutosCompra(int idCompra, int idProduto, String nomeProduto, int quantidade, double valorProduto, double desconto, Date dataInicio, Date dataFim, String observacao, int status) {
		this.idCompra = idCompra;
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.valorProduto = valorProduto;
		this.desconto = desconto;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.observacao = observacao;
		this.status = status;
	}
	
	public int getIdCompra() {
		return idCompra;
	}
	
	public void setIdCompra(int idCompra) {
		this.idCompra = idCompra;
	}
	
	public int getIdProduto() {
		return idProduto;
	}
	
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}
	
	public String getNomeProduto() {
		return nomeProduto;
	}
	
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getValorProduto() {
		return valorProduto;
	}
	
	public void setValorProduto(double valorProduto) {
		this.valorProduto = valorProduto;
	}
	
	public double getDesconto() {
		return desconto;
	}
	
	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
