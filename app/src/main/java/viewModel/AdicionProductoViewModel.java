package viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import room.entidades.Producto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioProducto;

public class AdicionProductoViewModel extends ViewModel {

    private Repositorio repo;

    public AdicionProductoViewModel(Context context){
        repo = new RepositorioProducto(context);
    }

    public Completable agregarProductos(Producto producto){
        return repo.agregarElemento(producto)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
