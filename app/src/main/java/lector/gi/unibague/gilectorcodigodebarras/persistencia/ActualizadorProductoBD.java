package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.io.Serializable;

import lector.gi.unibague.gilectorcodigodebarras.CompraActivity;
import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;


/**
 * Created by Juan David on 17/05/2018.
 */

class ActualizadorProductoBD extends AsyncTaskLoader<Void> {

    private AsistenteLectorCodigoDeBarras asistente;
    private Bundle paquete;

    public ActualizadorProductoBD(Context context, Bundle bundle) {
        super(context);
        paquete = bundle;
        asistente = new AsistenteLectorCodigoDeBarras(context);
    }

    @Nullable
    @Override
    public Void loadInBackground() {
        actualizarProducto();
        return null;
    }

    public void actualizarProducto(){
        Producto producto = (Producto) paquete.getSerializable(CompraActivity.PRODUCTO_A_ACTUALIZAR);
        SQLiteDatabase db = asistente.getWritableDatabase();
        // UPDATE COMPANY SET ADDRESS = 'Texas', SALARY = 20000.00;
        String sql = "UPDATE " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA +
                " set " + ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD +
                " = " + (producto.getCantidadEnStock() - producto.getCantidadVendida()) +
                " where " + ContratoLectorCodigoDeBarras.Producto._ID +
                " = " + producto.getCodigo();

        Log.i("ActualizadorProducto",sql);
        db.execSQL(sql);
    }
}
