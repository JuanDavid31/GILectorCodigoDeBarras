package viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import room.entidades.Producto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioProducto;

public class ProductosViewModel extends ViewModel {

    Repositorio repo;

    ProductosViewModel(Context context){
        repo = new RepositorioProducto(context);

    }

    public Flowable<List<Producto>> darProductos(){
        return repo.darElementos()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
