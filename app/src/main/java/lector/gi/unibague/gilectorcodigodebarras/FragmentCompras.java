package lector.gi.unibague.gilectorcodigodebarras;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderConsulta;

/**
 * Created by Juan David on 9/05/2018.
 */

public class FragmentCompras extends Fragment implements IPostLoaderConsulta{

    private static TextView tvInformacion;
    private static ProgressBar pbBarraProgreso;
    private static RecyclerView rvListaProductos;

    public FragmentCompras() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        realizarConsulta();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_compras, container, false);
        cargarVariablesIniciales(rootView);
        return rootView;
    }

    private void cargarVariablesIniciales(View v){
        tvInformacion = v.findViewById(R.id.tv_informacion_compras);
        pbBarraProgreso = v.findViewById(R.id.pb_indicador_carga_compras);
        rvListaProductos = v.findViewById(R.id.rv_recycler_view_compras);
        rvListaProductos.setHasFixedSize(true);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListaProductos.setAdapter(null);
    }

    public void realizarConsulta(){
        ocultarLista();
        getLoaderManager().initLoader(MainActivity.LOADER_CONSULTOR_COMPRAS_DB, null, AdminSingletons.darInstanciaConsultorCompras(this, getActivity()));
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = getIntent();
//
//        if(intent == null) return; //TODO: intent sera null al iniciar la app?
//        if(intent.getBooleanExtra(MainActivity.REFRESCAR_DATOS, false)) realizarConsulta();
//    }

    public static void ocultarLista(){
        pbBarraProgreso.setVisibility(View.VISIBLE);
        tvInformacion.setVisibility(View.INVISIBLE);
        rvListaProductos.setVisibility(View.INVISIBLE);
    }

    public static void mostrarLista(){
        pbBarraProgreso.setVisibility(View.INVISIBLE);
        tvInformacion.setVisibility(View.INVISIBLE);
        rvListaProductos.setVisibility(View.VISIBLE);
    }

    public static void mostrarMensaje(){
        pbBarraProgreso.setVisibility(View.INVISIBLE);
        tvInformacion.setVisibility(View.VISIBLE);
        rvListaProductos.setVisibility(View.INVISIBLE);
    }

    @Override
    public void accionPostLoaderConsulta(Cursor cursor) {
        rvListaProductos.setAdapter(new AdaptadorProductoEnStock(cursor));
        if(cursor.getCount() == 0){
            mostrarMensaje();
        }else{
            mostrarLista();
        }
    }

}
