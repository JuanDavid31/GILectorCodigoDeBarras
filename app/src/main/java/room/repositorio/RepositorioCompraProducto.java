package room.repositorio;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
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
    public Flowable<CompraProducto> darElemento(Object id) {
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
        Log.i("RespoCompraProducto", "AgregarCompraProductos " + compraProductos.toString());
        return Completable
                .fromAction(() -> darInstanciaDB()
                                    .darDaoCompraProducto()
                                    .agregarCompraProductos(compraProductos));
    }
}
