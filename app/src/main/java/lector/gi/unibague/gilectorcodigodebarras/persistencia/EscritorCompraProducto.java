package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import lector.gi.unibague.gilectorcodigodebarras.CompraActivity;
import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

/**
 * Created by Juan David on 15/05/2018.
 */

class EscritorCompraProducto extends AsyncTaskLoader<Void> {

    private AsistenteLectorCodigoDeBarras asistente;
    private Bundle paquete;

    public EscritorCompraProducto(Context context, Bundle bundle) {
        super(context);
        asistente = new AsistenteLectorCodigoDeBarras(context);
        this.paquete = bundle;
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        agregarCompraProducto();
        return null;
    }

    public void agregarCompraProducto(){

        SQLiteDatabase db = asistente.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_PRODUCTO, paquete.getLong(CompraActivity.CODIGO_PRODUCTO));
        cv.put(ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_COMPRA, paquete.getInt(CompraActivity.CODIGO_COMPRA));
        cv.put(ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CANTIDAD_VENDIDA, paquete.getInt(CompraActivity.CANTIDAD_VENDIDA));


        db.insert(ContratoLectorCodigoDeBarras.CompraProducto.NOMBRE_TABLA, null, cv);

    }
}
