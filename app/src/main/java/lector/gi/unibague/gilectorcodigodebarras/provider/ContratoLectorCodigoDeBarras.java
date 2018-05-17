package lector.gi.unibague.gilectorcodigodebarras.provider;

import android.provider.BaseColumns;

/**
 * Created by Personal on 24/03/2018.
 */

public class ContratoLectorCodigoDeBarras {

    private ContratoLectorCodigoDeBarras(){}

    public static class Cliente implements BaseColumns{
        public final static String NOMBRE_TABLA = "GI_CLIENTE";
        public final static String COLUMNA_NOMBRE = "d_nombre";
    }

    public final class Compra implements BaseColumns{
        public final static String NOMBRE_TABLA = "GI_COMPRA";
        public final static String COLUMNA_CEDULA = "c_cedula";
        public final static String COLUMNA_FECHA = "f_fecha";
    }

    public final static class CompraProducto implements BaseColumns{
        public final static String NOMBRE_TABLA = "GI_COMPRA_PRODUCTO";
        public final static String COLUMNA_CODIGO_COMPRA = "c_codigo_compra";
        public final static String COLUMNA_CODIGO_PRODUCTO = "c_codigo_producto";
        public final static String COLUMNA_CANTIDAD_VENDIDA = "n_cantidad_vendida";
    }

    public static class Producto implements BaseColumns{
        public final static String NOMBRE_TABLA = "GI_PRODUCTO";
        public final static String COLUMNA_NOMBRE = "d_nombre";
        public final static String COLUMNA_CANTIDAD = "n_cantidad";
        public final static String COLUMNA_PRECIO_UNITARIO = "n_precio_unitario";

    }
}
