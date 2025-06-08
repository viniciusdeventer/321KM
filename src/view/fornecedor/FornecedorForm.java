package view.fornecedor;

import dao.FornecedorDAO;
import model.Fornecedor;

import dao.ContratoDAO;
import model.Contrato;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.toedter.calendar.JDateChooser;
import org.apache.pdfbox.Loader;

public class FornecedorForm extends JDialog {
	
	private JPanel painelAnexo;
    private Contrato contratoAtual;
	
    public FornecedorForm(Component parent, Fornecedor fornecedor) {
        super(SwingUtilities.getWindowAncestor(parent) instanceof Frame
                ? (Frame) SwingUtilities.getWindowAncestor(parent)
                : null, true);

        setTitle(fornecedor == null ? "Novo Fornecedor" : "Editar Fornecedor");
        setSize(700, 450);
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

        JTextField txtInscricao = new JTextField();
        txtInscricao.setPreferredSize(new Dimension(280, 25));
        txtInscricao.setMaximumSize(new Dimension(280, 25));
        txtInscricao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Jurídica", "Física"});
        cbTipo.setAlignmentX(Component.LEFT_ALIGNMENT);

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
	         } else {
	             lblInscricao.setText("CNPJ");
	         }
	     }
	
	     cbTipo.addActionListener(e -> {
	         if (cbTipo.getSelectedIndex() == 1) {
	             lblInscricao.setText("CPF");
	         } else {
	             lblInscricao.setText("CNPJ");
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
        abaGeral.add(painelBtn);

        abas.addTab("Dados Gerais", abaGeral);

        JPanel abaContrato = new JPanel();
        abaContrato.setLayout(new BoxLayout(abaContrato, BoxLayout.Y_AXIS));
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
                txtAnexoPDF.setText(selectedFile.getAbsolutePath());

                try {
                    PDDocument document = Loader.loadPDF(selectedFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(document);

                    BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
                    ImageIcon icon = new ImageIcon(bim.getScaledInstance(105, 140, Image.SCALE_SMOOTH));
                    JLabel label = new JLabel(icon);

                    JPanel pdfPanel = new JPanel(new BorderLayout());
                    pdfPanel.add(label, BorderLayout.CENTER);
                    pdfPanel.setPreferredSize(fixedSize);
                    pdfPanel.setMaximumSize(fixedSize);
                    pdfPanel.setMinimumSize(fixedSize);

                    painelAnexo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    painelAnexo.setPreferredSize(fixedSize);
                    painelAnexo.setMaximumSize(fixedSize);
                    painelAnexo.setMinimumSize(fixedSize);

                    painelAnexo.removeAll();
                    painelAnexo.add(pdfPanel);
                    painelAnexo.revalidate();
                    painelAnexo.repaint();

                    document.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar PDF: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        contratoAtual = new ContratoDAO().buscarPorFornecedor(fornecedor.getId());
        if (contratoAtual != null) {
            txtIdContrato.setText(String.valueOf(contratoAtual.getId()));
            dateInicio.setDate(contratoAtual.getDataInicio());
            dateVencimento.setDate(contratoAtual.getDataVencimento());
            txtAnexoPDF.setText(contratoAtual.getCaminho());
            
            // Carrega visualização do PDF se existir
            if (contratoAtual.getCaminho() != null && !contratoAtual.getCaminho().isEmpty()) {
                carregarPDFVisualizacao(contratoAtual.getCaminho());
            }
        }

        btnSalvar.addActionListener(e -> {
            // Dados do Fornecedor
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

            // Validação básica
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório.", "Preenchimento Obrigatório", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Salvar/Atualizar Fornecedor
            Fornecedor f = fornecedor == null ? new Fornecedor() : fornecedor;
            f.setNome(nome);
            f.setInscricao(insc);
            f.setTipoPessoa(cbTipo.getSelectedIndex());
            f.setFantasia(fants);
            f.setTelefone(tel);
            f.setEmail(email);
            f.setEndereco(logr);
            f.setNumero(num);
            f.setComplemento(comp);
            f.setBairro(bairro);
            f.setCidade(cidade);
            f.setEstado(estado);
            f.setCEP(cep);
            f.setStatus(cbStatus.getSelectedIndex());

            FornecedorDAO fornecedorDAO = new FornecedorDAO();
            fornecedorDAO.salvar(f);
            
            // 2. Tratamento do contrato
            if (dateInicio.getDate() != null && dateVencimento.getDate() != null) {
                Contrato contrato = contratoAtual == null ? new Contrato() : contratoAtual;
                
                // Garante a associação correta
                contrato.setFornecedor(f); // Usa o objeto fornecedor já salvo
                
                contrato.setDataInicio(dateInicio.getDate());
                contrato.setDataVencimento(dateVencimento.getDate());
                
                if (!txtAnexoPDF.getText().isEmpty()) {
                    contrato.setCaminho(txtAnexoPDF.getText());
                }
                
                // Configura status padrão se for novo contrato
                if (contratoAtual == null) {
                    contrato.setStatus(1); // 1 = Ativo
                }
                
                new ContratoDAO().salvar(contrato);
            } else if (contratoAtual != null) {
                new ContratoDAO().excluir(contratoAtual.getId());
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

        // Painel PDF
        JPanel linhaCampoEBotao = new JPanel();
        linhaCampoEBotao.setLayout(new BoxLayout(linhaCampoEBotao, BoxLayout.X_AXIS));
        linhaCampoEBotao.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaCampoEBotao.add(txtAnexoPDF);
        linhaCampoEBotao.add(Box.createRigidArea(new Dimension(5, 0)));
        linhaCampoEBotao.add(btnAnexarPDF);

        painelAnexo = new JPanel();
        painelAnexo.setLayout(new BoxLayout(painelAnexo, BoxLayout.Y_AXIS));
        painelAnexo.add(Box.createRigidArea(new Dimension(0, 5)));
        painelAnexo.add(linhaCampoEBotao);

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
        linhaC1.add(painelAnexo);

        // Painel Contrato
        abaContrato.setLayout(new BoxLayout(abaContrato, BoxLayout.Y_AXIS));
        abaContrato.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        abaContrato.add(linhaC1);
        abaContrato.add(Box.createVerticalStrut(15));

        abas.addTab("Contrato", abaContrato);
        abas.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topo = new JPanel();
        topo.setLayout(new BorderLayout());
        topo.add(linha1, BorderLayout.CENTER);
        
        mainPanel.add(topo, BorderLayout.NORTH);
        mainPanel.add(abas, BorderLayout.CENTER);

        setContentPane(mainPanel);

    }
    
    private void carregarPDFVisualizacao(String caminhoPDF) {
        try {
            File file = new File(caminhoPDF);
            if (file.exists()) {
                PDDocument document = Loader.loadPDF(file);
                PDFRenderer pdfRenderer = new PDFRenderer(document);

                BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);
                ImageIcon icon = new ImageIcon(bim.getScaledInstance(105, 140, Image.SCALE_SMOOTH));
                JLabel label = new JLabel(icon);

                JPanel pdfPanel = new JPanel(new BorderLayout());
                pdfPanel.add(label, BorderLayout.CENTER);
                pdfPanel.setPreferredSize(new Dimension(105, 140));
                pdfPanel.setMaximumSize(new Dimension(105, 140));
                pdfPanel.setMinimumSize(new Dimension(105, 140));

                painelAnexo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                painelAnexo.setPreferredSize(new Dimension(105, 140));
                painelAnexo.setMaximumSize(new Dimension(105, 140));
                painelAnexo.setMinimumSize(new Dimension(105, 140));

                painelAnexo.removeAll();
                painelAnexo.add(pdfPanel);
                painelAnexo.revalidate();
                painelAnexo.repaint();

                document.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar PDF: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
