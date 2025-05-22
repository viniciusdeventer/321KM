package component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Function;

public class TabelaUtils {
    public static <T> void adicionarDuploClique(
            JTable tabela,
            DefaultTableModel modelo,
            Function<Integer, T> buscarPorId,
            Consumer<T> abrirFormulario
    ) {
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    int linha = tabela.rowAtPoint(e.getPoint());
                    if (linha >= 0) {
                        int modelRow = tabela.convertRowIndexToModel(linha);
                        int id = (int) modelo.getValueAt(modelRow, 0);
                        T objeto = buscarPorId.apply(id);
                        if (objeto != null) {
                            abrirFormulario.accept(objeto);
                        }
                    }
                }
            }
        });
    }
}
