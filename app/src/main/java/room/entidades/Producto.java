package room.entidades;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "GI_PRODUCTO")
public class Producto implements Serializable{

    @PrimaryKey
    @ColumnInfo(name = "c_codigo" )
    public Long codigo;

    @ColumnInfo(name = "a_nombre")
    public String nombre;

    @ColumnInfo(name = "n_cantidad")
    public int cantidad;

    @ColumnInfo(name = "n_precio")
    public int precio;

    public Producto(Long codigo, String nombre, int cantidad, int precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
