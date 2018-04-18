package lector.gi.unibague.gilectorcodigodebarras.persistencia;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Personal on 02/04/2018.
 */

public class ConsultorBD extends AsyncTaskLoader<Cursor> {

    private static AsistenteLectorCodigoDeBarras asistente;
    private Bundle paquete;
    public final static String X = "datos";
    private int valorConsulta;

    public ConsultorBD(Context context, Bundle paquete) {
        super(context);
        asistente = new AsistenteLectorCodigoDeBarras(context);
        this.paquete = paquete;
    }

    @Override
    protected void onStartLoading() { System.out.println("Estoy en onStartLoading()");
        if(paquete == null){
            valorConsulta = 0;
        }else{
            valorConsulta = paquete.getInt(X);
        }
    }

    @Override
    public Cursor loadInBackground() { System.out.println("Estoy en loadInBackground()");
        if(valorConsulta == 0) return darProductos();
        return darProducto(valorConsulta);
    }

    @Override
    public void deliverResult(Cursor data) { System.out.println("Estoy en deliverResult()");
        super.deliverResult(data);
    }

    private Cursor darProductos(){
        SQLiteDatabase db = asistente.getReadableDatabase();
        return db.query(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    private Cursor darProducto(int id){
        SQLiteDatabase db = asistente.getReadableDatabase();
        return db.query(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA,
                null,
                ContratoLectorCodigoDeBarras.Producto._ID + " = ?",
                new String[]{Integer.toString(id)},
                null,
                null,
                null);
    }
}
