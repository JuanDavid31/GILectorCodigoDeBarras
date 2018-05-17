package lector.gi.unibague.gilectorcodigodebarras.persistencia;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by Juan David on 15/05/2018.
 */

class CargadorEscritorCompraProductoBD implements LoaderManager.LoaderCallbacks<Void>{

    Context context;

    public CargadorEscritorCompraProductoBD(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        return new EscritorCompraProducto(context, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }
}
