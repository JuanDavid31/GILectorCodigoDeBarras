package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Transaction;

import java.util.ArrayList;

import io.reactivex.Completable;
import room.DatabaseApp;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.CompraProducto;
import room.entidades.Producto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioCliente;
import room.repositorio.RepositorioCompra;

@Dao
public abstract class DaoCliente {

    @Insert
    public abstract void agregarCliente(Cliente cliente);

    @Transaction
    public void hacerDeTodo(DatabaseApp db, Cliente cliente, Compra compra, ArrayList<Producto> productos){
        agregarCliente(cliente);
        //agrego la compra
        db.darDaoCompra().agregarCompra(compra);
        //Consulto la compra
        db.darDaoCompra().darCompraPorFecha(compra.getFecha())
            .subscribe(compraBuscada ->{
                db.darDaoCompraProducto()
                    .agregarCompraProductos(
                            crearCompraProductosPorCodigoCompra(compraBuscada.getCodigo(), productos)
                    );
                db.darDaoProducto().actualizarProductos(productos.toArray(new Producto[productos.size()]));
            });
        //Con la compra consultado agrego los CompraProductos
        //actualizo los productos

    }

    private CompraProducto[] crearCompraProductosPorCodigoCompra(int codigoCompra,ArrayList<Producto> productos){
        ArrayList<CompraProducto> compraProductos = null;
        compraProductos = new ArrayList<CompraProducto>();
        for (Producto p: productos) {
            compraProductos.add(new CompraProducto(0, codigoCompra, p.getCodigo(), p.getCantidad()));
        }
        return compraProductos.toArray(new CompraProducto[productos.size()]);
    }


}
