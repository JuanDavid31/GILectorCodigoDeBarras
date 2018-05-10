package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;

public class CompraActivity extends AppCompatActivity {

    public final static String PRODUCTO_A_VENDER = "Producto a vender";
    private File archivo = new File(getCacheDir(), File.pathSeparator + "productos");
    private ArrayList<Producto> productos;
    private Producto productoActual;

    private RecyclerView rvListaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
    }

    public void cargarRecyclerView(){
        rvListaProductos = findViewById(R.id.rv_recycler_view_compra);
        rvListaProductos.setHasFixedSize(true);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(this));
        rvListaProductos.setAdapter(new AdaptadorProductoEnCompra(productos));
    }

    @Override
    protected void onStart() {
        super.onStart();
        cargarProductos();
        adicionarNuevoProducto();
        cargarRecyclerView(); //TODO: Esto debe ir despues de onStart()
    }

    public void cargarProductos(){
        if(!archivo.exists())return;
        FileInputStream fis = null;
        ObjectInputStream  ois = null;
        try {
            fis = openFileInput(archivo.getPath());
            ois = new ObjectInputStream(fis);
            productos = (ArrayList<Producto>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            limpiarInputStream(fis);
            limpiarInputStream(ois);
        }
    }

    public void limpiarInputStream(InputStream i){
        try {
            i.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarNuevoProducto(){
        Intent i = getIntent(); //TODO: Este intent podra ser null?
        Producto producto = (Producto) i.getSerializableExtra(PRODUCTO_A_VENDER);

        if(producto == null)return;
        if(seCargaronLosProductos()){
            if(!estaAgregado(producto))productos.add(producto);
        }else{
            productoActual = producto;
            productos = new ArrayList<Producto>();
            productos.add(producto);
        }
    }

    public boolean seCargaronLosProductos(){
        if(productos == null)return false;
        return true;
    }

    public boolean estaAgregado(Producto producto){
        for(Producto p : productos){
            if(p.getCodigo() == producto.getCodigo()) return true;
        }
        return false;
    }

    public void agregarProducto(View v){
        Intent i = new Intent(this, EscaneoActivity.class);
        startActivity(i);
    }

    public void comprar(View v){
        Intent i = new Intent(this, FacturaActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Compra Activity","Debo estar guardando algo");
        guardarProductos();
    }

    public void guardarProductos(){
        if(productos == null)return;
        FileOutputStream fos = null;
        ObjectOutputStream ous = null;

        try {
            fos = openFileOutput(archivo.getPath(), Context.MODE_PRIVATE);
            ous = new ObjectOutputStream(fos);
            ous.writeObject(productos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            limpiarOutputStream(fos);
            limpiarOutputStream(ous);
        }
    }

    public void limpiarOutputStream(OutputStream o){
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
