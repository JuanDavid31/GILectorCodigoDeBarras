package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;

/**
 * Created by Juan David on 4/05/2018.
 */

public class AdminSingletons {

    private static CargadorConsultorProductosBD consultorProductos = null;
    private static CargadorEscritorProductosBD escritorProductos = null;
    private static CargadorEscritorClienteBD escritorCliente = null;
    private static CargadorConsultorComprasBD consultorCompras = null;
    private static CargadorEscritorCompraBD escritorCompra = null;
    private static CargadorEscritorCompraProductoBD escritorCompraProducto = null;

    public static LoaderManager.LoaderCallbacks<Cursor> darInstanciaConsultorProductos(IPostLoaderConsulta ipl, Context context){
        if(consultorProductos == null){
            consultorProductos = new CargadorConsultorProductosBD(ipl, context);
        }
        consultorProductos.cambiarIPl(ipl);
        return consultorProductos;
    }

    public static LoaderManager.LoaderCallbacks<Cursor> darInstanciaConsultorCompras(IPostLoaderConsulta ipl, Context context){
        if(consultorCompras == null){
            consultorCompras = new CargadorConsultorComprasBD(ipl, context);
        }
        consultorCompras.cambiarIPl(ipl);
        return consultorCompras;
    }

    public static LoaderManager.LoaderCallbacks<Void> darInstanciaEscritorProducto(IPostLoaderEscritura ipe, Context context){
        if(escritorProductos == null){
            escritorProductos = new CargadorEscritorProductosBD(ipe, context);
        }
        escritorProductos.cambiarIPE(ipe);
        return escritorProductos;
    }

    public static LoaderManager.LoaderCallbacks<Void> darInstanciaEscritorCliente(Context context) {
        if(escritorCliente == null){
            escritorCliente = new CargadorEscritorClienteBD(context);
        }
        return escritorCliente;
    }

    public static LoaderManager.LoaderCallbacks<Long> darInstanciaEscritorCompra(Context context, IPostLoaderEscritura ipe) {
        if(escritorCompra == null){
            escritorCompra = new CargadorEscritorCompraBD(context, ipe);
        }
        return escritorCompra;
    }

    public static LoaderManager.LoaderCallbacks<Void> darInstanciaEscritorCompraProducto(Context context) {
        if(escritorCompraProducto == null){
            escritorCompraProducto = new CargadorEscritorCompraProductoBD(context);
        }
        return escritorCompraProducto;
    }
}
