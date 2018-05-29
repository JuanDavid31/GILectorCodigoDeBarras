package room;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import room.dao.DaoCliente;
import room.dao.DaoCompra;
import room.dao.DaoCompraProducto;
import room.dao.DaoProducto;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.CompraProducto;
import room.entidades.Producto;

@Database(entities = {Cliente.class,
                        Compra.class,
                        CompraProducto.class,
                        Producto.class},
            version = 2)
public abstract class DatabaseApp extends RoomDatabase{
    public abstract DaoCliente darDaoCliente();
    public abstract DaoCompra darDaoCompra();
    public abstract DaoCompraProducto darDaoCompraProducto();
    public abstract DaoProducto darDaoProducto();
}
