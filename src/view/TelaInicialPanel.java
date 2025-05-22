package view;

import javax.swing.*;
import java.awt.*;

public class TelaInicialPanel extends JPanel {

    public TelaInicialPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel painelCentral = new JPanel();
        painelCentral.setOpaque(false);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Bem-vindo ao sistema da 321KM!");
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.BLACK);

        JLabel subtitulo = new JLabel("Sua plataforma de gestão de viagens.");
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitulo.setForeground(Color.BLACK);
        
        painelCentral.add(Box.createVerticalGlue());
        painelCentral.add(titulo);
        painelCentral.add(subtitulo);
        painelCentral.add(Box.createVerticalGlue()); 

        add(painelCentral, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(0x5271FF));
        g2.fillRoundRect(30, 30, 300, 150, 80, 80);

        g2.setColor(new Color(0x0CC0DF));
        g2.fillOval(200, 100, 250, 250);

        g2.setColor(new Color(0x0827B2));
        g2.fillRoundRect(getWidth() - 350, getHeight() - 250, 320, 180, 150, 150);

        g2.dispose();
    }
}
