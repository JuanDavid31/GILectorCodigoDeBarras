package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import room.entidades.Producto;

@Dao
public abstract class DaoProducto {

    @Query("SELECT * FROM GI_PRODUCTO")
    public abstract Flowable<List<Producto>> darProductos();

    @Query("SELECT * FROM GI_PRODUCTO WHERE c_codigo = :codigoActual")
    public abstract Maybe<Producto> darProducto(Long codigoActual);

    @Insert
    public abstract void agregarProducto(Producto producto);

    @Update
    public abstract void actualizarProducto(Producto producto);

    @Update
    public abstract void actualizarProductos(Producto... productos);
}
