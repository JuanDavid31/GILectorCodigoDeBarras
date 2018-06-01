package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import room.entidades.CompraProducto;

@Dao
public abstract class DaoCompraProducto {

    @Insert
    public abstract void agregarCompraProductos(CompraProducto... compraProductos);
}
