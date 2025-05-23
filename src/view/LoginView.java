package view;

import dao.UsuarioDAO;
import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JFrame {
    public LoginView() {
        Font fonte = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", fonte);
        UIManager.put("Button.font", fonte);
        UIManager.put("TextField.font", fonte);
        UIManager.put("PasswordField.font", fonte);

        setTitle("Login");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(new BoxLayout(painelLogin, BoxLayout.Y_AXIS));
        painelLogin.setBackground(new Color(245, 245, 245));
        painelLogin.setPreferredSize(new Dimension(500, 500));

        ImageIcon imagem = new ImageIcon(getClass().getResource("/ui/assets/imagens/logo_321KM.png"));
        Image imagemRedimensionada = imagem.getImage().getScaledInstance(220, 72, Image.SCALE_SMOOTH);
        ImageIcon novaImagem = new ImageIcon(imagemRedimensionada);
        JLabel labelImagem = new JLabel(novaImagem);
        labelImagem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtLogin = new JTextField();
        txtLogin.setMaximumSize(new Dimension(300, 30));
        txtLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtLogin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtLogin.setToolTipText("Digite seu login");

        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setMaximumSize(new Dimension(300, 30));
        txtSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtSenha.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtSenha.setToolTipText("Digite sua senha");

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogin.setForeground(new Color(28, 26, 39));

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSenha.setForeground(new Color(28, 26, 39));

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setBackground(Color.BLACK);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setMaximumSize(new Dimension(200, 40));
        btnEntrar.setPreferredSize(new Dimension(200, 40));

        btnEntrar.addActionListener(e -> {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.login(txtLogin.getText(), new String(txtSenha.getPassword()));

            if (u != null) {
                if (u.getTipo() == 0) {
                    new AppView(u).setVisible(true);
                    dispose();
                    // JOptionPane.showMessageDialog(this, "Usuário sem permissão para acessar o sistema.");
                } else {
                    new MainView(u).setVisible(true);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login inválido");
            }
        });

        JLabel esqueceuSenha = new JLabel("Esqueceu a senha?");
        esqueceuSenha.setForeground(Color.decode("#0827B2"));
        esqueceuSenha.setCursor(new Cursor(Cursor.HAND_CURSOR));
        esqueceuSenha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        esqueceuSenha.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Recuperação de senha ainda não implementada.");
            }
        });

        JPanel painelLinkSenha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        painelLinkSenha.setMaximumSize(new Dimension(300, 30)); 
        painelLinkSenha.setBackground(new Color(245, 245, 245));
        painelLinkSenha.add(esqueceuSenha);

        painelLogin.add(Box.createVerticalGlue());
        painelLogin.add(labelImagem);
        painelLogin.add(Box.createVerticalStrut(20));
        painelLogin.add(lblLogin);
        painelLogin.add(txtLogin);
        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(lblSenha);
        painelLogin.add(txtSenha);
        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(painelLinkSenha);
        painelLogin.add(Box.createVerticalStrut(10));
        painelLogin.add(btnEntrar);
        painelLogin.add(Box.createVerticalGlue());

        JLabel copy = new JLabel("Copyright © 2025 - 321KM | Versão 1.0.1");
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copy.setForeground(new Color(102, 102, 102));
        copy.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel painelCinza = new JPanel(new BorderLayout());
        painelCinza.setBackground(Color.LIGHT_GRAY);
        painelCinza.setPreferredSize(new Dimension(300, 500));
        painelCinza.add(copy, BorderLayout.SOUTH);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.X_AXIS));
        painelPrincipal.add(painelLogin);
        painelPrincipal.add(painelCinza);

        setContentPane(painelPrincipal);
    }
}
