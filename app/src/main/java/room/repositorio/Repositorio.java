package room.repositorio;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import room.DatabaseApp;

public abstract class Repositorio <T>{

    Context context;
    private static DatabaseApp db = null;

    public Repositorio(Context context){
        this.context = context;
    }

    DatabaseApp darInstanciaDB(){
        if(db == null){
            db = Room.databaseBuilder(context,
                            DatabaseApp.class,
                            "LectorCodigoDeBarras.db")
                            .build();
        }
        return db;
    }

    public abstract Flowable<List<T>> darElementos();
    public abstract Flowable<T> darElemento(Object id);
    public abstract Completable agregarElemento(T elemento);
    public abstract Completable editarElemento(T elemento);
    public abstract Completable eliminarElemento(int id);
}
