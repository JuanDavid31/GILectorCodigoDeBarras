package lector.gi.unibague.gilectorcodigodebarras.persistencia;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import lector.gi.unibague.gilectorcodigodebarras.AdicionProductoActivity;
import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;

/**
 * Created by Juan David on 14/05/2018.
 */

public class CargadorEscritorProductosBD implements LoaderManager.LoaderCallbacks<Void>{

    private IPostLoaderEscritura ipe;
    private Context context;

    public CargadorEscritorProductosBD(IPostLoaderEscritura ipl, Context context){
        this.context = context;
        this.ipe = ipl;
    }


    public void cambiarIPE(IPostLoaderEscritura ipl){
        this.ipe = ipl;
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("CargEscritorProductos", "Holi");
        return new EscritorProductoBD(context, (Producto) args.getSerializable(AdicionProductoActivity.PRODUCTO));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {
        ipe.accionPostLoaderEscritura(null);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }
}
