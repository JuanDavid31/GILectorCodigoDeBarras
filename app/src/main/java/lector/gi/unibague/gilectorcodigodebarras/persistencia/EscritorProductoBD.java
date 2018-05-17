package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 14/05/2018.
 */

public class EscritorProductoBD extends AsyncTaskLoader<Void> {


    private AsistenteLectorCodigoDeBarras asistente;
    private Producto producto;

    public EscritorProductoBD(Context context, Producto p) {
        super(context);
        this.producto = p;
        asistente = new AsistenteLectorCodigoDeBarras(context);
    }

    @Override
    public Void loadInBackground() {
        agregarProducto();
        return null;
    }

    private void agregarProducto(){
        SQLiteDatabase db = asistente.getWritableDatabase();
        ContentValues cv = darContetValuesDelProductos();
        db.insert(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA, null, cv);
    }

    private ContentValues darContetValuesDelProductos() {
        ContentValues valores = new ContentValues();
        valores.put(ContratoLectorCodigoDeBarras.Producto._ID, producto.getCodigo());
        valores.put(ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE, producto.getNombre());
        valores.put(ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD, producto.getCantidadEnStock());
        valores.put(ContratoLectorCodigoDeBarras.Producto.COLUMNA_PRECIO_UNITARIO, producto.getPrecio());
        return valores;
    }

}
