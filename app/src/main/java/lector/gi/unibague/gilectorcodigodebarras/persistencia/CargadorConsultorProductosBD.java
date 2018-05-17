package lector.gi.unibague.gilectorcodigodebarras.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Created by Juan David on 4/05/2018.
 */

public class CargadorConsultorProductosBD implements LoaderManager.LoaderCallbacks<Cursor> {

    private IPostLoaderConsulta ipl;
    private Context context;

    public CargadorConsultorProductosBD(IPostLoaderConsulta ipl, Context context){
        this.ipl = ipl;
        this.context = context;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new ConsultorProductosBD(context, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        ipl.accionPostLoaderConsulta(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public void cambiarIPl(IPostLoaderConsulta ipl){
        this.ipl = ipl;
    }


}
