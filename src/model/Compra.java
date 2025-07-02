package model;

import java.util.Date;

public class Compra {
	private int id;
	private int idCliente;
	private int idVendedor;
	private Date dataCompra;
	private int status;
	
	public Compra() {}
	public Compra(int id, int idCliente, int idVendedor, Date dataCompra, int status) {
		this.id = id;
		this.idCliente = idCliente;
		this.idVendedor = idVendedor;
		this.dataCompra = dataCompra;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	public int getIdVendedor() {
		return idVendedor;
	}
	
	public void setIdVendedor(int idVendedor) {
		this.idVendedor = idVendedor;
	}	
	
	public Date getDataCompra() {
		return dataCompra;
	}
	
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}
