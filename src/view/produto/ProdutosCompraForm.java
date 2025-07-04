package view.produto;

import com.toedter.calendar.JDateChooser;
import dao.ProdutoDAO;
import dao.ProdutosCompraDAO;
import model.Produto;
import model.ProdutosCompra;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class ProdutosCompraForm extends JDialog {
    public ProdutosCompraForm(Component parent, ProdutosCompra produto, int idCompra) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(produto == null ? "Novo Produto" : "Editar Produto");
        setSize(550, 400);
        setLocationRelativeTo(parent);

        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> listaProdutos = dao.listar();

        JComboBox<Produto> comboNome = new JComboBox<>();
        comboNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (Produto p : listaProdutos) comboNome.addItem(p);

        comboNome.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Produto) setText(((Produto) value).getNome());
                return this;
            }
        });

        JTextField txtValorProduto = new JTextField(10);
        JTextField txtDesconto = new JTextField(10);
        JTextField txtQuantidade = new JTextField(10);

        txtValorProduto.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtDesconto.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtQuantidade.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (produto != null) {
            txtValorProduto.setText(String.valueOf(produto.getValorProduto()));
            txtDesconto.setText(String.valueOf(produto.getDesconto()));
            txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        }

        JDateChooser dateInicio = new JDateChooser();
        dateInicio.setDateFormatString("dd/MM/yyyy");
        dateInicio.setDate(produto != null ? produto.getDataInicio() : new Date());

        JDateChooser dateFim = new JDateChooser();
        dateFim.setDateFormatString("dd/MM/yyyy");
        dateFim.setDate(produto != null ? produto.getDataFim() : new Date());

        JTextField txtObs = new JTextField(30);
        txtObs.setText(produto != null ? produto.getObservacao() : "");

        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        cmbStatus.setSelectedIndex(produto != null ? produto.getStatus() : 0);

        if (produto != null) {
            for (int i = 0; i < comboNome.getItemCount(); i++) {
                if (comboNome.getItemAt(i).getId() == produto.getIdProduto()) {
                    comboNome.setSelectedIndex(i);
                    break;
                }
            }
        } else if (comboNome.getItemCount() > 0) comboNome.setSelectedIndex(0);

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
            Produto selecionado = (Produto) comboNome.getSelectedItem();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String venda = txtValorProduto.getText().trim();
            String descontoTxt = txtDesconto.getText().trim();
            String qtdTxt = txtQuantidade.getText().trim();

            if (venda.isEmpty() || descontoTxt.isEmpty() || qtdTxt.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantidade = Integer.parseInt(qtdTxt);
                double valorProduto = Double.parseDouble(venda.replace(",", "."));
                double desconto = Double.parseDouble(descontoTxt.replace(",", "."));
                Date inicio = dateInicio.getDate();
                Date fim = dateFim.getDate();
                String observacao = txtObs.getText();
                int status = cmbStatus.getSelectedIndex();

                ProdutosCompra p = produto == null ? new ProdutosCompra() : produto;
                p.setIdProduto(selecionado.getId());
                p.setNomeProduto(selecionado.getNome());
                p.setIdCompra(idCompra);
                p.setQuantidade(quantidade);
                p.setValorProduto(valorProduto);
                p.setDesconto(desconto);
                p.setDataInicio(inicio);
                p.setDataFim(fim);
                p.setObservacao(observacao);
                p.setStatus(status);

                new ProdutosCompraDAO().salvar(p);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelNome = new JPanel();
        painelNome.setLayout(new BoxLayout(painelNome, BoxLayout.Y_AXIS));
        JLabel lblNome = new JLabel("Produto");
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelNome.add(lblNome);
        painelNome.add(comboNome);
        painelNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelQtd = new JPanel();
        painelQtd.setLayout(new BoxLayout(painelQtd, BoxLayout.Y_AXIS));
        JLabel lblQtd = new JLabel("Quantidade");
        lblQtd.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelQtd.add(lblQtd);
        painelQtd.add(txtQuantidade);

        JPanel painelDesc = new JPanel();
        painelDesc.setLayout(new BoxLayout(painelDesc, BoxLayout.Y_AXIS));
        JLabel lblDesc = new JLabel("Desconto");
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelDesc.add(lblDesc);
        painelDesc.add(txtDesconto);

        JPanel linha2 = new JPanel(new GridLayout(1, 2, 10, 0));
        linha2.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha2.add(painelQtd);
        linha2.add(painelDesc);

        JPanel painelValor = new JPanel();
        painelValor.setLayout(new BoxLayout(painelValor, BoxLayout.Y_AXIS));
        JLabel lblValor = new JLabel("Valor Produto");
        lblValor.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelValor.add(lblValor);
        painelValor.add(txtValorProduto);

        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(lblStatus);
        painelStatus.add(cmbStatus);

        JPanel linha3 = new JPanel(new GridLayout(1, 2, 10, 0));
        linha3.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha3.add(painelValor);
        linha3.add(painelStatus);

        JPanel painelInicio = new JPanel();
        painelInicio.setLayout(new BoxLayout(painelInicio, BoxLayout.Y_AXIS));
        JLabel lblInicio = new JLabel("Início");
        lblInicio.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInicio.add(lblInicio);
        painelInicio.add(dateInicio);

        JPanel painelFim = new JPanel();
        painelFim.setLayout(new BoxLayout(painelFim, BoxLayout.Y_AXIS));
        JLabel lblFim = new JLabel("Fim");
        lblFim.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelFim.add(lblFim);
        painelFim.add(dateFim);

        JPanel linha4 = new JPanel(new GridLayout(1, 2, 10, 0));
        linha4.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha4.add(painelInicio);
        linha4.add(painelFim);

        JPanel painelObs = new JPanel();
        painelObs.setLayout(new BoxLayout(painelObs, BoxLayout.Y_AXIS));
        JLabel lblObs = new JLabel("Observações");
        lblObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelObs.add(lblObs);
        painelObs.add(txtObs);
        painelObs.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.add(btnSalvar);
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(painelNome);
        form.add(Box.createVerticalStrut(15));
        form.add(linha2);
        form.add(Box.createVerticalStrut(15));
        form.add(linha3);
        form.add(Box.createVerticalStrut(15));
        form.add(linha4);
        form.add(Box.createVerticalStrut(15));
        form.add(painelObs);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
        getRootPane().setDefaultButton(btnSalvar);
    }
}
