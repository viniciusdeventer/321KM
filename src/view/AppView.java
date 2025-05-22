package view;

import javax.swing.*;

import model.Usuario;
import view.produto.ProdutoPanel;
import view.usuario.UsuarioPanel;

import java.awt.*;

public class AppView extends JFrame {
    public AppView(Usuario user) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        setContentPane(painelPrincipal);
    }
}

