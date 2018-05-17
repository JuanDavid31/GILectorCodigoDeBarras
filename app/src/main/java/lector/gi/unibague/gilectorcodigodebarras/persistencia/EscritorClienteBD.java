package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 15/05/2018.
 */

class EscritorClienteBD extends AsyncTaskLoader<Void> {

    AsistenteLectorCodigoDeBarras asistente;

    public EscritorClienteBD(Context context) {
        super(context);
        asistente = new AsistenteLectorCodigoDeBarras(context);
    }

    @Override
    public Void loadInBackground() {
        agregarClienteDummy();
        return null;
    }

    private void agregarClienteDummy(){
        SQLiteDatabase db = asistente.getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(ContratoLectorCodigoDeBarras.Cliente._ID, 1110582700);
            cv.put(ContratoLectorCodigoDeBarras.Cliente.COLUMNA_NOMBRE, "Juan");
            db.insert(ContratoLectorCodigoDeBarras.Cliente.NOMBRE_TABLA, null, cv);
        }catch (SQLiteConstraintException e){
            Log.i("EscritorClienteBD", "Estoy agregando el mismo cliente otra vez");
        }

    }

}
