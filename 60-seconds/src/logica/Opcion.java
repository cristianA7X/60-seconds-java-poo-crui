package logica;

public class Opcion {
    private String descripcion;
    private TipoItem requerimiento; // Puede ser null si es una opción siempre disponible (ej: ignorar)
    private int efectoSalud;
    private int efectoIntegridad;

    public Opcion(String descripcion, TipoItem requerimiento, int efectoSalud, int efectoIntegridad) {
        this.descripcion = descripcion;
        this.requerimiento = requerimiento;
        this.efectoSalud = efectoSalud;
        this.efectoIntegridad = efectoIntegridad;
    }

    // La opción determina si se puede clickear o no
    public boolean esEjecutable(Inventario inventario) {
        if (requerimiento == null) {
            return true;
        }
        return inventario.tieneRecurso(requerimiento);
    }

    // Si el jugador la elige, la opción aplica los cambios
    public void aplicarConsecuencias(Refugio refugio, Inventario inventario) {
        if (requerimiento != null) {
            inventario.consumirRecurso(requerimiento);
        }
        refugio.modificarSalud(efectoSalud);
        refugio.modificarIntegridad(efectoIntegridad);
    }

    public String getDescripcion() {
        return descripcion;
    }
}