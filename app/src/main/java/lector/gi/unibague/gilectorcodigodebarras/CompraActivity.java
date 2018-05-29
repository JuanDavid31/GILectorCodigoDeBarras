package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import room.entidades.Producto;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.CompraProducto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioCliente;
import room.repositorio.RepositorioCompra;
import room.repositorio.RepositorioCompraProducto;
import room.repositorio.RepositorioProducto;

public class CompraActivity extends AppCompatActivity implements DialogoCantidadAVender.DialogListener{

    public final static String PRODUCTO_A_VENDER = "Producto a vender";
    public final static String PRODUCTO_A_ACTUALIZAR = "Producto a actualizar";
    public final static String CEDULA_CLIENTE = "Cedula cliente";
    public final static String PRODUCTOS = "Productos";
    private File archivo;
    private ArrayList<Producto> productos;
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
        agregarCompraProductoYActualizarProductos();
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

    public void agregarCompraProductoYActualizarProductos(){
        //Insercion, consulta, insercion, actualización
        //Completable, Flowable, Completable, Completable
        Completable c = insertarClienteDummy();
        Flowable<Integer> fCodigoCompra = insertarCompra();

//        V1
//        c.andThen(fCodigoCompra).map(codigoCompra ->{
//
//            List<CompraProducto> compraProductos = null;
//            compraProductos = new ArrayList<CompraProducto>();
//            for (Producto p: productos) {
//                compraProductos.add(new CompraProducto(0, codigoCompra, p.getCodigo(), p.getCantidad()));
//            }
//            return compraProductos;
//        }).subscribe(compraProductos -> {
//
//            RepositorioCompraProducto repo = new RepositorioCompraProducto(getApplicationContext());
//            RepositorioProducto repo2 = new RepositorioProducto(getApplicationContext());
//            repo.agregarCompraProductos((CompraProducto[]) compraProductos.toArray())
//                    .mergeWith(repo2.actualizarProductos((Producto[]) productos.toArray()))
//                    .subscribe(() -> irAFacturaACtivity());
//        }).dispose();

//        V2
        c.andThen(fCodigoCompra)
            .map(codigoCompra -> crearCompraProductosPorCodigoCompra(codigoCompra))
            .flatMapCompletable(compraProductos -> agregarCompraProductos(compraProductos))
            .andThen(actualizarCompraProductos())
            .subscribe();

//        V3
//        c.andThen(fCodigoCompra)
//            .map(this::crearCompraProductosPorCodigoCompra)
//            .flatMapCompletable(this::agregarCompraProductos)
//            .andThen(actualizarCompraProductos())
//            .subscribe();

    }

    private Completable insertarClienteDummy(){
        Cliente cliente = new Cliente(1110582700, "Juan");
        Repositorio repo = new RepositorioCliente(getApplicationContext());
        return Completable.fromAction(() -> repo.agregarElemento(cliente));
    }

    private Flowable<Integer> insertarCompra(){

        String fecha = (String) DateFormat.format("yyyy-MM-dd hh:mm:ss a", Calendar.getInstance().getTime());

        Repositorio repo = new RepositorioCompra(getApplicationContext());
        Completable p = repo.agregarElemento(new Compra(0, 1110582700, fecha));
        RepositorioCompra repo2 = new RepositorioCompra(getApplicationContext());
        Flowable<Compra> f = repo2.darCompraPorFecha(fecha);
        Flowable f1 = f.flatMap(compra -> Flowable.fromArray(compra.getCodigo()));
        return p.andThen(f1);

    }

    private ArrayList crearCompraProductosPorCodigoCompra(int codigoCompra){
        ArrayList<CompraProducto> compraProductos = null;
        compraProductos = new ArrayList<CompraProducto>();
        for (Producto p: productos) {
            compraProductos.add(new CompraProducto(0, codigoCompra, p.getCodigo(), p.getCantidad()));
        }
        return compraProductos;
    }

    private Completable agregarCompraProductos(ArrayList compraProductos){
        RepositorioCompraProducto repo = new RepositorioCompraProducto(getApplicationContext());
        return repo.agregarCompraProductos((CompraProducto[]) compraProductos.toArray());
    }

    private Completable actualizarCompraProductos(){
        RepositorioProducto repo2 = new RepositorioProducto(getApplicationContext());
        return repo2.actualizarProductos((Producto[]) productos.toArray());
    }

    public void irAFacturaACtivity(){
        Intent i = new Intent(this, FacturaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCTOS, productos);
        i.putExtras(bundle);
        limpiarCache();
        startActivity(i);
    }

    @Override
    public void accion(int cantidad, int posicion) {
        Producto producto = productos.get(posicion);
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
