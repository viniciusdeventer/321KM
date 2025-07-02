package view.usuario;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import dao.VendedorDAO;
import model.Cliente;
import model.Usuario;
import model.Vendedor;

import javax.swing.*;
import java.awt.*;

public class UsuarioForm extends JDialog {
    public UsuarioForm(Component parent, Usuario usuario) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(usuario == null ? "Novo Usuário" : "Editar Usuário");
        setSize(350, 350);
        setLocationRelativeTo(parent);

        JTextField txtLogin = new JTextField(20);
        txtLogin.setMaximumSize(new Dimension(350, 25));

        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setMaximumSize(new Dimension(350, 25));

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Cliente", "Vendedor", "Administrador"});
        JComboBox<Object> cbReferencia = new JComboBox<>();
        cbReferencia.addItem("Selecione");
        
        cbTipo.addActionListener(e -> {
            cbReferencia.removeAllItems();
            int tipo = cbTipo.getSelectedIndex();

            if (tipo == 0) {
                for (Cliente c : new ClienteDAO().listar()) {
                    cbReferencia.addItem(c);
                }
            } else  {
                for (Vendedor v : new VendedorDAO().listar()) {
                    cbReferencia.addItem(v);
                }
            }
        });
        
        cbReferencia.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Cliente) {
                    setText(((Cliente) value).getNome());
                } else if (value instanceof Vendedor) {
                    setText(((Vendedor) value).getNome());
                }
                return this;
            }
        });
        
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});

        if (usuario != null) {
            txtLogin.setText(usuario.getLogin());
            txtSenha.setText(usuario.getSenha());
            txtSenha.setEnabled(false);
            cbTipo.setSelectedIndex(usuario.getTipo());
            cbStatus.setSelectedIndex(usuario.getStatus());
        } else {
			cbStatus.setSelectedIndex(1);
		}

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
            String login = txtLogin.getText().trim();
            String senha = new String(txtSenha.getPassword()).trim();

            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Login e Senha são obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario u = usuario == null ? new Usuario() : usuario;
            u.setLogin(login);
            u.setSenha(senha);
            u.setTipo(cbTipo.getSelectedIndex());
            u.setStatus(cbStatus.getSelectedIndex());
            
            Object ref = cbReferencia.getSelectedItem();
            if (ref instanceof Cliente cliente) {
                u.setIdReferencia(cliente.getId());
            } else if (ref instanceof Vendedor vendedor) {
                u.setIdReferencia(vendedor.getId());
            }

            new UsuarioDAO().salvar(u);
            dispose();
        });

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(new BoxLayout(painelLogin, BoxLayout.Y_AXIS));
        JLabel lblLogin = new JLabel("Login");
        lblLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtLogin.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelLogin.add(lblLogin);
        painelLogin.add(txtLogin);
        painelLogin.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelSenha = new JPanel();
        painelSenha.setLayout(new BoxLayout(painelSenha, BoxLayout.Y_AXIS));
        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtSenha.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelSenha.add(lblSenha);
        painelSenha.add(txtSenha);
        painelSenha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelTipo = new JPanel();
        painelTipo.setLayout(new BoxLayout(painelTipo, BoxLayout.Y_AXIS));
        JLabel lblTipo = new JLabel("Tipo");
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelTipo.add(lblTipo);
        painelTipo.add(cbTipo);

        JPanel painelReferencia = new JPanel();
        painelReferencia.setLayout(new BoxLayout(painelReferencia, BoxLayout.Y_AXIS));
        JLabel lblReferencia = new JLabel("Referência");
        lblReferencia.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbReferencia.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelReferencia.add(lblReferencia);
        painelReferencia.add(cbReferencia);
        painelReferencia.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        cbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(lblStatus);
        painelStatus.add(cbStatus);

        JPanel linhaTipoStatus = new JPanel();
        linhaTipoStatus.setLayout(new BoxLayout(linhaTipoStatus, BoxLayout.X_AXIS));
        linhaTipoStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaTipoStatus.add(painelTipo);
        linhaTipoStatus.add(Box.createRigidArea(new Dimension(20, 0)));
        linhaTipoStatus.add(painelStatus);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBtn.add(btnSalvar);

        form.add(painelLogin);
        form.add(Box.createVerticalStrut(15));
        form.add(painelSenha);
        form.add(Box.createVerticalStrut(15));
        form.add(linhaTipoStatus);
        form.add(Box.createVerticalStrut(15));
        form.add(painelReferencia);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
    }
}
