package logica;

public class Refugio {
    private int salud;
    private int integridad;
    private int diasSupervividos;

    public Refugio() {
        this.salud = 100;
        this.integridad = 100;
        this.diasSupervividos = 1;
    }

    public void modificarSalud(int cantidad) {
        this.salud += cantidad;
        if (this.salud > 100) this.salud = 100;
        if (this.salud < 0) this.salud = 0;
    }

    public void modificarIntegridad(int cantidad) {
        this.integridad += cantidad;
        if (this.integridad > 100) this.integridad = 100;
        if (this.integridad < 0) this.integridad = 0;
    }

    public void avanzarDia() {
        this.diasSupervividos++;
    }

    public boolean esDerrota() {
        return salud <= 0 || integridad <= 0;
    }

    // Getters para que Swing actualice las barras/etiquetas
    public int getSalud() { return salud; }
    public int getIntegridad() { return integridad; }
    public int getDiasSupervividos() { return diasSupervividos; }
}