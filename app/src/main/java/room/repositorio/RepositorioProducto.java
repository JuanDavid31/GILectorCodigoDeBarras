package room.repositorio;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import room.entidades.Producto;

public class RepositorioProducto extends Repositorio<Producto> {

    public RepositorioProducto(Context context) {
        super(context);
    }

    @Override
    public Flowable<List<Producto>> darElementos() {
        return darInstanciaDB()
                .darDaoProducto()
                .darProductos();
    }

    @Override
    public Flowable<Producto> darElemento(Object id) {
        return darInstanciaDB()
                .darDaoProducto()
                .darProducto((Long)id);
    }

    @Override
    public Completable agregarElemento(Producto elemento) {

        return null;
    }

    @Override
    public Completable editarElemento(Producto elemento) {

        return null;
    }

    @Override
    public Completable eliminarElemento(int id) {

        return null;
    }

    public Completable actualizarProductos(Producto... productos){
        Log.i("RespositorioProducto", "sctualizarProductos " + productos.toString());
        return Completable
                .fromAction(() -> darInstanciaDB()
                                    .darDaoProducto()
                                    .actualizarProductos(productos));
    }
}
