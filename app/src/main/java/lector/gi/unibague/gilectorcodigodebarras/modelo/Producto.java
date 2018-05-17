package lector.gi.unibague.gilectorcodigodebarras.modelo;

import java.io.Serializable;

/**
 * Created by Juan David on 7/05/2018.
 */

public class Producto implements Serializable{

    private Long codigo;
    private String nombre;
    private int cantidadEnStock;
    private int precio;

    private int cantidadVendida;

    public Producto(Long codigo, String nombre, int cantidad, int precio) throws Exception {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidadEnStock = cantidad;
        this.precio = precio;
        if(cantidadVendida > cantidadEnStock)
            throw new Exception("No hay cantidad suficiente productos para vender");
        this.cantidadVendida = 1;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) throws Exception {
        if(cantidadVendida > cantidadEnStock)
            throw new Exception("No hay cantidad suficiente productos para vender");
        this.cantidadVendida = cantidadVendida;
    }
}