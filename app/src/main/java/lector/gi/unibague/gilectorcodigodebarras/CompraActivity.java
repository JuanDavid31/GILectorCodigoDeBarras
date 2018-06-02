package lector.gi.unibague.gilectorcodigodebarras;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.Completable;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import room.entidades.Producto;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.CompraProducto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioCliente;
import room.repositorio.RepositorioCompra;
import room.repositorio.RepositorioCompraProducto;
import room.repositorio.RepositorioProducto;
import viewModel.CompraViewModel;
import viewModel.FabricaViewModel;

public class CompraActivity extends AppCompatActivity implements DialogoCantidadAVender.DialogListener{

    public final static String PRODUCTO_A_VENDER = "Producto a vender";
    public final static String PRODUCTOS = "Productos";
    private RecyclerView rvListaProductos;
    private CompraViewModel vmCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
        FabricaViewModel.FabricaCompraViewModel fab = new FabricaViewModel.FabricaCompraViewModel(getApplicationContext());
        vmCompra = ViewModelProviders.of(this, fab).get(CompraViewModel.class);
    }

    @Override
    protected void onStart() {
        setTitle("Compra");
        super.onStart();
        vmCompra.cargarProductos();
        adicionarNuevoProducto();
        cargarRecyclerView();
    }

    public void cargarRecyclerView(){
        rvListaProductos = findViewById(R.id.rv_recycler_view_compra);
        rvListaProductos.setHasFixedSize(true);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(this));
        rvListaProductos.setAdapter(new AdaptadorProductoEnCompra(vmCompra.productos, this));
    }

    public void eliminarProducto(int posicion){
        vmCompra.productos.remove(posicion);
        rvListaProductos.getAdapter().notifyItemRemoved(posicion);
        rvListaProductos.getAdapter().notifyItemRangeChanged(posicion, vmCompra.productos.size());
    }

    public void adicionarNuevoProducto(){
        Intent i = getIntent();
        Producto producto = (Producto) i.getSerializableExtra(PRODUCTO_A_VENDER);
        if(!vmCompra.adicionarProducto(producto)){
            Toast.makeText(this, "Ya esta agregado el producto", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarProducto(View v){
        Intent i = new Intent(this, EscaneoActivity.class);
        startActivity(i);
    }

    public void abrirDialogo(int posicion){
        DialogoCantidadAVender dialogo = new DialogoCantidadAVender();
        dialogo.setPosicion(posicion);
        dialogo.show(getSupportFragmentManager(), "Dialogo");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("CompraActivity","Estoy guardando los productos");
        vmCompra.guardarProductos();
    }

    public void comprar(View v){
        if(vmCompra.productos == null || vmCompra.productos.size() == 0){
            Toast.makeText(this, "Añade un producto", Toast.LENGTH_SHORT).show();
            return;
        }
        agregarCompraProductoYActualizarProductos();
    }

    public void agregarCompraProductoYActualizarProductos(){
        vmCompra.hacerDeTodo()
            .subscribe(() -> irAFacturaActivity());
    }

    public void irAFacturaActivity(){
        Intent i = new Intent(this, FacturaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCTOS, vmCompra.productos);
        i.putExtras(bundle);
        vmCompra.limpiarCache();
        startActivity(i);
    }

    @Override
    public void accion(int cantidad, int posicion) {
        Producto producto = vmCompra.productos.get(posicion);
        try {
            producto.setCantidad(cantidad);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No se puede vender esta cantidad", Toast.LENGTH_SHORT).show();
        }
        rvListaProductos.getAdapter().notifyItemChanged(posicion);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(this, MainActivity.class);
        startActivity(startMain);
    }
}
