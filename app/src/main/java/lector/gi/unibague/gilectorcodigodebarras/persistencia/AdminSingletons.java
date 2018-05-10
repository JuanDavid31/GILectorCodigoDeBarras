package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.Context;

/**
 * Created by Juan David on 4/05/2018.
 */

public class AdminSingletons {

    private static CargadorConsultorProductosBD consultorProductos = null;
    private static CargadorConsultorComprasBD consultorCompras = null;

    public static CargadorConsultorProductosBD darInstanciaConsultorProductos(IPostLoaderConsulta ipl, Context context){
        if(consultorProductos == null){
            consultorProductos = new CargadorConsultorProductosBD(ipl, context);
        }
        consultorProductos.cambiarIPl(ipl);
        return consultorProductos;
    }

    public static CargadorConsultorComprasBD darInstanciaConsultorCompras(IPostLoaderConsulta ipl, Context context){
        if(consultorCompras == null){
            consultorCompras = new CargadorConsultorComprasBD(ipl, context);
        }
        consultorCompras.cambiarIPl(ipl);
        return consultorCompras;
    }


}
