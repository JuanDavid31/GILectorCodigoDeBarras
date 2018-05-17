package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderEscritura;

public class CompraActivity extends AppCompatActivity implements IPostLoaderEscritura, DialogoCantidadAVender.DialogListener{

    public final static String PRODUCTO_A_VENDER = "Producto a vender";
    public final static String PRODUCTO_A_ACTUALIZAR = "Producto a actualizar";
    public final static String CEDULA_CLIENTE = "Cedula cliente";
    public final static String CANTIDAD_VENDIDA = "Cantidad vendida";
    public final static String PRODUCTOS = "Productos";
    public final static int LOADER_ESCRITOR_CLIENTE = 14;
    public final static int LOADER_ESCRITOR_COMPRA = 15;
    public final static int LOADER_ESCRITOR_COMPRA_PRODUCTO = 16;
    public final static int LOADER_ACTUALIZADOR_PRODUCTO = 17;
    public final static String CODIGO_COMPRA= "Codigo compra";
    public final static String CODIGO_PRODUCTO = "Codigo producto";
    private File archivo;
    private ArrayList<Producto> productos;
    private Producto productoActual;
    private int codigoCompra;

    private RecyclerView rvListaProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);
    }

    @Override
    protected void onStart() {
        setTitle("Compra");
        super.onStart();
        cargarProductos(this);
        adicionarNuevoProducto();
        cargarRecyclerView();
    }

    public void cargarRecyclerView(){
        rvListaProductos = findViewById(R.id.rv_recycler_view_compra);
        rvListaProductos.setHasFixedSize(true);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(this));
        rvListaProductos.setAdapter(new AdaptadorProductoEnCompra(productos, this));
    }

    public void cargarProductos(Context context){
        archivo = new File(context.getCacheDir(), File.pathSeparator + "productos");
        if(!archivo.exists())return;
        FileInputStream fis = null;
        ObjectInputStream  ois = null;
        try {
            fis = new FileInputStream(archivo.getPath());
            ois = new ObjectInputStream(fis);
            productos = (ArrayList<Producto>) ois.readObject();
            limpiarInputStream(fis);
            limpiarInputStream(ois);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int posicion){
        productos.remove(posicion);
        rvListaProductos.getAdapter().notifyItemRemoved(posicion);
        rvListaProductos.getAdapter().notifyItemRangeChanged(posicion, productos.size());
    }

    public void limpiarInputStream(InputStream i){
        try {
            i.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adicionarNuevoProducto(){
        Intent i = getIntent();
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
            if(p.getCodigo().equals(producto.getCodigo())) {
                Toast.makeText(this, "Ya esta agregado el producto", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public void agregarProducto(View v){
        Intent i = new Intent(this, EscaneoActivity.class);
        startActivity(i);
    }

    public void comprar(View v){
        if(productos == null || productos.size() == 0){
            Toast.makeText(this, "Añade un producto", Toast.LENGTH_SHORT).show();
            return;
        }
        insertarClienteDummy();
        insertarCompra();
        //Sigue en accionPostLoaderEscritura
    }

    public void insertarClienteDummy(){
        getSupportLoaderManager().initLoader(LOADER_ESCRITOR_CLIENTE,
                null,
                AdminSingletons.darInstanciaEscritorCliente(this)).forceLoad();
    }

    public void insertarCompra(){
        Bundle b = new Bundle();
        b.putInt(CEDULA_CLIENTE,1110582700);
        getSupportLoaderManager().initLoader(LOADER_ESCRITOR_COMPRA,
                b,
                AdminSingletons.darInstanciaEscritorCompra(this, this)).forceLoad();
    }

    public void limpiarCache(){
        archivo.delete();
        productos = null;
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
        guardarProductos();
    }

    public void guardarProductos(){
        if(productos == null)return;
        FileOutputStream fos = null;
        ObjectOutputStream ous = null;

        try {
            fos = new FileOutputStream(archivo.getPath());
            ous = new ObjectOutputStream(fos);
            ous.writeObject(productos);
            limpiarOutputStream(fos);
            limpiarOutputStream(ous);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void limpiarOutputStream(OutputStream o){
        try {
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accionPostLoaderEscritura(Long l) {
        codigoCompra = Integer.parseInt(l.toString());

        Intent i = new Intent(this, FacturaActivity.class);
        for(Producto p: productos){
            agregarCompraProducto(codigoCompra, p.getCodigo(), p.getCantidadVendida());
            actualizarProducto(p);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCTOS, productos);
        i.putExtras(bundle);

        limpiarCache();

        startActivity(i);
    }

    public void actualizarProducto(Producto producto){
        Bundle b = new Bundle();
        b.putSerializable(PRODUCTO_A_ACTUALIZAR, producto);
        getSupportLoaderManager().initLoader(LOADER_ACTUALIZADOR_PRODUCTO,
                b,
                AdminSingletons.darInstanciaActualizadorProducto(this)).forceLoad();
    }

    public void agregarCompraProducto(int codigoCompra, Long codigoProducto, int cantidadVendida){
        Bundle b = new Bundle();
        b.putInt(CODIGO_COMPRA, codigoCompra);
        b.putLong(CODIGO_PRODUCTO, codigoProducto);
        b.putInt(CANTIDAD_VENDIDA, cantidadVendida);
        getSupportLoaderManager().initLoader(LOADER_ESCRITOR_COMPRA_PRODUCTO,
                b,
                AdminSingletons.darInstanciaEscritorCompraProducto(this)).forceLoad();
    }

    @Override
    public void accion(int cantidad, int posicion) {
        Producto producto = productos.get(posicion);
        try {
            producto.setCantidadVendida(cantidad);
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
