package logica;

import java.util.ArrayList;

public class GestorPartida {
    private ArrayList<Habitacion> habitaciones;
    private ArrayList<Evento> mazoEventos;
    private Inventario inventario;
    private Refugio refugio;
    private Habitacion habitacionActual;
    private Evento eventoActual;

    public GestorPartida() {
        this.habitaciones = new ArrayList<>();
        this.mazoEventos = new ArrayList<>();
        this.inventario = new Inventario();
        this.refugio = new Refugio();
        
        cargarDatosDePrueba();
    }

    private void cargarDatosDePrueba() {
        // Carga de habitaciones para Fase 1
        Habitacion cocina = new Habitacion("COC", "Cocina");
        cocina.agregarItem(new Item("A1", "Agua", TipoItem.AGUA));
        cocina.agregarItem(new Item("L12", "Lata de Comida", TipoItem.COMIDA));
        
        Habitacion bano = new Habitacion("BAN", "Baño");
        bano.agregarItem(new Item("BOT9", "Botiquín", TipoItem.MEDICAMENTO));
        bano.agregarItem(new Item("H1", "Herramientas", TipoItem.HERRAMIENTA));

        habitaciones.add(cocina);
        habitaciones.add(bano);
        
        // Carga de Eventos para Fase 2
        Evento evento1 = new Evento("Alguien golpea desesperadamente la puerta del refugio pidiendo asilo.");
        evento1.agregarOpcion(new Opcion("Abrir y compartir (Requiere Comida)", TipoItem.COMIDA, 10, 0));
        evento1.agregarOpcion(new Opcion("Ignorar y quedarse en silencio", null, -10, 0)); // Te baja la moral/salud por culpa
        
        Evento evento2 = new Evento("El sistema de ventilación está fallando y el aire se vuelve tóxico.");
        evento2.agregarOpcion(new Opcion("Reparar el filtro (Requiere Herramienta)", TipoItem.HERRAMIENTA, 0, 0));
        evento2.agregarOpcion(new Opcion("Soportar la falta de aire", null, -40, -10));
        
        mazoEventos.add(evento1);
        mazoEventos.add(evento2);
    }

    // --- MÉTODOS PARA SWING: FASE 1 ---
    public boolean cambiarHabitacion(String codigo) {
        for (Habitacion h : habitaciones) {
            if (h.getCodigo().equalsIgnoreCase(codigo)) {
                this.habitacionActual = h;
                return true;
            }
        }
        return false; 
    }

    public boolean intentarRecoger(String codigoTipeado) {
        if (habitacionActual != null) {
            Item recogido = habitacionActual.recogerItem(codigoTipeado);
            if (recogido != null) {
                inventario.agregarItem(recogido);
                return true; 
            }
        }
        return false; 
    }

    // --- MÉTODOS PARA SWING: FASE 2 ---
    public boolean procesarNuevoDia() {
        refugio.avanzarDia();
        
        // 1. Consumo diario
        if (inventario.tieneRecurso(TipoItem.AGUA)) inventario.consumirRecurso(TipoItem.AGUA);
        else refugio.modificarSalud(-15); // Castigo por sed
        
        if (inventario.tieneRecurso(TipoItem.COMIDA)) inventario.consumirRecurso(TipoItem.COMIDA);
        else refugio.modificarSalud(-15); // Castigo por hambre

        // 2. Chequeo de derrota
        if (refugio.esDerrota()) {
            return false; // Retorna falso si el jugador murió
        }

        // 3. Obtener nuevo evento
        int indiceDia = refugio.getDiasSupervividos() - 2; // Día 2 = índice 0
        if (indiceDia >= 0 && indiceDia < mazoEventos.size()) {
            eventoActual = mazoEventos.get(indiceDia);
        } else {
            // Si nos quedamos sin eventos, creamos uno genérico de relleno
            eventoActual = new Evento("Un día tranquilo en el búnker. No pasa nada inusual.");
            eventoActual.agregarOpcion(new Opcion("Descansar", null, 10, 0));
        }
        
        return true; // El jugador sigue vivo
    }

    public void ejecutarOpcion(Opcion seleccionada) {
        seleccionada.aplicarConsecuencias(refugio, inventario);
    }

    public Inventario getInventario() { return inventario; }
    public Refugio getRefugio() { return refugio; }
    public Habitacion getHabitacionActual() { return habitacionActual; }
    public Evento getEventoActual() { return eventoActual; }
}