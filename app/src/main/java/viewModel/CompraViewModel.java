package viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.text.format.DateFormat;

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
import io.reactivex.schedulers.Schedulers;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.Producto;
import room.repositorio.RepositorioCliente;

public class CompraViewModel extends ViewModel {

    private Context context;
    private RepositorioCliente repo;
    private File archivo;
    public ArrayList<Producto> productos;

    public CompraViewModel(Context context){
        repo = new RepositorioCliente(context);
        this.context = context;
    }

    public void cargarProductos(){
        archivo = new File(context.getCacheDir(), File.pathSeparator + "productos");
        if(!archivo.exists())return;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
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

    public void limpiarInputStream(InputStream i){
        try {
            i.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean adicionarProducto(Producto producto){
        producto.setCantidad(1);
        if(producto == null)return false;
        if(seCargaronLosProductos()){
            if(!estaAgregado(producto)){
                productos.add(producto);
                return true;
            }else {
                return false;
            }
        }else{
            productos = new ArrayList<Producto>();
            productos.add(producto);
            return true;
        }
    }

    public boolean seCargaronLosProductos(){
        if(productos == null)return false;
        return true;
    }

    public boolean estaAgregado(Producto producto){
        for(Producto p : productos){
            if(p.getCodigo().equals(producto.getCodigo())) {
                return true;
            }
        }
        return false;
    }

    public void limpiarCache(){
        archivo.delete();
        productos = null;
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

    public Completable hacerDeTodo(){
        Cliente cliente = darClienteDummy();
        Compra compra = darCompraConFechaActual();

        return Completable.fromAction(() ->{
            repo.darInstanciaDB()
                .darDaoCliente()
                .hacerDeTodo(repo.darInstanciaDB(), cliente, compra, productos);
        }).subscribeOn(Schedulers.io());
    }

    public Cliente darClienteDummy(){
        return new Cliente(111, "Juan");
    }

    public Compra darCompraConFechaActual(){
        return new Compra(0, 111, darFechaActual());
    }

    public String darFechaActual(){
        return (String) DateFormat.format("yyyy-MM-dd hh:mm:ss a", Calendar.getInstance().getTime());
    }
}
