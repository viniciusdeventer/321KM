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
        setSize(300, 300);
        setLocationRelativeTo(parent);

        JTextField txtLogin = new JTextField(15);
        JPasswordField txtSenha = new JPasswordField(15);
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Cliente", "Vendedor", "Administrador"});
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});

        if (usuario != null) {
            txtLogin.setText(usuario.getLogin());
            txtSenha.setText(usuario.getSenha());
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

        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Login:")); form.add(txtLogin);
        form.add(new JLabel("Senha:")); form.add(txtSenha);
        form.add(new JLabel("Tipo:")); form.add(cbTipo);
        form.add(new JLabel("Status:")); form.add(cbStatus);
        form.add(btnSalvar);

        add(form);
    }
}
