package lector.gi.unibague.gilectorcodigodebarras.provider;

import android.provider.BaseColumns;

/**
 * Created by Personal on 24/03/2018.
 */

public class ContratoLectorCodigoDeBarras {

    private ContratoLectorCodigoDeBarras(){}

    public static class Producto implements BaseColumns{
        public final static String NOMBRE_TABLA = "LB_PRODUCTO";
        public final static String COLUMNA_NOMBRE = "d_nombre";
        public final static String COLUMNA_CANTIDAD = "n_cantidad";
    }
}
