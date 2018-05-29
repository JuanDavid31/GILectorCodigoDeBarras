package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import room.entidades.CompraProducto;

@Dao
public interface DaoCompraProducto {

    @Insert
    void agregarCompraProductos(CompraProducto... compraProductos);
}
