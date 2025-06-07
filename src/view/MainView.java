package view;

import model.Usuario;
import view.produto.ProdutoPanel;
import view.usuario.UsuarioPanel;
import view.fornecedor.FornecedorPanel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import static com.formdev.flatlaf.FlatClientProperties.STYLE;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatClientProperties;

public class MainView extends JFrame {

    public MainView(Usuario user) {
        setTitle("321KM");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Menu lateral
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Menu");
        DefaultMutableTreeNode cadastros = new DefaultMutableTreeNode("Cadastros");
        cadastros.add(new DefaultMutableTreeNode("Usuários"));
        cadastros.add(new DefaultMutableTreeNode("Produtos"));
        cadastros.add(new DefaultMutableTreeNode("Fornecedores"));

        root.add(cadastros);

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
                    case "Fornecedores" -> painelConteudo.add(new FornecedorPanel(), BorderLayout.CENTER);
                    default -> painelConteudo.add(telaInicial, BorderLayout.CENTER);
                }

                painelConteudo.revalidate();
                painelConteudo.repaint();
            }
        });

        ImageIcon imagemOriginal = new ImageIcon(getClass().getResource("/ui/assets/imagens/logo_321KM.png"));
        Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(100, 32, Image.SCALE_SMOOTH);
        ImageIcon imagem = new ImageIcon(imagemRedimensionada);
        JLabel labelImagem = new JLabel(imagem);

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        painelTopo.add(labelImagem, BorderLayout.WEST);

        JPanel painelDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        
        JLabel labelUsuario = new JLabel("Teste de Nome" + user.getId());
        labelUsuario.setOpaque(true);
        //labelUsuario.setBackground(Color.BLACK);
        //labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        labelUsuario.putClientProperty(STYLE, "arc: 25; background: #040404; foreground: #FFFFFF;");

        JMenuItem itemLogout = new JMenuItem("Logout");
        itemLogout.addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
            }
        });

        painelDireita.add(labelUsuario);
        painelDireita.add(itemLogout);
        painelTopo.add(painelDireita, BorderLayout.EAST);

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.WEST);
        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);

        setContentPane(painelPrincipal);
    }
}
