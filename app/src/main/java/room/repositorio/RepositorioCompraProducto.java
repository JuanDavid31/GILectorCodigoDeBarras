package room.repositorio;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import room.entidades.CompraProducto;

public class RepositorioCompraProducto extends Repositorio<CompraProducto> {

    public RepositorioCompraProducto(Context context) {
        super(context);
    }

    @Override
    public Flowable<List<CompraProducto>> darElementos() {
        return null;
    }

    @Override
    public Maybe<CompraProducto> darElemento(Object id) {
        return null;
    }

    @Override
    public Completable agregarElemento(CompraProducto elemento) {

        return null;
    }

    @Override
    public Completable editarElemento(CompraProducto elemento) {

        return null;
    }

    @Override
    public Completable eliminarElemento(int id) {

        return null;
    }

    public Completable agregarCompraProductos(CompraProducto... compraProductos){
        return Completable
                .fromAction(() -> {
                    Log.i("RepoCompraProducto", "AgregarCompraProductos " + Thread.currentThread().getName());
                    darInstanciaDB().darDaoCompraProducto().agregarCompraProductos(compraProductos);
                });
    }
}
