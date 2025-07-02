package view.vendedor;

import dao.VendedorDAO;
import model.Vendedor;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;

public class VendedorForm extends JDialog {
    public VendedorForm(Component parent, Vendedor vendedor) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(vendedor == null ? "Novo Vendedor" : "Editar Vendedor");
        setSize(600, 300);
        setLocationRelativeTo(parent);

        JTextField txtId = new JTextField(5);
        txtId.setPreferredSize(new Dimension(50, 25));
        txtId.setMaximumSize(new Dimension(50, 25));

        JTextField txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(550, 25));
        txtNome.setMaximumSize(new Dimension(550, 25));
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        final MaskFormatter telMask;
        try {
			telMask = new MaskFormatter("(##) #####-####");
			telMask.setPlaceholderCharacter('_');
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        
        JFormattedTextField txtTelefone = new JFormattedTextField(telMask);
        txtTelefone.setPreferredSize(new Dimension(240, 25));
        txtTelefone.setMaximumSize(new Dimension(240, 25));
        txtTelefone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(320, 25));
        txtEmail.setMaximumSize(new Dimension(320, 25));
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField txtTxComissao = new JTextField(15);
        txtTxComissao.setPreferredSize(new Dimension(320, 25));
        txtTxComissao.setMaximumSize(new Dimension(320, 25));
        txtTxComissao.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});
        cbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (vendedor != null) {
            txtId.setText(String.valueOf(vendedor.getId()));
            txtNome.setText(vendedor.getNome());
            txtTelefone.setText(vendedor.getTelefone());
            txtEmail.setText(vendedor.getEmail());
            txtTxComissao.setText(String.valueOf(vendedor.getTxComissao()));
            cbStatus.setSelectedIndex(vendedor.getStatus());
        } else {
            cbStatus.setSelectedIndex(1);
        }

        txtId.setEnabled(false);

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
        	String id = txtId.getText().trim();
            String nome = txtNome.getText().trim();
            String tel = txtTelefone.getText().replaceAll("\\D", "");
            String email = txtEmail.getText().trim();
            String txComissao = txtTxComissao.getText().trim();

            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(txComissao.replace(",", "."));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Vendedor v = vendedor == null ? new Vendedor() : vendedor;
            v.setNome(nome);
            v.setTelefone(tel);
            v.setEmail(email);
            v.setTxComissao(valor);
            v.setStatus(cbStatus.getSelectedIndex());

            new VendedorDAO().salvar(v);
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

        JPanel painelTelefone = new JPanel();
        painelTelefone.setLayout(new BoxLayout(painelTelefone, BoxLayout.Y_AXIS));
        JLabel lblTel = new JLabel("Telefone");
        lblTel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelTelefone.add(lblTel);
        painelTelefone.add(txtTelefone);
        painelTelefone.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel painelEmail = new JPanel();
        painelEmail.setLayout(new BoxLayout(painelEmail, BoxLayout.Y_AXIS));
        JLabel lblEmail = new JLabel("E-mail");
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelEmail.add(lblEmail);
        painelEmail.add(txtEmail);
        painelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel painelTxComissao = new JPanel();
        painelTxComissao.setLayout(new BoxLayout(painelTxComissao, BoxLayout.Y_AXIS));
        JLabel lblTxComissao = new JLabel("Taxa Comissão (%)");
        lblTxComissao.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelTxComissao.add(lblTxComissao);
        painelTxComissao.add(txtTxComissao);
        painelTxComissao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Linha 2 - Telefone, E-mail e Taxa de Comissão lado a lado
        JPanel linha2 = new JPanel(new GridLayout(1, 3, 10, 0));
        linha2.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha2.add(painelTelefone);
        linha2.add(painelEmail);
        linha2.add(painelTxComissao);

        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(lblStatus);
        painelStatus.add(cbStatus);

        // Status
        JPanel linha3 = new JPanel(new GridLayout(1, 3, 10, 0));
        linha3.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha3.add(painelStatus);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBtn.add(btnSalvar);

        form.add(linha1);
        form.add(Box.createVerticalStrut(15));
        form.add(linha2);
        form.add(Box.createVerticalStrut(15));
        form.add(linha3);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
    }
}
