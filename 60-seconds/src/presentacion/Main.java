package presentacion;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FrmPrincipal ventana = new FrmPrincipal();
                ventana.setVisible(true);
            }
        });
    }
}