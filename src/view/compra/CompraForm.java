package view.compra;

import dao.ClienteDAO;
import dao.CompraDAO;
import dao.VendedorDAO;
import model.Cliente;
import model.Compra;
import model.Vendedor;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;

public class CompraForm extends JDialog {
    public CompraForm(Component parent, Compra compra) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(compra == null ? "Nova Compra" : "Editar Compra");
        setSize(500, 270);
        setLocationRelativeTo(parent);

        JTextField txtId = new JTextField(5);
        txtId.setPreferredSize(new Dimension(50, 25));
        txtId.setMaximumSize(new Dimension(50, 25));
        txtId.setEnabled(false);

        JComboBox<Vendedor> cbVendedor = new JComboBox<>();
        cbVendedor.setPreferredSize(new Dimension(300, 25));
        cbVendedor.setMaximumSize(new Dimension(300, 25));

        cbVendedor.addItem(null);
        for (Vendedor v : new VendedorDAO().listar()) {
            cbVendedor.addItem(v);
        }

        cbVendedor.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Vendedor) {
                    setText(((Vendedor) value).getNome());
                } else {
                    setText("Selecione");
                }
                return this;
            }
        });

        JDateChooser dataCompra = new JDateChooser();
        dataCompra.setDateFormatString("dd/MM/yyyy");
        dataCompra.setPreferredSize(new Dimension(120, 25));
        dataCompra.setMaximumSize(new Dimension(120, 25));

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});
        cbStatus.setPreferredSize(new Dimension(100, 25));
        cbStatus.setMaximumSize(new Dimension(100, 25));

        if (compra != null) {
            txtId.setText(String.valueOf(compra.getId()));

            for (int i = 1; i < cbVendedor.getItemCount(); i++) {
                if (cbVendedor.getItemAt(i).getId() == compra.getIdVendedor()) {
                    cbVendedor.setSelectedIndex(i);
                    break;
                }
            }

            dataCompra.setDate(compra.getDataCompra());
            cbStatus.setSelectedIndex(compra.getStatus());
        } else {
            cbStatus.setSelectedIndex(1);
        }

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
            Vendedor vendedor = (Vendedor) cbVendedor.getSelectedItem();
            java.util.Date data = dataCompra.getDate();

            if (vendedor == null || data == null) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Compra v = compra == null ? new Compra() : compra;
            v.setIdVendedor(vendedor.getId());
            v.setDataCompra(new java.sql.Date(data.getTime()));
            v.setStatus(cbStatus.getSelectedIndex());

            new CompraDAO().salvar(v);
            dispose();
        });

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelId = new JPanel(new BorderLayout(5, 0));
        JLabel lblId = new JLabel("ID");
        lblId.setHorizontalAlignment(SwingConstants.LEFT);
        painelId.add(lblId, BorderLayout.NORTH);
        painelId.add(txtId, BorderLayout.CENTER);
        painelId.setMaximumSize(new Dimension(80, 50));

        JPanel painelData = new JPanel(new BorderLayout(5, 0));
        JLabel lblData = new JLabel("Data Compra");
        lblData.setHorizontalAlignment(SwingConstants.LEFT);
        painelData.add(lblData, BorderLayout.NORTH);
        painelData.add(dataCompra, BorderLayout.CENTER);
        painelData.setMaximumSize(new Dimension(140, 50));

        JPanel painelStatus = new JPanel(new BorderLayout(5, 0));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
        painelStatus.add(lblStatus, BorderLayout.NORTH);
        painelStatus.add(cbStatus, BorderLayout.CENTER);
        painelStatus.setMaximumSize(new Dimension(120, 50));

        JPanel linha1 = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        linha1.add(painelId);
        linha1.add(Box.createRigidArea(new Dimension(15, 0)));
        linha1.add(painelData);
        linha1.add(Box.createRigidArea(new Dimension(15, 0)));
        linha1.add(painelStatus);
        linha1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelLinha1Wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelLinha1Wrapper.add(linha1);
        painelLinha1Wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelVendedor = new JPanel(new BorderLayout(5, 0));
        JLabel lblVendedor = new JLabel("Vendedor");
        lblVendedor.setHorizontalAlignment(SwingConstants.LEFT);
        painelVendedor.add(lblVendedor, BorderLayout.NORTH);
        painelVendedor.add(cbVendedor, BorderLayout.CENTER);
        painelVendedor.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        painelVendedor.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.add(btnSalvar);
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(painelLinha1Wrapper);
        form.add(Box.createVerticalStrut(15));
        form.add(painelVendedor);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
    }
}
