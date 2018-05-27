package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import lector.gi.unibague.gilectorcodigodebarras.CompraActivity;
import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
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
        hacerDeTodo();
        return null;
    }

    public void hacerDeTodo(){

        SQLiteDatabase db = asistente.getWritableDatabase();

        //Agrego cliente
        try {
            ContentValues cv = new ContentValues();
            cv.put(ContratoLectorCodigoDeBarras.Cliente._ID, 1110582700);
            cv.put(ContratoLectorCodigoDeBarras.Cliente.COLUMNA_NOMBRE, "Juan");
            db.insert(ContratoLectorCodigoDeBarras.Cliente.NOMBRE_TABLA, null, cv);
        }catch (SQLiteConstraintException e){
            Log.i("EscritorClienteBD", "Estoy agregando el mismo cliente otra vez");
        }

        //agrego Compra

        ContentValues cv = new ContentValues();

        Double v = (Math.random() * 100);
        String s = String.format("%.0f", v);
        int id = Integer.parseInt(s);

        String fecha = (String) DateFormat.format("yyyy-MM-dd hh:mm:ss a", Calendar.getInstance().getTime());

        cv.put(ContratoLectorCodigoDeBarras.Compra._ID, id);
        cv.put(ContratoLectorCodigoDeBarras.Compra.COLUMNA_CEDULA, 1110582700);
        cv.put(ContratoLectorCodigoDeBarras.Compra.COLUMNA_FECHA, fecha);

        db.insert(ContratoLectorCodigoDeBarras.Compra.NOMBRE_TABLA, null, cv);

        //Aquí abajo debe llevar un for

        //Agrego compraProducto
        ArrayList<Producto> productos = (ArrayList<Producto>) paquete.getSerializable(CompraActivity.PRODUCTOS);

        for(Producto p : productos){
            ContentValues cv1 = new ContentValues();
            cv1.put(ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_PRODUCTO, p.getCodigo());
            cv1.put(ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CODIGO_COMPRA, id);
            cv1.put(ContratoLectorCodigoDeBarras.CompraProducto.COLUMNA_CANTIDAD_VENDIDA, p.getCantidadVendida());

            db.insert(ContratoLectorCodigoDeBarras.CompraProducto.NOMBRE_TABLA, null, cv1);

            //Actualizo producto
            String sql = "UPDATE " + ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA +
                    " set " + ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD +
                    " = " + (p.getCantidadEnStock() - p.getCantidadVendida()) +
                    " where " + ContratoLectorCodigoDeBarras.Producto._ID +
                    " = " + p.getCodigo();

            db.execSQL(sql);
        }


    }
}
