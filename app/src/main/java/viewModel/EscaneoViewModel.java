package viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import room.entidades.Producto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioProducto;

public class EscaneoViewModel extends ViewModel{

    private Repositorio repo;

    public Long codigoActual;

    public EscaneoViewModel(Context context){
        repo = new RepositorioProducto(context);
    }

    public Maybe<Producto> darProducto(){
        return repo.darElemento(codigoActual)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
