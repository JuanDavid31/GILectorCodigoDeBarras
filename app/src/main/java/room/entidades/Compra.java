package room.entidades;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "GI_COMPRA",
        foreignKeys = @ForeignKey(entity = Cliente.class,
                                    parentColumns = "c_cedula",
                                    childColumns = "c_cedula",
                                    onDelete = ForeignKey.CASCADE))
public class Compra {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "c_codigo")
    private int codigo;

    @ColumnInfo(name = "c_cedula")
    private int cedulaCliente;

    @ColumnInfo(name = "f_fecha")
    private String fecha;

    public Compra(int codigo, int cedulaCliente, String fecha) {
        this.codigo = codigo;
        this.cedulaCliente = cedulaCliente;
        this.fecha = fecha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
