package view.fornecedor;

import dao.FornecedorDAO;
import dao.ProdutosFornecedorDAO;
import model.Fornecedor;
import model.ProdutosFornecedor;
import view.produto.ProdutosFornecedorPanel;
import dao.ContratoDAO;
import model.Contrato;

import controller.FornecedorController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.toedter.calendar.JDateChooser;

import component.DefaultTableModelNaoEditavel;

import org.apache.pdfbox.Loader;

import java.util.List;

public class FornecedorForm extends JDialog {
	
	private JPanel painelAnexo;
	private JPanel painelImagem;
    private Contrato contratoAtual;
    private FornecedorController controller;
    private Fornecedor fornecedor;
	
    public FornecedorForm(Component parent, Fornecedor fornecedor, FornecedorController controller) {
    	
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);
        
        this.controller = controller;
        this.fornecedor = fornecedor;

        setTitle(fornecedor == null ? "Novo Fornecedor" : "Editar Fornecedor");
        setSize(700, 480);
        setLocationRelativeTo(parent);

        JTextField txtId = new JTextField(5);
        txtId.setPreferredSize(new Dimension(50, 25));
        txtId.setMaximumSize(new Dimension(50, 25));
        txtId.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtNome = new JTextField();
        txtNome.setPreferredSize(new Dimension(550, 25));
        txtNome.setMaximumSize(new Dimension(550, 25));
        txtNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtId.setEnabled(false);

        JTextField txtFantasia = new JTextField();
        txtFantasia.setPreferredSize(new Dimension(280, 25));
        txtFantasia.setMaximumSize(new Dimension(280, 25));
        txtFantasia.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        final MaskFormatter cnpjMask;
        final MaskFormatter cpfMask;
        try {
            cnpjMask = new MaskFormatter("##.###.###/####-##");
            cnpjMask.setPlaceholderCharacter('_');
            cpfMask = new MaskFormatter("###.###.###-##");
            cpfMask.setPlaceholderCharacter('_');
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        JFormattedTextField txtInscricao = new JFormattedTextField(cnpjMask);
        txtInscricao.setPreferredSize(new Dimension(280, 25));
        txtInscricao.setMaximumSize(new Dimension(280, 25));
        txtInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Jurídica", "Física"});
        cbTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        final MaskFormatter telMask;
        try {
			telMask = new MaskFormatter("(##) #####-####");
			telMask.setPlaceholderCharacter('_');
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        
        JFormattedTextField txtTelefone = new JFormattedTextField(telMask);
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
        cbStatus.setPreferredSize(new Dimension(700, 25));
        cbStatus.setMaximumSize(new Dimension(700, 25));
        cbStatus.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (fornecedor != null) {
            txtId.setText(String.valueOf(fornecedor.getId()));
            txtNome.setText(fornecedor.getNome());
            cbTipo.setSelectedIndex(fornecedor.getTipoPessoa());
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
        } else {
            cbStatus.setSelectedIndex(1);
        }

        JButton btnSalvar = new JButton("Confirmar");

        JPanel abaGeral = new JPanel();
        abaGeral.setLayout(new BoxLayout(abaGeral, BoxLayout.Y_AXIS));
        abaGeral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        
        JPanel painelTipo = new JPanel();
        painelTipo.setLayout(new BoxLayout(painelTipo, BoxLayout.Y_AXIS));
        JLabel lblTipo = new JLabel("Tipo Pessoa");
        lblTipo.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelTipo.add(lblTipo);
        painelTipo.add(cbTipo);
        painelTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelInscricao = new JPanel();
        painelInscricao.setLayout(new BoxLayout(painelInscricao, BoxLayout.Y_AXIS));
        JLabel lblInscricao = new JLabel("CNPJ");

        if (fornecedor != null) {
            cbTipo.setSelectedIndex(fornecedor.getTipoPessoa());
            if (fornecedor.getTipoPessoa() == 1) {
                lblInscricao.setText("CPF");
                txtInscricao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(cpfMask));
            } else {
                lblInscricao.setText("CNPJ");
                txtInscricao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(cnpjMask));
            }
            txtInscricao.setText(fornecedor.getInscricao());
        }
	
        cbTipo.addActionListener(e -> {
            txtInscricao.setText(""); 
            txtInscricao.setValue(null);
            if (cbTipo.getSelectedIndex() == 1) {
                lblInscricao.setText("CPF");
                txtInscricao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(cpfMask));
            } else {
                lblInscricao.setText("CNPJ");
                txtInscricao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(cnpjMask));
            }
        });

        lblInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInscricao.add(lblInscricao);
        painelInscricao.add(txtInscricao);
        painelInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel painelFantasia = new JPanel();
        painelFantasia.setLayout(new BoxLayout(painelFantasia, BoxLayout.Y_AXIS));
        JLabel lblFantasia = new JLabel("Fantasia");
        lblFantasia.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelFantasia.add(lblFantasia);
        painelFantasia.add(txtFantasia);
        painelFantasia.setAlignmentX(Component.LEFT_ALIGNMENT);

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
        linha1.add(Box.createRigidArea(new Dimension(10, 0)));
        linha1.add(painelTipo);
        linha1.add(Box.createRigidArea(new Dimension(10, 0)));
        linha1.add(painelInscricao);

        JPanel linha2 = new JPanel();
        linha2.setLayout(new BoxLayout(linha2, BoxLayout.X_AXIS));
        linha2.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha2.add(painelFantasia);
        linha2.add(Box.createRigidArea(new Dimension(10, 0)));
        linha2.add(painelTelefone);
        linha2.add(Box.createRigidArea(new Dimension(10, 0)));
        linha2.add(painelEmail);

        JPanel linha3 = new JPanel();
        linha3.setLayout(new BoxLayout(linha3, BoxLayout.X_AXIS));
        linha3.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha3.add(painelCep);
        linha3.add(Box.createRigidArea(new Dimension(10, 0)));
        linha3.add(painelEndereco);
        linha3.add(Box.createRigidArea(new Dimension(10, 0)));
        linha3.add(painelNumero);
        linha3.add(Box.createRigidArea(new Dimension(10, 0)));
        linha3.add(painelComplemento);

        JPanel linha4 = new JPanel();
        linha4.setLayout(new BoxLayout(linha4, BoxLayout.X_AXIS));
        linha4.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha4.add(painelBairro);
        linha4.add(Box.createRigidArea(new Dimension(10, 0)));
        linha4.add(painelCidade);
        linha4.add(Box.createRigidArea(new Dimension(10, 0)));
        linha4.add(painelEstado);

        JPanel painelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelBtn.add(btnSalvar);

        JTabbedPane abas = new JTabbedPane();

        abaGeral.add(linha2);
        abaGeral.add(Box.createVerticalStrut(15));
        abaGeral.add(linha3);
        abaGeral.add(Box.createVerticalStrut(15));
        abaGeral.add(linha4);
        abaGeral.add(Box.createVerticalStrut(15));
        abaGeral.add(painelStatus);
        abaGeral.add(Box.createVerticalStrut(20));

        abas.addTab("Dados Gerais", abaGeral);

        JPanel abaContrato = new JPanel();
        abaContrato.setLayout(new BorderLayout());
        abaContrato.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField txtIdContrato = new JTextField(5);
        txtIdContrato.setPreferredSize(new Dimension(50, 25));
        txtIdContrato.setMaximumSize(new Dimension(50, 25));
        txtIdContrato.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtIdContrato.setEnabled(false);

        JDateChooser dateInicio = new JDateChooser();
        dateInicio.setDateFormatString("dd/MM/yyyy");
        dateInicio.setMaximumSize(new Dimension(95, 25));
        dateInicio.setAlignmentX(Component.LEFT_ALIGNMENT);

        JDateChooser dateVencimento = new JDateChooser();
        dateVencimento.setDateFormatString("dd/MM/yyyy");
        dateVencimento.setMaximumSize(new Dimension(95, 25));
        dateVencimento.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField txtContato = new JTextField();
        txtContato.setPreferredSize(new Dimension(240, 25));
        txtContato.setMaximumSize(new Dimension(240, 25));
        txtContato.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField txtAnexoPDF = new JTextField();
        Dimension fixedSize = new Dimension(105, 140);

        txtAnexoPDF.setEditable(false);
        txtAnexoPDF.setPreferredSize(fixedSize);
        txtAnexoPDF.setMaximumSize(fixedSize);
        txtAnexoPDF.setMinimumSize(fixedSize);
        txtAnexoPDF.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtAnexoPDF.setBackground(Color.WHITE);

        JButton btnAnexarPDF = new JButton("+");

        btnAnexarPDF.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos PDF", "pdf"));

            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String caminhoCopiado = copiarPDFParaDiretorio(selectedFile);
                if (caminhoCopiado != null) {
                    txtAnexoPDF.setText(caminhoCopiado);
                    try {
                        mostrarImagemDoPDF(new File(caminhoCopiado)); 
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Erro ao carregar PDF: " + e1.getMessage(),
                                "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        
        if (fornecedor != null) {
            contratoAtual = new ContratoDAO().buscarPorFornecedor(fornecedor.getId());
        }
        
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String insc = txtInscricao.getText().replaceAll("\\D", "");
            String fants = txtFantasia.getText().trim();
            String tel = txtTelefone.getText().replaceAll("\\D", "");
            String email = txtEmail.getText().trim();
            String logr = txtEndereco.getText().trim();
            String num = txtNumero.getText().trim();
            String comp = txtComplemento.getText().trim();
            String bairro = txtBairro.getText().trim();
            String cidade = txtCidade.getText().trim();
            String estado = txtEstado.getText().trim();
            String cep = txtCep.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Fornecedor f = fornecedor == null ? new Fornecedor() : fornecedor;

            controller.salvarFornecedor(
                f, nome, insc, cbTipo.getSelectedIndex(),
                fants, tel, email, logr, num, comp, bairro, cidade, estado, cep, cbStatus.getSelectedIndex()
            );

            if (dateInicio.getDate() != null && dateVencimento.getDate() != null) {
                Contrato contrato = contratoAtual == null ? new Contrato() : contratoAtual;
                controller.salvarContrato(
                    f,
                    contrato,
                    dateInicio.getDate(),
                    dateVencimento.getDate(),
                    txtContato.getText().trim(),
                    txtAnexoPDF.getText().isEmpty() ? null : txtAnexoPDF.getText()
                );
            } else if (contratoAtual != null) {
                controller.excluirContrato(contratoAtual.getId());
            }

            dispose();
        });

        
        // Painel ID Contrato
        JPanel painelIdContrato = new JPanel();
        painelIdContrato.setLayout(new BoxLayout(painelIdContrato, BoxLayout.Y_AXIS));
        JLabel lblIdContrato = new JLabel("ID");
        lblIdContrato.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelIdContrato.add(lblIdContrato);
        painelIdContrato.add(txtIdContrato);

        // Painel Data Início
        JPanel painelInicio = new JPanel();
        painelInicio.setLayout(new BoxLayout(painelInicio, BoxLayout.Y_AXIS));
        JLabel lblInicio = new JLabel("Data Início");
        lblInicio.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelInicio.add(lblInicio);
        painelInicio.add(dateInicio);

        // Painel Data Vencimento
        JPanel painelVenc = new JPanel();
        painelVenc.setLayout(new BoxLayout(painelVenc, BoxLayout.Y_AXIS));
        JLabel lblVenc = new JLabel("Data Final");
        lblVenc.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelVenc.add(lblVenc);
        painelVenc.add(dateVencimento);

        JPanel painelContato = new JPanel();
        painelContato.setLayout(new BoxLayout(painelContato, BoxLayout.Y_AXIS));
        JLabel lblContato = new JLabel("Contato");
        lblContato.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelContato.add(lblContato);
        painelContato.add(txtContato);
        painelContato.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Painel do PDF
        painelImagem = new JPanel();
        painelImagem.setLayout(new BorderLayout());
        painelImagem.setPreferredSize(fixedSize);
        painelImagem.setMaximumSize(fixedSize);
        painelImagem.setMinimumSize(fixedSize);
        painelImagem.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Painel campo + botão
        JPanel painelCampoBotao = new JPanel();
        painelCampoBotao.setLayout(new BoxLayout(painelCampoBotao, BoxLayout.X_AXIS));
        painelCampoBotao.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCampoBotao.add(Box.createRigidArea(new Dimension(5, 0)));
        painelCampoBotao.add(btnAnexarPDF);

        // Painel Geral
        painelAnexo = new JPanel();
        painelAnexo.setLayout(new BoxLayout(painelAnexo, BoxLayout.X_AXIS));
        painelAnexo.add(painelImagem);
        painelAnexo.add(Box.createRigidArea(new Dimension(0,5)));
        painelAnexo.add(painelCampoBotao);
        
        if (contratoAtual != null) {
            txtIdContrato.setText(String.valueOf(contratoAtual.getId()));
            dateInicio.setDate(contratoAtual.getDataInicio());
            dateVencimento.setDate(contratoAtual.getDataVencimento());
            txtContato.setText(contratoAtual.getContato());
            String caminho = contratoAtual.getCaminho();
            txtAnexoPDF.setText(caminho);

            if (caminho != null && !caminho.isEmpty()) {
                File file = new File(caminho);
                if (file.exists()) {
                    try {
                        mostrarImagemDoPDF(file);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Erro ao carregar PDF: " + e.getMessage(),
                                "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Arquivo PDF não encontrado:\n" + caminho,
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        // Linha 1 - ID Contrato, Data Início, Vencimento
        JPanel linhaC1 = new JPanel();
        linhaC1.setLayout(new BoxLayout(linhaC1, BoxLayout.X_AXIS));
        linhaC1.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaC1.add(painelIdContrato);
        linhaC1.add(Box.createRigidArea(new Dimension(10, 0)));
        linhaC1.add(painelInicio);
        linhaC1.add(Box.createRigidArea(new Dimension(10, 0)));
        linhaC1.add(painelVenc);
        linhaC1.add(Box.createRigidArea(new Dimension(10, 0)));
        linhaC1.add(painelContato);
        linhaC1.add(Box.createRigidArea(new Dimension(10, 0)));
        linhaC1.add(painelAnexo);

        // Painel Contrato
        abaContrato.setLayout(new BorderLayout());
        abaContrato.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        abaContrato.add(linhaC1, BorderLayout.NORTH);

        if (fornecedor != null) {
            ProdutosFornecedorPanel produtosPanel = new ProdutosFornecedorPanel(fornecedor.getId());
            produtosPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
            abaContrato.add(produtosPanel, BorderLayout.CENTER);
        }

        if (contratoAtual != null) {
	        abas.addTab("Contrato", abaContrato);
	        abas.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        }

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topo = new JPanel();
        topo.setLayout(new BorderLayout());
        topo.add(linha1, BorderLayout.CENTER);
        
        mainPanel.add(topo, BorderLayout.NORTH);
        mainPanel.add(abas, BorderLayout.CENTER);
        mainPanel.add(painelBtn, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
    
    private String copiarPDFParaDiretorio(File arquivoOrigem) {
        String pastaDestino = "data/contratos/For_" + fornecedor.getId();
        File pasta = new File(pastaDestino);
        if (!pasta.exists()) pasta.mkdirs();

        String nomeArquivo = "Contrato_" + arquivoOrigem.getName();
        File destino = new File(pasta, nomeArquivo);

        try {
            java.nio.file.Files.copy(arquivoOrigem.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            return destino.getAbsolutePath();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao copiar o PDF: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    private void mostrarImagemDoPDF(File file) throws IOException {
        painelImagem.removeAll();

        try (PDDocument document = Loader.loadPDF(file)) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
            ImageIcon icon = new ImageIcon(bim.getScaledInstance(105, 140, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(icon);

            painelImagem.add(label, BorderLayout.CENTER);
            painelImagem.revalidate();
            painelImagem.repaint();
        }
    }
}
