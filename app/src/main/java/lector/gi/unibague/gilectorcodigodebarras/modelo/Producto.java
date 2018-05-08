package lector.gi.unibague.gilectorcodigodebarras.modelo;

import java.io.Serializable;

/**
 * Created by Juan David on 7/05/2018.
 */

public class Producto implements Serializable{

    private int codigo;
    private String nombre;
    private int cantidad;

    public Producto(int codigo, String nombre, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}