package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import room.entidades.Producto;

@Dao
public interface DaoProducto {

    @Query("SELECT * FROM GI_PRODUCTO")
    Flowable<List<Producto>> darProductos();

    @Query("SELECT * FROM GI_PRODUCTO WHERE c_codigo = :codigoActual")
    Flowable<Producto> darProducto(Long codigoActual);

    @Insert
    void agregarProducto(Producto producto);

    @Update
    void actualizarProducto(Producto producto);

    @Update
    void actualizarProductos(Producto... productos);
}
