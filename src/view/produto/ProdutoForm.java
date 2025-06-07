package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;

public class ProdutoForm extends JDialog {
    public ProdutoForm(Component parent, Produto produto) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(produto == null ? "Novo Produto" : "Editar Produto");
        setSize(600, 300);
        setLocationRelativeTo(parent);

        JTextField txtId = new JTextField(5);
        txtId.setPreferredSize(new Dimension(50, 25));
        txtId.setMaximumSize(new Dimension(50, 25));

        JTextField txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(550, 25));
        txtNome.setMaximumSize(new Dimension(550, 25));
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea txtDesc = new JTextArea(3, 30); 
        txtDesc.setLineWrap(false);
        txtDesc.setWrapStyleWord(false);
        txtDesc.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtValor = new JTextField(15);
        txtValor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Serviço", "Passagem", "Pacote"});
        cbTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});
        cbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (produto != null) {
            txtId.setText(String.valueOf(produto.getId()));
            txtNome.setText(produto.getNome());
            txtDesc.setText(produto.getDesc());
            txtValor.setText(String.valueOf(produto.getValorProduto()));
            cbTipo.setSelectedIndex(produto.getTipoProduto());
            cbStatus.setSelectedIndex(produto.getStatus());
        }

        txtId.setEnabled(false);

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String desc = txtDesc.getText().trim();
            String valorTexto = txtValor.getText().trim();

            if (nome.isEmpty() || desc.isEmpty() || valorTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, Descrição e Valor são obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(valorTexto.replace(",", "."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Produto p = produto == null ? new Produto() : produto;
            p.setNome(nome);
            p.setDesc(desc);
            p.setValorProduto(valor);
            p.setTipoProduto(cbTipo.getSelectedIndex());
            p.setStatus(cbStatus.getSelectedIndex());

            new ProdutoDAO().salvar(p);
            dispose();
        });

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ID
        JPanel painelId = new JPanel();
        painelId.setLayout(new BoxLayout(painelId, BoxLayout.Y_AXIS));
        JLabel lblId = new JLabel("ID");
        lblId.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtId.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelId.add(lblId);
        painelId.add(txtId);
        painelId.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Nome
        JPanel painelNome = new JPanel();
        painelNome.setLayout(new BoxLayout(painelNome, BoxLayout.Y_AXIS));
        JLabel lblNome = new JLabel("Nome");
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelNome.add(lblNome);
        painelNome.add(txtNome);
        painelNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Linha 1 - ID e Nome lado a lado
        JPanel linha1 = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        linha1.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha1.add(painelId);
        linha1.add(Box.createRigidArea(new Dimension(10, 0))); 
        linha1.add(painelNome);

        // Descrição
        JPanel painelDesc = new JPanel();
        painelDesc.setLayout(new BoxLayout(painelDesc, BoxLayout.Y_AXIS));
        painelDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblDesc = new JLabel("Descrição");
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelDesc.add(lblDesc);
        painelDesc.add(txtDesc);

        // Valor, Tipo e Status
        JPanel linha3 = new JPanel(new GridLayout(1, 3, 10, 0));
        linha3.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelValor = new JPanel();
        painelValor.setLayout(new BoxLayout(painelValor, BoxLayout.Y_AXIS));
        JLabel lblValor = new JLabel("Valor");
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelValor.add(lblValor);
        painelValor.add(txtValor);

        JPanel painelTipo = new JPanel();
        painelTipo.setLayout(new BoxLayout(painelTipo, BoxLayout.Y_AXIS));
        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelTipo.add(lblTipo);
        painelTipo.add(cbTipo);

        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(lblStatus);
        painelStatus.add(cbStatus);

        linha3.add(painelValor);
        linha3.add(painelTipo);
        linha3.add(painelStatus);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBtn.add(btnSalvar);

        form.add(linha1);
        form.add(Box.createVerticalStrut(15));
        form.add(painelDesc);
        form.add(Box.createVerticalStrut(15));
        form.add(linha3);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
    }
}
