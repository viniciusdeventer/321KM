package model;

public class ProdutosFornecedor {
	private int IdProduto;
	private String NomeProduto;
	private int IdFornecedor;
	private double ValorVenda;	
	private double ValorCusto;
	
    public ProdutosFornecedor() {}
    public ProdutosFornecedor(int idProduto, String nomeProduto, int idFornecedor, double valorVenda, double valorCusto) {
		this.IdProduto = idProduto;
		this.NomeProduto = nomeProduto;
		this.IdFornecedor = idFornecedor;
		this.ValorVenda = valorVenda;
		this.ValorCusto = valorCusto;
    }
    
    public int getIdProduto() {
		return IdProduto;
	}
    
	public void setIdProduto(int idProduto) {
		this.IdProduto = idProduto;
	}
	
	public String getNomeProduto() {
		return NomeProduto;
	}
	
	public void setNomeProduto(String nomeProduto) {
		this.NomeProduto = nomeProduto;
	}
	
	public int getIdFornecedor() {
		return IdFornecedor;
	}
	
	public void setIdFornecedor(int idFornecedor) {
		this.IdFornecedor = idFornecedor;
	}
	
	public double getValorVenda() {
		return ValorVenda;
	}
	
	public void setValorVenda(double valorVenda) {
		this.ValorVenda = valorVenda;
	}
	
	public double getValorCusto() {
		return ValorCusto;
	}
	
	public void setValorCusto(double valorCusto) {
		this.ValorCusto = valorCusto;
	}
}


