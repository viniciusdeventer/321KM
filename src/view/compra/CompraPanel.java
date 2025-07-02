package view.compra;

import dao.CompraDAO;
import model.Compra;
import component.DefaultTableModelNaoEditavel;
import component.TabelaUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CompraPanel extends JPanel {
    private JTable tabela;
    private DefaultTableModelNaoEditavel modelo;
    private int idCliente;
    private CompraDAO compraDAO;

    public CompraPanel(int idCliente) {
        this.idCliente = idCliente;
        this.compraDAO = new CompraDAO();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        modelo = new DefaultTableModelNaoEditavel(new String[]{"ID", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(20);
        tabela.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollTabela = new JScrollPane(tabela);

        TabelaUtils.adicionarDuploClique(
                tabela,
                modelo,
                id -> compraDAO.listar().stream()
                        .filter(c -> c.getId() == id && c.getIdCliente() == idCliente)
                        .findFirst()
                        .orElse(null),
                compra -> {
                    CompraForm form = new CompraForm(this, compra);
                    form.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            atualizarTabela();
                        }
                    });
                    form.setVisible(true);
                }
        );

        JButton btnNovo = new JButton("Novo");
        JButton btnExcluir = new JButton("Excluir");

        btnNovo.addActionListener(e -> {
            Compra nova = new Compra();
            nova.setIdCliente(idCliente);
            CompraForm form = new CompraForm(this, nova);
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
                int idCompra = (int) modelo.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Deseja excluir essa compra?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    compraDAO.excluir(idCompra);
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
        List<Compra> compras = compraDAO.buscar(idCliente);
        for (Compra c : compras) {
            modelo.addRow(new Object[]{
                    c.getId(),
                    c.getDataCompra(),
            });
        }
    }
}
