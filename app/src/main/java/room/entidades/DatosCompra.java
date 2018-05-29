package room.entidades;

import android.arch.persistence.room.ColumnInfo;

public class DatosCompra {

    @ColumnInfo(name = "d_nombre")
    public String nombreCliente;

    @ColumnInfo(name = "f_fecha")
    public String fecha;

    @ColumnInfo(name = "n_total_compra")
    public int totalCompra;

    public DatosCompra(String nombreCliente, String fecha, int totalCompra) {
        this.nombreCliente = nombreCliente;
        this.fecha = fecha;
        this.totalCompra = totalCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(int totalCompra) {
        this.totalCompra = totalCompra;
    }
}
