package lector.gi.unibague.gilectorcodigodebarras;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.text.format.DateFormat;

import org.apache.tools.ant.taskdefs.condition.And;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.Calendar;


import java.util.concurrent.TimeUnit;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import room.DatabaseApp;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.Producto;

@RunWith(RobolectricTestRunner.class)
public class TestTest {



    private DatabaseApp mDatabase;
//    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                DatabaseApp.class)
                // allowing main thread queries, just for testing
                .build();

        Completable.fromAction(() ->{
            mDatabase.darDaoProducto().agregarProducto(new Producto(Long.parseLong("1"), "Trident", 9, 200));
        }).doOnError(err -> System.out.println("Error - AgregarProducto " +  err.getMessage()))
                .subscribeOn(Schedulers.io())
                .subscribe();

    }

//    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }



//    @Test
    public void otroTest(){
        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(), CompraActivity.class);
        Producto p = new Producto(Long.parseLong("1"), "Trident", 9, 200);
        intent.putExtra(CompraActivity.PRODUCTO_A_VENDER, p);
        CompraActivity compraActivity = Robolectric.buildActivity(CompraActivity.class, intent).setup().get();
        compraActivity.comprar(null);

    }

    @Test
    public void hagoUnTest() throws InterruptedException {
        //Agrego cliente, agrego compra, consulto compra, agrego compraProductos | actualizoProductos

        Completable c1 = Completable.fromAction(() ->{
            System.out.println("Agrego un cliente" + Thread.currentThread().getName());
        }).doOnError(err -> System.out.println("Error :"+ Thread.currentThread().getName() + err.getMessage()));

        Completable c2 = Completable.fromAction(() ->{
            System.out.println("Agrego una compra " + Thread.currentThread().getName());
        }).doOnError(err -> System.out.println(Thread.currentThread().getName() + err.getMessage()));

        Completable c3 =Flowable.fromArray(1)
                .flatMapCompletable(numero ->{
                    return Completable.fromAction(() ->{
                        System.out.println("Agrego compra productos con " + numero + Thread.currentThread().getName());
                    }).doOnError(err -> System.out.println(Thread.currentThread().getName() + err.getMessage()));
                });

        Completable c4 = Completable.fromAction(() ->{
            System.out.println("Actualizo productos" + Thread.currentThread().getName());
        }).doOnError(err -> System.out.println(Thread.currentThread().getName() + err.getMessage()));


        c1.subscribeOn(Schedulers.io())
                .andThen(c2.andThen(c3))
                .andThen(c4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> System.out.println("Termine " + Thread.currentThread().getName()));



    }

    private Completable insertarClienteDummy(){
        return Completable.fromAction(() -> {
            System.out.println("Agrego cliente " + Thread.currentThread().getName());
        });
    }

    private Completable insertarCompra(){

        String fecha = (String) DateFormat.format("yyyy-MM-dd hh:mm:ss a", Calendar.getInstance().getTime());

        Completable p = Completable
                .fromAction(() -> {
                    System.out.println("Agrego compra " + Thread.currentThread().getName());
                });

        Completable c = Flowable.fromArray("Compra")
                .flatMapCompletable(compraProductos -> {
                    return Completable.fromAction(() -> {
                        System.out.println("Agrego compraProductos con codigo compra" + Thread.currentThread().getName());
                    });
                }).subscribeOn(Schedulers.trampoline());
        return p.andThen(c);

    }

//    private ArrayList crearCompraProductosPorCodigoCompra(int codigoCompra){
//        ArrayList<CompraProducto> compraProductos = null;
//        compraProductos = new ArrayList<CompraProducto>();
//        for (Producto p: productos) {
//            compraProductos.add(new CompraProducto(0, codigoCompra, p.getCodigo(), p.getCantidad()));
//        }
//        return compraProductos;
//    }

//    private Completable agregarCompraProductos(ArrayList<CompraProducto> compraProductos){
//        RepositorioCompraProducto repo = new RepositorioCompraProducto(getApplicationContext());
//        return repo.agregarCompraProductos(compraProductos.toArray(new CompraProducto[compraProductos.size()]));
//    }
//
    private Completable actualizarProductos(){
        return Completable
                .fromAction(() -> {
                    System.out.println("Actualizo compraProductos " + Thread.currentThread().getName());
                });
    }
}
