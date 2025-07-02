package view.produto;

import dao.ProdutosFornecedorDAO;
import model.ProdutosFornecedor;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProdutosFornecedorForm extends JDialog {
    public ProdutosFornecedorForm(Component parent, ProdutosFornecedor produto, int idFornecedor) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(produto == null ? "Novo Produto" : "Editar Produto");
        setSize(320, 220);
        setLocationRelativeTo(parent);

        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> listaProdutos = dao.listar();

        JComboBox<Produto> comboNome = new JComboBox<>();
        comboNome.setPreferredSize(new Dimension(550, 25));
        comboNome.setMaximumSize(new Dimension(550, 25));
        comboNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (Produto p : listaProdutos) {
            comboNome.addItem(p);
        }

        comboNome.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produto) {
                    setText(((Produto) value).getNome());
                }
                return this;
            }
        });

        JTextField txtVenda = new JTextField(10);
        txtVenda.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtVenda.setText(String.valueOf(produto.getValorVenda()));

        JTextField txtCusto = new JTextField(10);
        txtCusto.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtCusto.setText(String.valueOf(produto.getValorCusto()));

        if (produto != null) {
            for (int i = 0; i < comboNome.getItemCount(); i++) {
                if (comboNome.getItemAt(i).getId() == produto.getIdProduto()) {
                    comboNome.setSelectedIndex(i);
                    break;
                }
            }
        } else if (comboNome.getItemCount() > 0) {
            comboNome.setSelectedIndex(0);
        }

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
            Produto selecionado = (Produto) comboNome.getSelectedItem();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String vendaTxt = txtVenda.getText().trim();
            String custoTxt = txtCusto.getText().trim();

            if (vendaTxt.isEmpty() || custoTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valorVenda, valorCusto;
            try {
                valorVenda = Double.parseDouble(vendaTxt.replace(",", "."));
                valorCusto = Double.parseDouble(custoTxt.replace(",", "."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProdutosFornecedor p = produto == null ? new ProdutosFornecedor() : produto;
            p.setIdProduto(selecionado.getId());
            p.setNomeProduto(selecionado.getNome());
            p.setValorVenda(valorVenda);
            p.setValorCusto(valorCusto);
            p.setIdFornecedor(idFornecedor);

            new ProdutosFornecedorDAO().salvar(p);
            dispose();
        });

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Nome
        JPanel painelNome = new JPanel();
        painelNome.setLayout(new BoxLayout(painelNome, BoxLayout.Y_AXIS));
        JLabel lblNome = new JLabel("Nome");
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelNome.add(lblNome);
        painelNome.add(comboNome);
        painelNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Venda
        JPanel painelVenda = new JPanel();
        painelVenda.setLayout(new BoxLayout(painelVenda, BoxLayout.Y_AXIS));
        JLabel lblVenda = new JLabel("Valor Venda");
        lblVenda.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelVenda.add(lblVenda);
        painelVenda.add(txtVenda);

        // Custo
        JPanel painelCusto = new JPanel();
        painelCusto.setLayout(new BoxLayout(painelCusto, BoxLayout.Y_AXIS));
        JLabel lblCusto = new JLabel("Valor Custo");
        lblCusto.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCusto.add(lblCusto);
        painelCusto.add(txtCusto);

        JPanel linha1 = new JPanel(new GridLayout(1, 1));
        linha1.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha1.add(painelNome);

        JPanel linha2 = new JPanel(new GridLayout(1, 2, 10, 0));
        linha2.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha2.add(painelVenda);
        linha2.add(painelCusto);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBtn.add(btnSalvar);

        form.add(linha1);
        form.add(Box.createVerticalStrut(15));
        form.add(linha2);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
    }
}
