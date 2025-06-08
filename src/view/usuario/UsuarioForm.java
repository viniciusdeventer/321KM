package view.usuario;

import dao.UsuarioDAO;
import model.Usuario;

import javax.swing.*;
import java.awt.*;

public class UsuarioForm extends JDialog {
    public UsuarioForm(Component parent, Usuario usuario) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(usuario == null ? "Novo Usuário" : "Editar Usuário");
        setSize(300, 275);
        setLocationRelativeTo(parent);

        JTextField txtLogin = new JTextField(20);
        txtLogin.setMaximumSize(new Dimension(300, 25));

        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setMaximumSize(new Dimension(300, 25));

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Cliente", "Vendedor", "Administrador"});
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});

        if (usuario != null) {
            txtLogin.setText(usuario.getLogin());
            txtSenha.setText(usuario.getSenha());
            txtSenha.setEnabled(false);
            cbTipo.setSelectedIndex(usuario.getTipo());
            cbStatus.setSelectedIndex(usuario.getStatus());
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
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
    }
}
