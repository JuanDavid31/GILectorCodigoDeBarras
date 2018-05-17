package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import lector.gi.unibague.gilectorcodigodebarras.CompraActivity;

/**
 * Created by Juan David on 15/05/2018.
 */

class CargadorEscritorCompraBD implements LoaderManager.LoaderCallbacks<Long>{

    private Context context;
    private IPostLoaderEscritura ipe;

    public CargadorEscritorCompraBD(Context context, IPostLoaderEscritura ipe) {
        this.context = context;
        this.ipe = ipe;
    }

    @NonNull
    @Override
    public Loader<Long> onCreateLoader(int id, @Nullable Bundle args) {
        return new EscritorCompraBD(context, args.getInt(CompraActivity.CEDULA_CLIENTE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Long> loader, Long data) {
        ipe.accionPostLoaderEscritura(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Long> loader) {

    }
}
