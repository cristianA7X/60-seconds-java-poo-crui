package logica;

import java.util.ArrayList;

public class Inventario {
    private ArrayList<Item> itemsObtenidos;

    public Inventario() {
        this.itemsObtenidos = new ArrayList<>();
    }

    public void agregarItem(Item item) {
        this.itemsObtenidos.add(item);
    }

    public boolean tieneRecurso(TipoItem tipoRequerido) {
        for (Item item : itemsObtenidos) {
            if (item.getTipo() == tipoRequerido) {
                return true;
            }
        }
        return false;
    }

    public void consumirRecurso(TipoItem tipo) {
        for (int i = 0; i < itemsObtenidos.size(); i++) {
            if (itemsObtenidos.get(i).getTipo() == tipo) {
                itemsObtenidos.remove(i);
                break; 
            }
        }
    }

    public ArrayList<Item> getItemsObtenidos() {
        return itemsObtenidos;
    }
}