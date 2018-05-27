package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

/**
 * Created by Juan David on 15/05/2018.
 */

class CargadorEscritorClienteBD implements LoaderManager.LoaderCallbacks<Void> {

    private Context context;

    public CargadorEscritorClienteBD(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public Loader<Void> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i("DorEscritorClienteDB", "OnCreaLoader EscritorClienteDB");
        return new EscritorClienteBD(context);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Void> loader, Void data) {
        Log.i("DorEscritorClienteDB", "OnLoadFinish EscritorClienteDB");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Void> loader) {

    }
}
