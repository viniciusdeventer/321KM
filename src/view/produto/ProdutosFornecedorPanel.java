package view.produto;

import dao.ProdutoDAO;
import dao.ProdutosFornecedorDAO;
import model.ProdutosFornecedor;

import javax.swing.*;
import component.DefaultTableModelNaoEditavel;
import component.TabelaUtils;

import java.awt.*;
import java.util.List;

public class ProdutosFornecedorPanel extends JPanel {
    private JTable tabela;
    private DefaultTableModelNaoEditavel modelo;
    private int idFornecedor;
    private ProdutosFornecedorDAO produtosFornecedorDAO;

    public ProdutosFornecedorPanel(int idFornecedor) {
        this.idFornecedor = idFornecedor;
        this.produtosFornecedorDAO = new ProdutosFornecedorDAO();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        modelo = new DefaultTableModelNaoEditavel(new String[]{"ID", "Produto", "Valor Venda", "Valor Custo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);    
        TabelaUtils.adicionarDuploClique(
        	    tabela,
        	    modelo,
        	    id -> new ProdutosFornecedorDAO().listar().stream()
        	    		.filter(p -> p.getIdProduto() == id && p.getIdFornecedor() == idFornecedor)
        	            .findFirst()
        	            .orElse(null),
	            produto -> {
	                ProdutosFornecedorForm form = new ProdutosFornecedorForm(this, produto, idFornecedor);
	                form.addWindowListener(new java.awt.event.WindowAdapter() {
	                    @Override
	                    public void windowClosed(java.awt.event.WindowEvent e) {
	                        atualizarTabela();
	                    }
	                });
	                form.setVisible(true);
	            }         
        );
        
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(20);
        tabela.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollTabela = new JScrollPane(tabela);

        JButton btnNovo = new JButton("Novo");
        JButton btnExcluir = new JButton("Excluir");

        btnNovo.addActionListener(e -> {
            ProdutosFornecedor novoProduto = new ProdutosFornecedor();
            novoProduto.setIdFornecedor(idFornecedor);
            ProdutosFornecedorForm form = new ProdutosFornecedorForm(this, novoProduto, idFornecedor);
            form.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    atualizarTabela();
                }
            });
            form.setVisible(true);
        });

        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                int idProduto = (int) modelo.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja excluir esse produto?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                	produtosFornecedorDAO.excluir(idProduto, idFornecedor);
                    atualizarTabela();
                }
            } 
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnExcluir);

        scrollTabela.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBotoes.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(scrollTabela);
        add(Box.createVerticalStrut(10));
        add(painelBotoes);

        atualizarTabela();
    }

    public void atualizarTabela() {
        modelo.setRowCount(0);
        List<ProdutosFornecedor> produtos = produtosFornecedorDAO.buscar(idFornecedor);
        for (ProdutosFornecedor p : produtos) {
            modelo.addRow(new Object[]{
                    p.getIdProduto(),
                    p.getNomeProduto(),
                    String.format("R$%.2f", p.getValorVenda()),
                    String.format("R$%.2f", p.getValorCusto())
            });
        }
    }
}
