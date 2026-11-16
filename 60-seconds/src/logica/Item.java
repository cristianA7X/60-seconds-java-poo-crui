package logica;

public class Item {
    private String codigo;
    private String nombre;
    private TipoItem tipo;

    public Item(String codigo, String nombre, TipoItem tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoItem getTipo() {
        return tipo;
    }
}