package room.repositorio;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
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
    public Maybe<Producto> darElemento(Object id) {
        Log.i("RepositorioProducto", Thread.currentThread().getName());
        return darInstanciaDB()
                .darDaoProducto()
                .darProducto((Long)id);
    }

    @Override
    public Completable agregarElemento(Producto elemento) {
        return Completable.fromAction(()-> darInstanciaDB().darDaoProducto().agregarProducto(elemento));
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
        return Completable.fromAction(() -> {
            Log.i("RepositorioProducto", "actualizarProductos " + Thread.currentThread().getName());
            darInstanciaDB().darDaoProducto().actualizarProductos(productos);
        });
    }
}
