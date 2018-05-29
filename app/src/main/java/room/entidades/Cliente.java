package room.entidades;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "GI_CLIENTE")
public class Cliente {

    @PrimaryKey
    @ColumnInfo(name = "c_cedula")
    private int cedula;

    @ColumnInfo(name = "d_nombre")
    private String nombre;

    public Cliente(int cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
