package view.cliente;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import component.*;

public class ClientePanel extends JPanel {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField campoBusca;

    public ClientePanel() {
        setLayout(new BorderLayout());

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        campoBusca = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");

        btnBuscar.addActionListener(e -> atualizarTabela());

        painelBusca.add(campoBusca);
        painelBusca.add(btnBuscar);

        add(painelBusca, BorderLayout.NORTH);

        modelo = new DefaultTableModelNaoEditavel(new String[]{"ID", "Nome", "Telefone", "E-mail"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        TabelaUtils.adicionarDuploClique(
        	    tabela,
        	    modelo,
        	    id -> new ClienteDAO().listar().stream()
        	            .filter(p -> p.getId() == id)
        	            .findFirst()
        	            .orElse(null),
        	    cliente -> new ClienteForm(this, cliente).setVisible(true)
        );

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> {
            new ClienteForm(this, null).setVisible(true);
            atualizarTabela();
        });

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                int id = (int) modelo.getValueAt(row, 0);
                new ClienteDAO().excluir(id);
                atualizarTabela();
            }
        });

        JPanel botoes = new JPanel(new FlowLayout());
        botoes.add(btnNovo);;
        botoes.add(btnExcluir);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        String filtro = (campoBusca != null) ? campoBusca.getText().trim() : "";
        List<Cliente> lista = new ClienteDAO().buscar(filtro);

        for (Cliente v : lista) {
            modelo.addRow(new Object[]{
                v.getId(),
                //v.getIdUsuario(),
                v.getNome(),
                v.getTelefone(),
                v.getEmail(),
                //v.getStatus() == 1 ? "Ativo" : "Inativo"
            });
        }
    }
}
