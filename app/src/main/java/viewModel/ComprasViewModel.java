package viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import room.entidades.DatosCompra;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioCompra;

public class ComprasViewModel extends ViewModel {

    private RepositorioCompra repo;

    public ComprasViewModel(Context context){
        repo = new RepositorioCompra(context);
    }

    public Flowable<List<DatosCompra>> darDatosCompras(){
        return repo.darDatosCompras()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
