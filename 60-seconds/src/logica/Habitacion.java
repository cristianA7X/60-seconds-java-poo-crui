package logica;

import java.util.ArrayList;

public class Habitacion {
    private String codigo;
    private String nombre;
    private ArrayList<Item> itemsDisponibles;

    public Habitacion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.itemsDisponibles = new ArrayList<>();
    }

    public void agregarItem(Item item) {
        this.itemsDisponibles.add(item);
    }

    public Item recogerItem(String codigoTipeado) {
        for (Item item : itemsDisponibles) {
            if (item.getCodigo().equalsIgnoreCase(codigoTipeado)) {
                itemsDisponibles.remove(item);
                return item;
            }
        }
        return null; 
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public ArrayList<Item> getItemsDisponibles() { return itemsDisponibles; }
}