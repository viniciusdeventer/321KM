package view.produto;

import dao.ProdutosCompraDAO;
import model.ProdutosCompra;
import component.DefaultTableModelNaoEditavel;
import component.TabelaUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProdutosCompraPanel extends JPanel {
    private JTable tabela;
    private DefaultTableModelNaoEditavel modelo;
    private int idCompra;
    private ProdutosCompraDAO produtosCompraDAO;

    public ProdutosCompraPanel(int idCompra) {
        this.idCompra = idCompra;
        this.produtosCompraDAO = new ProdutosCompraDAO();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        modelo = new DefaultTableModelNaoEditavel(
                new String[]{"ID", "Produto", "Quantidade", "Valor Produto", "Desconto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);

        TabelaUtils.adicionarDuploClique(
                tabela,
                modelo,
                id -> new ProdutosCompraDAO().buscar(idCompra).stream()
                        .filter(p -> p.getIdProduto() == id && p.getIdCompra() == idCompra)
                        .findFirst()
                        .orElse(null),
                prod -> {
                    ProdutosCompraForm form = new ProdutosCompraForm(this, prod, idCompra);
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
            ProdutosCompra novo = new ProdutosCompra();
            novo.setIdCompra(idCompra);
            ProdutosCompraForm form = new ProdutosCompraForm(this, novo, idCompra);
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
                int opc = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja excluir esse produto?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION);
                if (opc == JOptionPane.YES_OPTION) {
                    produtosCompraDAO.excluir(idProduto, idCompra);
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
        List<ProdutosCompra> lista = produtosCompraDAO.buscar(idCompra);
        for (ProdutosCompra p : lista) {
            modelo.addRow(new Object[]{
                    p.getIdProduto(),
                    p.getNomeProduto(),
                    p.getQuantidade(),
                    String.format("R$%.2f", p.getValorProduto()),
                    String.format("R$%.2f", p.getDesconto())
            });
        }
    }
}
