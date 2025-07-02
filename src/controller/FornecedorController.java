package controller;

import dao.FornecedorDAO;
import dao.ProdutosFornecedorDAO;
import dao.ContratoDAO;
import model.Fornecedor;
import model.Contrato;
import model.ProdutosFornecedor;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class FornecedorController {

    private final FornecedorDAO fornecedorDAO;
    private final ContratoDAO contratoDAO;
    private final ProdutosFornecedorDAO produtosFornecedorDAO;

    public FornecedorController() {
        this.fornecedorDAO = new FornecedorDAO();
        this.contratoDAO = new ContratoDAO();
        this.produtosFornecedorDAO = new ProdutosFornecedorDAO();
    }

    public void salvarFornecedor(
            Fornecedor fornecedor,
            String nome,
            String inscricao,
            int tipoPessoa,
            String fantasia,
            String telefone,
            String email,
            String endereco,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            String estado,
            String cep,
            int status
    ) {
        fornecedor.setNome(nome);
        fornecedor.setInscricao(inscricao);
        fornecedor.setTipoPessoa(tipoPessoa);
        fornecedor.setFantasia(fantasia);
        fornecedor.setTelefone(telefone);
        fornecedor.setEmail(email);
        fornecedor.setEndereco(endereco);
        fornecedor.setNumero(numero);
        fornecedor.setComplemento(complemento);
        fornecedor.setBairro(bairro);
        fornecedor.setCidade(cidade);
        fornecedor.setEstado(estado);
        fornecedor.setCEP(cep);
        fornecedor.setStatus(status);

        fornecedorDAO.salvar(fornecedor);
    }

    public void salvarContrato(
            Fornecedor fornecedor,
            Contrato contrato,
            Date dataInicio,
            Date dataVencimento,
            String contato,
            String caminhoPDF
    ) {
        contrato.setFornecedor(fornecedor);
        contrato.setDataInicio(dataInicio);
        contrato.setDataVencimento(dataVencimento);
        contrato.setContato(contato);
        contrato.setCaminho(caminhoPDF);
        if (contrato.getId() == 0) {
            contrato.setStatus(1);
        }
        contratoDAO.salvar(contrato);
    }

    public void excluirContrato(int contratoId) {
        contratoDAO.excluir(contratoId);
    }

    public Contrato buscarContratoPorFornecedor(int fornecedorId) {
        return contratoDAO.buscarPorFornecedor(fornecedorId);
    }
    
    public void salvarProduto(
    		int idProduto,
			int idFornecedor,
			double valorVenda,
			double valorCusto
	) {
    	ProdutosFornecedor produto = new ProdutosFornecedor();
		produto.setIdProduto(idProduto);
		produto.setIdFornecedor(idFornecedor);
		produto.setValorVenda(valorVenda);
		produto.setValorCusto(valorCusto);

		produtosFornecedorDAO.salvar(produto);
    }
}
