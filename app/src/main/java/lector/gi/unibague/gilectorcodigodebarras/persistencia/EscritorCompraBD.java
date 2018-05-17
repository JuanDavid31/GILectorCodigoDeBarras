package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.text.format.DateFormat;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 15/05/2018.
 */

class EscritorCompraBD extends AsyncTaskLoader<Long> {

    AsistenteLectorCodigoDeBarras asistenteLectorCodigoDeBarras;
    private int cedula;

    public EscritorCompraBD(Context context, int cedula) {
        super(context);
        this.cedula = cedula;
        asistenteLectorCodigoDeBarras = new AsistenteLectorCodigoDeBarras(context);
    }

    @Nullable
    @Override
    public Long loadInBackground() {
        return agregarCompra();
    }

    public Long agregarCompra(){
        SQLiteDatabase db = asistenteLectorCodigoDeBarras.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Double v = (Math.random() * 100);
        String s = String.format("%.0f", v);
        int id = Integer.parseInt(s);

        String fecha = (String) DateFormat.format("yyyy-MM-dd hh:mm:ss a", Calendar.getInstance().getTime());

        cv.put(ContratoLectorCodigoDeBarras.Compra._ID, id);
        cv.put(ContratoLectorCodigoDeBarras.Compra.COLUMNA_CEDULA, cedula);
        cv.put(ContratoLectorCodigoDeBarras.Compra.COLUMNA_FECHA, fecha);

        db.insert(ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA, null, cv);

        return Long.parseLong(id + "");
    }
}
