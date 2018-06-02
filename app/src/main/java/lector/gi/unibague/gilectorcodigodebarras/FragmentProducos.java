package lector.gi.unibague.gilectorcodigodebarras;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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

import java.util.List;

import room.repositorio.Repositorio;
import room.repositorio.RepositorioProducto;
import viewModel.FabricaViewModel;
import viewModel.ProductosViewModel;

public class FragmentProducos extends Fragment{

    private static TextView tvInformacion;
    private static ProgressBar pbBarraProgreso;
    private static RecyclerView rvListaProductos;
    private ProductosViewModel vmProductos;

    public FragmentProducos() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelProvider.Factory fab = new FabricaViewModel.FabricaProductosViewModel(getActivity().getApplicationContext());
        vmProductos = ViewModelProviders.of(this, fab).get(ProductosViewModel.class);
        realizarConsulta();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_productos, container, false);
        cargarVariablesIniciales(rootView);
        return rootView;
    }

    private void cargarVariablesIniciales(View v){
        tvInformacion = v.findViewById(R.id.tv_informacion_productos);
        pbBarraProgreso = v.findViewById(R.id.pb_indicador_carga);
        rvListaProductos = v.findViewById(R.id.rv_recycler_view_stock);
        rvListaProductos.setHasFixedSize(true);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvListaProductos.setAdapter(null);
    }

    public void realizarConsulta(){
        ocultarLista();
        Repositorio repo = new RepositorioProducto(getActivity().getApplication());
        vmProductos
            .darProductos()
            .subscribe( datos -> actualizarProductos((List) datos));
    }

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

    public void actualizarProductos(List productos){
        rvListaProductos.setAdapter(new AdaptadorProductoEnStock(productos));
        if(productos.size() == 0){
            mostrarMensaje();
        }else{
            mostrarLista();
        }
    }



}
