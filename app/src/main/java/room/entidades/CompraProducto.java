package room.entidades;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "GI_COMPRA_PRODUCTO",
        foreignKeys = {@ForeignKey(entity = Compra.class,
                                    parentColumns = "c_codigo",
                                    childColumns = "c_codigo_compra"),
                        @ForeignKey(entity = Producto.class,
                                    parentColumns = "c_codigo",
                                    childColumns = "c_codigo_producto")})
public class CompraProducto {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "c_codigo")
    private int codigo;

    @ColumnInfo(name = "c_codigo_compra")
    private int codigoCompra;

    @ColumnInfo(name = "c_codigo_producto")
    private Long codigoProducto;

    @ColumnInfo(name = "n_cantidad_vendida")
    private int cantidadVendida;

    public CompraProducto(int codigo, int codigoCompra, Long codigoProducto, int cantidadVendida) {
        this.codigo = codigo;
        this.codigoCompra = codigoCompra;
        this.codigoProducto = codigoProducto;
        this.cantidadVendida = cantidadVendida;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(int codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public Long getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(Long codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }
}
