package model;

import java.util.Date;

public class Contrato {
    private int id;
    private Fornecedor fornecedor; 
    private Date dataInicio;
    private Date dataVencimento;
    private String contato;
    private String caminho;
    private int status;

    public Contrato() {}

    public Contrato(int id, Date dataInicio, Date dataVencimento, String contato, String caminho, int status) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataVencimento = dataVencimento;
        this.contato = contato;
        this.caminho = caminho;
        this.status = status;
    }

    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }
    
    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getContato() {
		return contato;
	}
	public void setContato(String contato) {
		this.contato = contato;
	}
	
    public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
