package view.cliente;

import dao.ClienteDAO;
import model.Cliente;
import view.compra.CompraPanel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;

public class ClienteForm extends JDialog {
    public ClienteForm(Component parent, Cliente cliente) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(cliente == null ? "Novo Cliente" : "Editar Cliente");
        setSize(500, 250);
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
        txtTelefone.setPreferredSize(new Dimension(150, 25));
        txtTelefone.setMaximumSize(new Dimension(150, 25));
        txtTelefone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(500, 25));
        txtEmail.setMaximumSize(new Dimension(500, 25));
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});
        cbStatus.setPreferredSize(new Dimension(100, 25));
        cbStatus.setMaximumSize(new Dimension(100, 25));
        cbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (cliente != null) {
            txtId.setText(String.valueOf(cliente.getId()));
            txtNome.setText(cliente.getNome());
            txtTelefone.setText(cliente.getTelefone());
            txtEmail.setText(cliente.getEmail());
            cbStatus.setSelectedIndex(cliente.getStatus());
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
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente v = cliente == null ? new Cliente() : cliente;
            v.setNome(nome);
            v.setTelefone(tel);
            v.setEmail(email);
            v.setStatus(cbStatus.getSelectedIndex());

            new ClienteDAO().salvar(v);
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
        
        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(lblStatus);
        painelStatus.add(cbStatus);
        
        // Linha 2 - Telefone, E-mail  lado a lado
        JPanel linha2 = new JPanel();
        linha2.setLayout(new BoxLayout(linha2, BoxLayout.X_AXIS));
        linha2.add(painelTelefone);
        linha2.add(Box.createRigidArea(new Dimension(10, 0)));
        linha2.add(painelEmail);
        linha2.add(Box.createRigidArea(new Dimension(10, 0)));
        linha2.add(painelStatus);
        
        JPanel linha3 = new JPanel();
        linha3.setLayout(new BoxLayout(linha3, BoxLayout.X_AXIS));
        linha3.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (cliente != null && cliente.getId() > 0) {
        	setSize(500, 350);
            linha3.add(Box.createVerticalStrut(10));
            linha3.add(new CompraPanel(cliente.getId()));
        }

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
