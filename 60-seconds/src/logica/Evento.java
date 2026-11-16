package logica;
import java.util.ArrayList;

public class Evento {
    private String historia;
    private ArrayList<Opcion> opciones;

    public Evento(String historia) {
        this.historia = historia;
        this.opciones = new ArrayList<>();
    }

    public void agregarOpcion(Opcion opcion) {
        this.opciones.add(opcion);
    }

    public String getHistoria() {
        return historia;
    }

    public ArrayList<Opcion> getOpciones() {
        return opciones;
    }
}