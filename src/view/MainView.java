package view;

import model.Usuario;
import view.produto.ProdutoPanel;
import view.usuario.UsuarioPanel;
import view.cliente.ClientePanel;
import view.fornecedor.FornecedorPanel;
import view.vendedor.VendedorPanel;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import controller.FornecedorController;
import dao.UsuarioDAO;

import java.awt.*;
import static com.formdev.flatlaf.FlatClientProperties.STYLE;

public class MainView extends JFrame {

	FornecedorController fornecedorController = new FornecedorController();
	UsuarioDAO usuarioDAO = new UsuarioDAO();
	
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
        cadastros.add(new DefaultMutableTreeNode("Vendedores"));
        cadastros.add(new DefaultMutableTreeNode("Clientes"));

        root.add(cadastros);

        JTree treeMenu = new JTree(root);
        treeMenu.setRootVisible(false);
        treeMenu.setShowsRootHandles(true);
        treeMenu.setBackground(Color.WHITE);
        treeMenu.setOpaque(true);

        JPanel menuLateral = new JPanel(new BorderLayout());
        menuLateral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuLateral.add(new JScrollPane(treeMenu), BorderLayout.CENTER);
        menuLateral.setPreferredSize(new Dimension(240, 0));

        JPanel painelConteudo = new JPanel(new BorderLayout());
        
        painelConteudo.setBackground(Color.WHITE);
        painelConteudo.setOpaque(false);
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Bem-vindo ao sistema da 321KM!");
        titulo.setAlignmentX(CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.BLACK);

        JLabel subtitulo = new JLabel("Sua plataforma de gestão de viagens.");
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitulo.setForeground(Color.BLACK);
        
        painelConteudo.add(Box.createVerticalGlue());
        painelConteudo.add(titulo);
        painelConteudo.add(subtitulo);
        painelConteudo.add(Box.createVerticalGlue()); 

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
                    case "Fornecedores" -> painelConteudo.add(new FornecedorPanel(fornecedorController), BorderLayout.CENTER);
                    case "Vendedores" -> painelConteudo.add(new VendedorPanel(), BorderLayout.CENTER);
                    case "Clientes" -> painelConteudo.add(new ClientePanel(), BorderLayout.CENTER);
                    default -> painelConteudo.add(painelConteudo, BorderLayout.CENTER);
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
        
        JLabel labelUsuario = new JLabel(user.getNome());
        labelUsuario.setOpaque(true);
        //labelUsuario.setBackground(Color.BLACK);
        //labelUsuario.setForeground(Color.WHITE);
        labelUsuario.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
        labelUsuario.putClientProperty(STYLE, "arc: 25; background: #040404; foreground: #FFFFFF;");

        JButton itemLogout = new JButton("Logout");
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
        painelPrincipal.add(menuLateral, BorderLayout.WEST);
        painelPrincipal.add(painelConteudo, BorderLayout.CENTER);

        setContentPane(painelPrincipal);
    }
}
