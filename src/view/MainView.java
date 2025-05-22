package view;

import model.Usuario;
import view.produto.ProdutoPanel;
import view.usuario.UsuarioPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public class MainView extends JFrame {
    public MainView(Usuario user) {
        setTitle("321KM");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");

        DefaultMutableTreeNode cadastros = new DefaultMutableTreeNode("Cadastros");
        cadastros.add(new DefaultMutableTreeNode("Usuários"));
        cadastros.add(new DefaultMutableTreeNode("Produtos"));

        DefaultMutableTreeNode relatorios = new DefaultMutableTreeNode("Relatórios");

        root.add(cadastros);
        root.add(relatorios);

        JTree treeMenu = new JTree(root);
        treeMenu.setRootVisible(false);
        treeMenu.setShowsRootHandles(true);

        JScrollPane scrollPane = new JScrollPane(treeMenu);
        scrollPane.setPreferredSize(new Dimension(220, 0));

        JPanel painelConteudo = new JPanel(new BorderLayout());

        TelaInicialPanel telaInicial = new TelaInicialPanel();
        painelConteudo.add(telaInicial, BorderLayout.CENTER);

        treeMenu.addTreeSelectionListener((TreeSelectionEvent e) -> {
            TreePath path = e.getPath();
            Object selectedNode = path.getLastPathComponent();

            if (selectedNode instanceof DefaultMutableTreeNode node) {
                if (!node.isLeaf()) return; 

                String selected = node.toString();
                painelConteudo.removeAll();

                switch (selected) {
                    case "Usuários" -> painelConteudo.add(new UsuarioPanel(), BorderLayout.CENTER);
                    case "Produtos" -> painelConteudo.add(new ProdutoPanel(), BorderLayout.CENTER);
                    default -> painelConteudo.add(telaInicial, BorderLayout.CENTER);  // fallback
                }

                painelConteudo.revalidate();
                painelConteudo.repaint();
            }
        });

        painelPrincipal.add(scrollPane, BorderLayout.WEST);
        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);

        setContentPane(painelPrincipal);
    }
}
