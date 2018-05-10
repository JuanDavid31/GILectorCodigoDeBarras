package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 9/05/2018.
 */

public class ConsultorComprasBD extends AsyncTaskLoader<Cursor> {

    private static AsistenteLectorCodigoDeBarras asistente;
    private Cursor cursor;

    public ConsultorComprasBD(Context context) {
        super(context);
        asistente = new AsistenteLectorCodigoDeBarras(context);
    }

    @Override
    protected void onStartLoading() { System.out.println("Estoy en onStartLoading()");
        if(cursor != null)deliverResult(cursor);
        else forceLoad();
    }

    @Override
    public Cursor loadInBackground() { System.out.println("Estoy en loadInBackground()");
        return darCompras();
    }

    @Override
    public void deliverResult(Cursor cursor) { System.out.println("Estoy en deliverResult()");
        this.cursor = cursor;
        super.deliverResult(cursor);
    }

    private Cursor darCompras(){
        SQLiteDatabase db = asistente.getReadableDatabase();
        cursor = db.query(ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA,
                null,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
}
