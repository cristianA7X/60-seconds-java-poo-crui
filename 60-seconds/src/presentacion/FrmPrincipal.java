package presentacion;

import logica.GestorPartida;
import logica.Item;
import logica.Opcion;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FrmPrincipal extends JFrame {
    
    private GestorPartida gestor;
    private Timer timerJuego;
    private int tiempoRestante = 60;
    
    private CardLayout cardLayout;
    private JPanel pnlContenedor;
    
    // --- COMPONENTES FASE 1 ---
    private JPanel pnlFase1;
    private JLabel lblTiempo;
    private JLabel lblHabitacionActual;
    private JTextField txtComando;
    private JLabel lblMensaje;
    private JTextArea txaItemsHabitacion; // NUEVO: Muestra las pistas de los ítems
    
    // --- COMPONENTES FASE 2 ---
    private JPanel pnlFase2;
    private JLabel lblDia;
    private JLabel lblSalud;
    private JLabel lblIntegridad;
    private JTextArea txaHistoria;
    
    private JPanel pnlInventario;
    private JLabel lblInvTitulo;
    private JLabel lblInvAgua;
    private JLabel lblInvComida;
    private JLabel lblInvMedicina;
    private JLabel lblInvHerramienta;
    private JLabel lblInvRadio;
    
    private JButton btnOpcion1;
    private JButton btnOpcion2;
    private JButton btnOpcion3;
    private JButton btnOpcion4;
    private JButton btnAvanzarDia;

    public FrmPrincipal() {
        this.gestor = new GestorPartida();
        
        configurarVentana();
        inicializarComponentes();
        configurarEventosFase1();
        configurarEventosFase2();
        
        iniciarTemporizador();
    }

    private void configurarVentana() {
        this.setTitle("60 Segundos - Supervivencia");
        this.setSize(750, 500); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        cardLayout = new CardLayout();
        pnlContenedor = new JPanel(cardLayout);
        this.add(pnlContenedor);
    }

    private void inicializarComponentes() {
        // --- FASE 1 ---
        pnlFase1 = new JPanel(null); 
        pnlFase1.setBackground(new Color(240, 240, 240));
        
        lblTiempo = new JLabel("Tiempo: 60");
        lblTiempo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTiempo.setForeground(Color.RED);
        lblTiempo.setBounds(300, 20, 150, 40);
        pnlFase1.add(lblTiempo);
        
        lblHabitacionActual = new JLabel("Estás en: Pasillo (Tipea COC o BAN)");
        lblHabitacionActual.setFont(new Font("Arial", Font.BOLD, 16));
        lblHabitacionActual.setBounds(30, 70, 400, 30);
        pnlFase1.add(lblHabitacionActual);
        
        // Área para listar las pistas de los objetos disponibles en la habitación
        txaItemsHabitacion = new JTextArea("Escribe COC (Cocina) o BAN (Baño) para entrar.");
        txaItemsHabitacion.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txaItemsHabitacion.setBackground(new Color(230, 230, 230));
        txaItemsHabitacion.setEditable(false);
        txaItemsHabitacion.setBounds(30, 110, 400, 100);
        pnlFase1.add(txaItemsHabitacion);
        
        txtComando = new JTextField();
        txtComando.setFont(new Font("Arial", Font.PLAIN, 18));
        txtComando.setBounds(30, 230, 200, 40);
        pnlFase1.add(txtComando);
        
        lblMensaje = new JLabel("¡Rápido, recolecta todo lo que puedas!");
        lblMensaje.setFont(new Font("Arial", Font.ITALIC, 14));
        lblMensaje.setBounds(30, 280, 450, 30); 
        pnlFase1.add(lblMensaje);
        
        // --- FASE 2 ---
        pnlFase2 = new JPanel(null);
        pnlFase2.setBackground(new Color(40, 40, 45)); 
        
        lblDia = new JLabel("BÚNKER - DÍA 1");
        lblDia.setFont(new Font("Consolas", Font.BOLD, 22));
        lblDia.setForeground(Color.WHITE);
        lblDia.setBounds(20, 20, 200, 30);
        pnlFase2.add(lblDia);
        
        lblSalud = new JLabel("Salud: 100");
        lblSalud.setFont(new Font("Consolas", Font.BOLD, 18));
        lblSalud.setForeground(Color.GREEN);
        lblSalud.setBounds(400, 20, 120, 30);
        pnlFase2.add(lblSalud);

        lblIntegridad = new JLabel("Refugio: 100");
        lblIntegridad.setFont(new Font("Consolas", Font.BOLD, 18));
        lblIntegridad.setForeground(Color.CYAN);
        lblIntegridad.setBounds(550, 20, 150, 30);
        pnlFase2.add(lblIntegridad);
        
        pnlInventario = new JPanel(null);
        pnlInventario.setBounds(20, 70, 180, 250);
        pnlInventario.setBackground(new Color(60, 60, 65));
        pnlInventario.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        lblInvTitulo = new JLabel("INVENTARIO");
        lblInvTitulo.setForeground(Color.WHITE);
        lblInvTitulo.setFont(new Font("Consolas", Font.BOLD, 14));
        lblInvTitulo.setBounds(10, 10, 100, 20);
        pnlInventario.add(lblInvTitulo);

        lblInvAgua = new JLabel("Agua: No");
        lblInvAgua.setForeground(Color.LIGHT_GRAY);
        lblInvAgua.setBounds(10, 40, 150, 20);
        pnlInventario.add(lblInvAgua);

        lblInvComida = new JLabel("Comida: No");
        lblInvComida.setForeground(Color.LIGHT_GRAY);
        lblInvComida.setBounds(10, 70, 150, 20);
        pnlInventario.add(lblInvComida);

        lblInvMedicina = new JLabel("Medicina: No");
        lblInvMedicina.setForeground(Color.LIGHT_GRAY);
        lblInvMedicina.setBounds(10, 100, 150, 20);
        pnlInventario.add(lblInvMedicina);

        lblInvHerramienta = new JLabel("Herramientas: No");
        lblInvHerramienta.setForeground(Color.LIGHT_GRAY);
        lblInvHerramienta.setBounds(10, 130, 150, 20);
        pnlInventario.add(lblInvHerramienta);

        lblInvRadio = new JLabel("Radio: No");
        lblInvRadio.setForeground(Color.LIGHT_GRAY);
        lblInvRadio.setBounds(10, 160, 150, 20);
        pnlInventario.add(lblInvRadio);
        
        pnlFase2.add(pnlInventario);
        
        txaHistoria = new JTextArea("Llegaste al búnker justo a tiempo.\nTu inventario determinará tus posibilidades de sobrevivir.");
        txaHistoria.setFont(new Font("Consolas", Font.PLAIN, 16));
        txaHistoria.setBackground(new Color(20, 20, 20));
        txaHistoria.setForeground(Color.WHITE);
        txaHistoria.setLineWrap(true);
        txaHistoria.setWrapStyleWord(true);
        txaHistoria.setEditable(false);
        txaHistoria.setMargin(new java.awt.Insets(10, 10, 10, 10));
        
        JScrollPane scrollHistoria = new JScrollPane(txaHistoria);
        scrollHistoria.setBounds(220, 70, 490, 250);
        scrollHistoria.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        pnlFase2.add(scrollHistoria);
        
        btnOpcion1 = new JButton();
        btnOpcion1.setBounds(220, 340, 235, 40);
        btnOpcion1.setVisible(false);
        pnlFase2.add(btnOpcion1);
        
        btnOpcion2 = new JButton();
        btnOpcion2.setBounds(475, 340, 235, 40);
        btnOpcion2.setVisible(false);
        pnlFase2.add(btnOpcion2);

        btnOpcion3 = new JButton();
        btnOpcion3.setBounds(220, 390, 235, 40);
        btnOpcion3.setVisible(false);
        pnlFase2.add(btnOpcion3);

        btnOpcion4 = new JButton();
        btnOpcion4.setBounds(475, 390, 235, 40);
        btnOpcion4.setVisible(false);
        pnlFase2.add(btnOpcion4);
        
        btnAvanzarDia = new JButton("Siguiente Día");
        btnAvanzarDia.setBounds(350, 370, 200, 50);
        pnlFase2.add(btnAvanzarDia);
        
        pnlContenedor.add(pnlFase1, "FASE1");
        pnlContenedor.add(pnlFase2, "FASE2");
    }

    private void configurarEventosFase1() {
        txtComando.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tiempoRestante <= 0) return;
                String comando = txtComando.getText().trim();
                
                if (comando.equals("0")) {
                    lblHabitacionActual.setText("Estás en: Pasillo (Tipea COC o BAN)");
                    txaItemsHabitacion.setText("Habitaciones disponibles:\n- [COC] Cocina\n- [BAN] Baño");
                    lblMensaje.setText("");
                    gestor.cambiarHabitacion(""); 
                } 
                else if (gestor.intentarRecoger(comando)) {
                    lblMensaje.setText("¡Ítem " + comando + " recogido!");
                    lblMensaje.setForeground(new Color(0, 150, 0));
                    actualizarPistasHabitacion(); // Refrescamos la lista de objetos del cuarto
                } 
                else {
                    if (gestor.cambiarHabitacion(comando)) {
                        lblHabitacionActual.setText("Entraste a: " + gestor.getHabitacionActual().getNombre());
                        lblMensaje.setText("Tipea el código del ítem para agarrarlo (o '0' para salir).");
                        lblMensaje.setForeground(Color.BLACK);
                        actualizarPistasHabitacion(); // Mostramos los ítems de esta habitación
                    } else {
                        lblMensaje.setText("Código incorrecto o habitación no encontrada.");
                        lblMensaje.setForeground(Color.RED);
                    }
                }
                txtComando.setText(""); 
            }
        });
    }
    
    // Método auxiliar para actualizar las pistas visuales en tiempo real
    private void actualizarPistasHabitacion() {
        if (gestor.getHabitacionActual() == null) {
            txaItemsHabitacion.setText("Habitaciones disponibles:\n- [COC] Cocina\n- [BAN] Baño");
            return;
        }
        
        StringBuilder sb = new StringBuilder("Objetos en este cuarto:\n");
        for (Item it : gestor.getHabitacionActual().getItemsDisponibles()) {
            sb.append("• [").append(it.getCodigo()).append("] ➔ ").append(it.getNombre()).append("\n");
        }
        if (gestor.getHabitacionActual().getItemsDisponibles().isEmpty()) {
            sb.append("(Habitación vacía. Tipea 0 para volver al pasillo)");
        }
        txaItemsHabitacion.setText(sb.toString());
    }

    private void configurarEventosFase2() {
        btnAvanzarDia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean vivo = gestor.procesarNuevoDia();
                actualizarPantallaBunker();
                
                if (!vivo) {
                    txaHistoria.setText("FIN DEL JUEGO.\n\nTu salud o integridad del refugio llegaron al límite.");
                    btnAvanzarDia.setEnabled(false);
                    return;
                }
                
                txaHistoria.setText("DÍA " + gestor.getRefugio().getDiasSupervividos() + "\n\n" + gestor.getEventoActual().getHistoria());
                cargarOpcionesEnPantalla();
            }
        });

        ActionListener listenerOpciones = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton botonPresionado = (JButton) e.getSource();
                
                int indiceOpcion = 0;
                if (botonPresionado == btnOpcion2) indiceOpcion = 1;
                if (botonPresionado == btnOpcion3) indiceOpcion = 2;
                if (botonPresionado == btnOpcion4) indiceOpcion = 3;

                Opcion seleccionada = gestor.getEventoActual().getOpciones().get(indiceOpcion);
                gestor.ejecutarOpcion(seleccionada);
                
                ocultarBotonesOpciones();
                btnAvanzarDia.setVisible(true);
                
                txaHistoria.setText("Decisión tomada. Consecuencias aplicadas.");
                actualizarPantallaBunker();
                
                if (gestor.getRefugio().esDerrota()) {
                    txaHistoria.setText("Esa decisión fue fatal. FIN DEL JUEGO.");
                    btnAvanzarDia.setEnabled(false);
                }
            }
        };

        btnOpcion1.addActionListener(listenerOpciones);
        btnOpcion2.addActionListener(listenerOpciones);
        btnOpcion3.addActionListener(listenerOpciones);
        btnOpcion4.addActionListener(listenerOpciones);
    }

    private void cargarOpcionesEnPantalla() {
        btnAvanzarDia.setVisible(false); 
        JButton[] botones = {btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4};
        
        for (int i = 0; i < 4; i++) {
            if (i < gestor.getEventoActual().getOpciones().size()) {
                Opcion op = gestor.getEventoActual().getOpciones().get(i);
                
                if (!op.esEjecutable(gestor.getInventario())) {
                    botones[i].setText("[🔒] " + op.getDescripcion());
                    botones[i].setEnabled(false);
                } else {
                    botones[i].setText(op.getDescripcion());
                    botones[i].setEnabled(true);
                }
                botones[i].setVisible(true);
            } else {
                botones[i].setVisible(false); 
            }
        }
    }
    
    private void ocultarBotonesOpciones() {
        btnOpcion1.setVisible(false);
        btnOpcion2.setVisible(false);
        btnOpcion3.setVisible(false);
        btnOpcion4.setVisible(false);
    }

    private void iniciarTemporizador() {
        timerJuego = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante--; 
                lblTiempo.setText("Tiempo: " + tiempoRestante);
                if (tiempoRestante <= 0) {
                    timerJuego.stop(); 
                    cambiarABunker();  
                }
            }
        });
        timerJuego.start(); 
    }

    private void cambiarABunker() {
        cardLayout.show(pnlContenedor, "FASE2");
        actualizarPantallaBunker();
        txtComando.setEnabled(false);
    }
    
    private void actualizarPantallaBunker() {
        lblDia.setText("BÚNKER - DÍA " + gestor.getRefugio().getDiasSupervividos());
        
        lblSalud.setText("Salud: " + gestor.getRefugio().getSalud());
        if (gestor.getRefugio().getSalud() > 50) lblSalud.setForeground(Color.GREEN);
        else if (gestor.getRefugio().getSalud() > 20) lblSalud.setForeground(Color.ORANGE);
        else lblSalud.setForeground(Color.RED);
        
        lblIntegridad.setText("Refugio: " + gestor.getRefugio().getIntegridad());
        
        logica.Inventario inv = gestor.getInventario();
        lblInvAgua.setText("Agua: " + (inv.tieneRecurso(logica.TipoItem.AGUA) ? "Sí" : "No"));
        lblInvComida.setText("Comida: " + (inv.tieneRecurso(logica.TipoItem.COMIDA) ? "Sí" : "No"));
        lblInvMedicina.setText("Medicina: " + (inv.tieneRecurso(logica.TipoItem.MEDICAMENTO) ? "Sí" : "No"));
        lblInvHerramienta.setText("Herr.: " + (inv.tieneRecurso(logica.TipoItem.HERRAMIENTA) ? "Sí" : "No"));
    }
}