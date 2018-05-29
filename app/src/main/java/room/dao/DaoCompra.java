package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import room.entidades.Compra;
import room.entidades.DatosCompra;

@Dao
public interface DaoCompra {

    @Insert
    void agregarCompra(Compra compra);

    @Query("SELECT * FROM GI_COMPRA WHERE f_fecha = :fecha")
    Flowable<Compra> darCompraPorFecha(String fecha);

    @Query("SELECT d_nombre, f_fecha, sum(GI_COMPRA_PRODUCTO.n_cantidad_vendida * GI_PRODUCTO.n_precio) as n_total_compra " +
            "FROM GI_CLIENTE, GI_COMPRA, GI_COMPRA_PRODUCTO, GI_PRODUCTO " +
            "WHERE GI_CLIENTE.c_cedula = 1110582700 and " +
            "GI_CLIENTE.c_cedula = GI_COMPRA.c_cedula and " +
            "GI_COMPRA.c_codigo = GI_COMPRA_PRODUCTO.c_codigo_compra and " +
            "GI_COMPRA_PRODUCTO.c_codigo_producto = GI_PRODUCTO.c_codigo " +
            "group by f_fecha")
    Flowable<List<DatosCompra>> darDatosCompra();

    /*String sql = "select GI_CLIENTE._id, GI_CLIENTE.d_nombre, f_fecha, GI_COMPRA._id,\n" +
            "sum(GI_COMPRA_PRODUCTO.n_cantidad_vendida * GI_PRODUCTO.n_precio_unitario) \n" +
            "from GI_CLIENTE, GI_COMPRA, GI_COMPRA_PRODUCTO, GI_PRODUCTO where GI_CLIENTE._id = GI_COMPRA.c_cedula and \n" +
            "GI_COMPRA._id = GI_COMPRA_PRODUCTO.c_codigo_compra and GI_COMPRA_PRODUCTO.c_codigo_producto = GI_PRODUCTO._id\n" +
            "group by GI_COMPRA._id";*/
}
