package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        if(cursor != null && cursor.getCount() != 0 )deliverResult(cursor);
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

        String sql = "select GI_CLIENTE._id, GI_CLIENTE.d_nombre, f_fecha, GI_COMPRA._id,\n" +
                "sum(GI_COMPRA_PRODUCTO.n_cantidad_vendida * GI_PRODUCTO.n_precio_unitario) \n" +
                "from GI_CLIENTE, GI_COMPRA, GI_COMPRA_PRODUCTO, GI_PRODUCTO where GI_CLIENTE._id = GI_COMPRA.c_cedula and \n" +
                "GI_COMPRA._id = GI_COMPRA_PRODUCTO.c_codigo_compra and GI_COMPRA_PRODUCTO.c_codigo_producto = GI_PRODUCTO._id\n" +
                "group by GI_COMPRA._id";
        Log.i("SQL consulta:", sql);
        cursor = db.rawQuery(sql, null);
        return cursor;
    }
}
