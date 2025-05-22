package component;

import javax.swing.table.DefaultTableModel;

public class DefaultTableModelNaoEditavel extends DefaultTableModel {
    public DefaultTableModelNaoEditavel(String[] colunas, int linhas) {
        super(colunas, linhas);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Nenhuma célula pode ser editada
    }
}
