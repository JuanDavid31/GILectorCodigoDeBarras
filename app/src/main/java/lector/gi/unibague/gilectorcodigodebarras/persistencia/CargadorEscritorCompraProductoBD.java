package lector.gi.unibague.gilectorcodigodebarras.persistencia;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by Juan David on 15/05/2018.
 */

class CargadorEscritorCompraProductoBD implements LoaderManager.LoaderCallbacks<Void>{

    private Context context;
    private IPostLoaderEscritura ipl;

    public CargadorEscritorCompraProductoBD(Context context){
        this.context = context;
        ipl = (IPostLoaderEscritura)context;
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("DorEscritorCompraProdDB", "OnCreaLoader EscritorCompraProductoDB");
        return new EscritorCompraProducto(context, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {
        Log.i("DorEscritorCompraProdDB", "OnLoadFinish EscritorCompraProductoDB");
        ipl.accionPostLoaderEscritura(null);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }
}
