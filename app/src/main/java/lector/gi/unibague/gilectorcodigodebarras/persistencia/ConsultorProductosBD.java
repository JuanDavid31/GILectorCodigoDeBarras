package lector.gi.unibague.gilectorcodigodebarras.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import lector.gi.unibague.gilectorcodigodebarras.EscaneoActivity;
import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Personal on 02/04/2018.
 */

public class ConsultorProductosBD extends AsyncTaskLoader<Cursor> {

    private static AsistenteLectorCodigoDeBarras asistente;
    private Bundle paquete;
    public final static String X = "datos"; //TODO: Cambiarle el nombre a esto
    private Cursor cursor;

    public ConsultorProductosBD(Context context, Bundle paquete) {
        super(context);
        System.out.println("Estoy en el constructor");
        asistente = new AsistenteLectorCodigoDeBarras(context);
        this.paquete = paquete;
    }

    @Override
    protected void onStartLoading() { System.out.println("Estoy en onStartLoading()");
        if(cursor != null)deliverResult(cursor);
        else forceLoad();
    }

    @Override
    public Cursor loadInBackground() { System.out.println("Estoy en loadInBackground()");
        int id = darIdProductoDeConsulta();
        if(id == 0) return darProductos();
        return darProducto(id);
    }

    public int darIdProductoDeConsulta(){
        if(paquete == null){
            return 0;
        }else{
            return paquete.getInt(X);
        }
    }

    @Override
    public void deliverResult(Cursor cursor) { System.out.println("Estoy en deliverResult()");
        this.cursor = cursor;
        super.deliverResult(cursor);
    }

    private Cursor darProductos(){
        SQLiteDatabase db = asistente.getReadableDatabase();
        cursor = db.query(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }

    private Cursor darProducto(int id){
        SQLiteDatabase db = asistente.getReadableDatabase();
        cursor = db.query(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA,
                null,
                ContratoLectorCodigoDeBarras.Producto._ID + " = ?",
                new String[]{Integer.toString(id)},
                null,
                null,
                null);
        return cursor;
    }
}
