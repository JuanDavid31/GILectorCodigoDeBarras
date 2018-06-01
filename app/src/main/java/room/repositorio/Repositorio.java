package room.repositorio;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import room.DatabaseApp;
import room.entidades.Producto;

public abstract class Repositorio <T>{

    Context context;
    private static DatabaseApp db = null;

    public Repositorio(Context context){
        this.context = context;
    }

    public DatabaseApp darInstanciaDB(){
        if(db == null){
            db = Room.databaseBuilder(context,
                            DatabaseApp.class,
                            "LectorCodigoDeBarras.db")
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            Completable.fromAction(() ->{
                                darInstanciaDB()
                                    .darDaoProducto()
                                    .agregarProducto(
                                            new Producto(Long.parseLong("7702133863264"), "Trident", 9, 200)
                                    );
                            }).subscribeOn(Schedulers.io())
                            .subscribe(() -> System.out.println("Agregado el producto"));
                        }
                    })
                    .build();
        }
        return db;
    }

    public abstract Flowable<List<T>> darElementos();
    public abstract Maybe<T> darElemento(Object id);
    public abstract Completable agregarElemento(T elemento);
    public abstract Completable editarElemento(T elemento);
    public abstract Completable eliminarElemento(int id);
}
