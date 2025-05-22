package view;

import javax.swing.*;
import java.awt.*;

public class AuthView extends JFrame {
    public AuthView() {
    	setTitle("321KM");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelMaior = new JPanel();
        painelMaior.setBackground(new Color(245, 245, 245));
        painelMaior.setLayout(new BoxLayout(painelMaior, BoxLayout.Y_AXIS));

        ImageIcon imagem = new ImageIcon(getClass().getResource("/ui/assets/imagens/logo_321KM.png"));
        Image imagemRedimensionada = imagem.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        ImageIcon novaImagem = new ImageIcon(imagemRedimensionada);
        JLabel labelImagem = new JLabel(novaImagem);
        labelImagem.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnEntrar = new JButton("Entre");
        JButton btnCadastrar = new JButton("Cadastre-se");

        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setBackground(Color.BLACK);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setMaximumSize(new Dimension(250, 40));
        btnEntrar.setPreferredSize(new Dimension(250, 40));
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnEntrar.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        btnCadastrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCadastrar.setBackground(Color.BLACK);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFocusPainted(false);
        btnCadastrar.setMaximumSize(new Dimension(250, 40));
        btnCadastrar.setPreferredSize(new Dimension(250, 40));
        btnCadastrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCadastrar.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        JLabel ouLabel = new JLabel("Ou");
        ouLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ouLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        ouLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        painelMaior.add(Box.createVerticalGlue());
        painelMaior.add(labelImagem);
        painelMaior.add(btnEntrar);
        painelMaior.add(ouLabel);
        painelMaior.add(btnCadastrar);
        painelMaior.add(Box.createVerticalGlue());

        JLabel copy = new JLabel("Copyright © 2025 - 321KM | Versão 1.0.1");
        copy.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        copy.setForeground(new Color(102, 102, 102)); 
        copy.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel painelMenor = new JPanel(new BorderLayout());
        painelMenor.setBackground(Color.LIGHT_GRAY);
        painelMenor.add(copy, BorderLayout.SOUTH);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.X_AXIS));

        painelPrincipal.add(painelMaior);
        painelPrincipal.add(painelMenor);

        painelMaior.setPreferredSize(new Dimension(500, 500));
        painelMenor.setPreferredSize(new Dimension(300, 500));

        setContentPane(painelPrincipal);
    }
}
