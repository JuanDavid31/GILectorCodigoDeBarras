package room.repositorio;

import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import room.entidades.Compra;
import room.entidades.DatosCompra;

public class RepositorioCompra extends Repositorio<Compra>{


    public RepositorioCompra(Context context) {
        super(context);
    }

    @Override
    public Flowable<List<Compra>> darElementos() {
        return null;
    }

    @Override
    public Flowable<Compra> darElemento(Object id) {
        return null;
    }

    @Override
    public Completable agregarElemento(Compra elemento) {
        Log.i("RespositorioCompra", "Agregar elemento " + elemento.toString());
        return Completable
                .fromAction(() -> darInstanciaDB().darDaoCompra().agregarCompra(elemento));
    }

    @Override
    public Completable editarElemento(Compra elemento) {

        return null;
    }

    @Override
    public Completable eliminarElemento(int id) {

        return null;
    }

    public Flowable<List<DatosCompra>> darDatosCompras(){
        return darInstanciaDB()
                .darDaoCompra()
                .darDatosCompra();
    }

    public Flowable<Compra> darCompraPorFecha(String fecha){
        Log.i("RespositorioCompra", "darCompraPorFecha " + fecha);
        return darInstanciaDB()
                .darDaoCompra()
                .darCompraPorFecha(fecha);
    }
}
