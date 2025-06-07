package view.fornecedor;

import dao.FornecedorDAO;
import model.Fornecedor;

import javax.swing.*;
import java.awt.*;

public class FornecedorForm extends JDialog {
    public FornecedorForm(Component parent, Fornecedor fornecedor) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(fornecedor == null ? "Novo Fornecedor" : "Editar Fornecedor");
        setSize(600, 500);
        setLocationRelativeTo(parent);

        JTextField txtId = new JTextField(5);
        txtId.setPreferredSize(new Dimension(50, 25));
        txtId.setMaximumSize(new Dimension(50, 25));
        txtId.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(550, 25));
        txtNome.setMaximumSize(new Dimension(550, 25));
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtFantasia = new JTextField();
        txtFantasia.setPreferredSize(new Dimension(280, 25));
        txtFantasia.setMaximumSize(new Dimension(280, 25));
        txtFantasia.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtInscricao = new JTextField();
        txtInscricao.setPreferredSize(new Dimension(280, 25));
        txtInscricao.setMaximumSize(new Dimension(280, 25));
        txtInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtTelefone = new JTextField();
        txtTelefone.setPreferredSize(new Dimension(240, 25));
        txtTelefone.setMaximumSize(new Dimension(240, 25));
        txtTelefone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(320, 25));
        txtEmail.setMaximumSize(new Dimension(320, 25));
        txtEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtEndereco = new JTextField();
        txtEndereco.setPreferredSize(new Dimension(280, 25));
        txtEndereco.setMaximumSize(new Dimension(280, 25));
        txtEndereco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtNumero = new JTextField();
        txtNumero.setPreferredSize(new Dimension(100, 25));
        txtNumero.setMaximumSize(new Dimension(100, 25));
        txtNumero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtComplemento = new JTextField();
        txtComplemento.setPreferredSize(new Dimension(160, 25));
        txtComplemento.setMaximumSize(new Dimension(160, 25));
        txtComplemento.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtBairro = new JTextField();
        txtBairro.setPreferredSize(new Dimension(270, 25));
        txtBairro.setMaximumSize(new Dimension(270, 25));
        txtBairro.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtCidade = new JTextField();
        txtCidade.setPreferredSize(new Dimension(270, 25));
        txtCidade.setMaximumSize(new Dimension(270, 25));
        txtCidade.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtEstado = new JTextField();
        txtEstado.setPreferredSize(new Dimension(100, 25));
        txtEstado.setMaximumSize(new Dimension(100, 25));
        txtEstado.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtCep = new JTextField();
        txtCep.setPreferredSize(new Dimension(160, 25));
        txtCep.setMaximumSize(new Dimension(160, 25));
        txtCep.setAlignmentX(Component.LEFT_ALIGNMENT);

        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Inativo", "Ativo"});
        cbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (fornecedor != null) {
            txtId.setText(String.valueOf(fornecedor.getId()));
            txtNome.setText(fornecedor.getNome());
            txtInscricao.setText(fornecedor.getInscricao());
            txtFantasia.setText(fornecedor.getFantasia());
            txtTelefone.setText(fornecedor.getTelefone());
            txtEmail.setText(fornecedor.getEmail());
            txtEndereco.setText(fornecedor.getEndereco());
            txtNumero.setText(fornecedor.getNumero());
            txtComplemento.setText(fornecedor.getComplemento());
            txtBairro.setText(fornecedor.getBairro());
            txtCidade.setText(fornecedor.getCidade());
            txtEstado.setText(fornecedor.getEstado());
            txtCep.setText(fornecedor.getCEP());
            cbStatus.setSelectedIndex(fornecedor.getStatus());
        }

        txtId.setEnabled(false);

        JButton btnSalvar = new JButton("Confirmar");
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String insc = txtInscricao.getText().trim();
            String fants = txtFantasia.getText().trim();
            String tel = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
            String logr = txtEndereco.getText().trim();
            String num = txtNumero.getText().trim();
            String comp = txtComplemento.getText().trim();
            String bairro = txtBairro.getText().trim();
            String cidade = txtCidade.getText().trim();
            String estado = txtEstado.getText().trim();
            String cep = txtCep.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e Valor são obrigatórios.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Fornecedor p = fornecedor == null ? new Fornecedor() : fornecedor;
            p.setNome(nome);
            p.setInscricao(insc);
            p.setFantasia(fants);
            p.setTelefone(tel);
            p.setEmail(email);
            p.setEndereco(logr);
            p.setNumero(num);
            p.setComplemento(comp);
            p.setBairro(bairro);
            p.setCidade(cidade);
            p.setEstado(estado);
            p.setCEP(cep);
            p.setStatus(cbStatus.getSelectedIndex());

            new FornecedorDAO().salvar(p);
            dispose();
        });

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ID
        JPanel painelId = new JPanel();
        painelId.setLayout(new BoxLayout(painelId, BoxLayout.Y_AXIS));
        JLabel lblId = new JLabel("ID");
        lblId.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtId.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelId.add(lblId);
        painelId.add(txtId);
        painelId.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelNome = new JPanel();
        painelNome.setLayout(new BoxLayout(painelNome, BoxLayout.Y_AXIS));
        JLabel lblNome = new JLabel("Nome");
        lblNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelNome.add(lblNome);
        painelNome.add(txtNome);
        painelNome.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelFantasia = new JPanel();
        painelFantasia.setLayout(new BoxLayout(painelFantasia, BoxLayout.Y_AXIS));
        JLabel lblFantasia = new JLabel("Fantasia");
        lblFantasia.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelFantasia.add(lblFantasia);
        painelFantasia.add(txtFantasia);
        painelFantasia.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelInscricao = new JPanel();
        painelInscricao.setLayout(new BoxLayout(painelInscricao, BoxLayout.Y_AXIS));
        JLabel lblInscricao = new JLabel("Inscrição");
        lblInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInscricao.add(lblInscricao);
        painelInscricao.add(txtInscricao);
        painelInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelTelefone = new JPanel();
        painelTelefone.setLayout(new BoxLayout(painelTelefone, BoxLayout.Y_AXIS));
        JLabel lblTelefone = new JLabel("Telefone");
        lblTelefone.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelTelefone.add(lblTelefone);
        painelTelefone.add(txtTelefone);
        painelTelefone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelEmail = new JPanel();
        painelEmail.setLayout(new BoxLayout(painelEmail, BoxLayout.Y_AXIS));
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelEmail.add(lblEmail);
        painelEmail.add(txtEmail);
        painelEmail.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelEndereco = new JPanel();
        painelEndereco.setLayout(new BoxLayout(painelEndereco, BoxLayout.Y_AXIS));
        JLabel lblEndereco = new JLabel("Endereço");
        lblEndereco.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelEndereco.add(lblEndereco);
        painelEndereco.add(txtEndereco);
        painelEndereco.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelNumero = new JPanel();
        painelNumero.setLayout(new BoxLayout(painelNumero, BoxLayout.Y_AXIS));
        JLabel lblNumero = new JLabel("Número");
        lblNumero.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelNumero.add(lblNumero);
        painelNumero.add(txtNumero);
        painelNumero.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelComplemento = new JPanel();
        painelComplemento.setLayout(new BoxLayout(painelComplemento, BoxLayout.Y_AXIS));
        JLabel lblComplemento = new JLabel("Complemento");
        lblComplemento.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelComplemento.add(lblComplemento);
        painelComplemento.add(txtComplemento);
        painelComplemento.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelBairro = new JPanel();
        painelBairro.setLayout(new BoxLayout(painelBairro, BoxLayout.Y_AXIS));
        JLabel lblBairro = new JLabel("Bairro");
        lblBairro.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBairro.add(lblBairro);
        painelBairro.add(txtBairro);
        painelBairro.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelCidade = new JPanel();
        painelCidade.setLayout(new BoxLayout(painelCidade, BoxLayout.Y_AXIS));
        JLabel lblCidade = new JLabel("Cidade");
        lblCidade.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCidade.add(lblCidade);
        painelCidade.add(txtCidade);
        painelCidade.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelEstado = new JPanel();
        painelEstado.setLayout(new BoxLayout(painelEstado, BoxLayout.Y_AXIS));
        JLabel lblEstado = new JLabel("Estado");
        lblEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelEstado.add(lblEstado);
        painelEstado.add(txtEstado);
        painelEstado.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelCep = new JPanel();
        painelCep.setLayout(new BoxLayout(painelCep, BoxLayout.Y_AXIS));
        JLabel lblCep = new JLabel("CEP");
        lblCep.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCep.add(lblCep);
        painelCep.add(txtCep);
        painelCep.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelStatus = new JPanel();
        painelStatus.setLayout(new BoxLayout(painelStatus, BoxLayout.Y_AXIS));
        JLabel lblStatus = new JLabel("Status");
        lblStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelStatus.add(lblStatus);
        painelStatus.add(cbStatus);
        painelStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Linhas
        JPanel linha1 = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        linha1.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha1.add(painelId);
        linha1.add(Box.createRigidArea(new Dimension(10, 0)));
        linha1.add(painelNome);

        JPanel linha2 = new JPanel();
        linha2.setLayout(new BoxLayout(linha2, BoxLayout.X_AXIS));
        linha2.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha2.add(painelFantasia);
        linha2.add(Box.createRigidArea(new Dimension(10, 0)));
        linha2.add(painelInscricao);

        JPanel linha3 = new JPanel();
        linha3.setLayout(new BoxLayout(linha3, BoxLayout.X_AXIS));
        linha3.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha3.add(painelTelefone);
        linha3.add(Box.createRigidArea(new Dimension(10, 0)));
        linha3.add(painelEmail);

        JPanel linha4 = new JPanel();
        linha4.setLayout(new BoxLayout(linha4, BoxLayout.X_AXIS));
        linha4.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha4.add(painelCep);
        linha4.add(Box.createRigidArea(new Dimension(10, 0)));
        linha4.add(painelEndereco);
        linha4.add(Box.createRigidArea(new Dimension(10, 0)));
        linha4.add(painelNumero);
        linha4.add(Box.createRigidArea(new Dimension(10, 0)));
        linha4.add(painelComplemento);

        JPanel linha5 = new JPanel();
        linha5.setLayout(new BoxLayout(linha5, BoxLayout.X_AXIS));
        linha5.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha5.add(painelBairro);
        linha5.add(Box.createRigidArea(new Dimension(10, 0)));
        linha5.add(painelCidade);
        linha5.add(Box.createRigidArea(new Dimension(10, 0)));
        linha5.add(painelEstado);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBtn.add(btnSalvar);

        JTabbedPane abas = new JTabbedPane();
        
        form.add(linha1);
        form.add(Box.createVerticalStrut(15));
        form.add(linha2);
        form.add(Box.createVerticalStrut(15));
        form.add(linha3);
        form.add(Box.createVerticalStrut(15));
        form.add(linha4);
        form.add(Box.createVerticalStrut(15));
        form.add(linha5);
        form.add(Box.createVerticalStrut(15));
        form.add(painelStatus);
        form.add(Box.createVerticalStrut(20));
        form.add(painelBtn);

        add(form);
        
        abas.addTab("Dados Gerais", form);

        JPanel abaContrato = new JPanel();
        abaContrato.setLayout(new BoxLayout(abaContrato, BoxLayout.Y_AXIS));
        abaContrato.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        abaContrato.add(new JLabel("Contrato"));

        abas.addTab("Contrato", abaContrato);

        setContentPane(abas);
    }
}
