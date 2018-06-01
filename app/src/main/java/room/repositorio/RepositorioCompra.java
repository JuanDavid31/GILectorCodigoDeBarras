package room.repositorio;

import android.content.Context;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
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
    public Maybe<Compra> darElemento(Object id) {
        return null;
    }

    @Override
    public Completable agregarElemento(Compra elemento) {
        return Completable
                .fromAction(() -> {
                    Log.i("RepositorioCompra", "Agregar elemento " + Thread.currentThread().getName());
                    darInstanciaDB().darDaoCompra().agregarCompra(elemento);
                });
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

    public Maybe<Compra> darCompraPorFecha(String fecha){
        return darInstanciaDB()
                .darDaoCompra()
                .darCompraPorFecha(fecha);
        //TODO: doOnCOmplete  | doOnSubsribe?
    }

//    Flowable.range(1, 10).subscribe(new Subscriber<Integer>() {
//        @Override
//        public void onSubscribe(Subscription s) {
//            s.request(Long.MAX_VALUE);
//        }
//
//        @Override
//        public void onNext(Integer t) {
//            System.out.println(t);
//        }
//
//        @Override
//        public void onError(Throwable t) {
//            t.printStackTrace();
//        }
//
//        @Override
//        public void onComplete() {
//            System.out.println("Done");
//        }
//    });
}
