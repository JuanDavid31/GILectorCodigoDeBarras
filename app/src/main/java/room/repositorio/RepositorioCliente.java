package room.repositorio;

import android.content.Context;
import android.util.Log;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import room.entidades.Cliente;

public class RepositorioCliente extends Repositorio<Cliente> {

    public RepositorioCliente(Context context) {
        super(context);
    }

    @Override
    public Flowable<List<Cliente>> darElementos() {
        return null;
    }

    @Override
    public Maybe<Cliente> darElemento(Object id) {
        return null;
    }

    @Override
    public Completable agregarElemento(Cliente elemento) {
        return Completable
                .fromAction(() -> {
                    darInstanciaDB().darDaoCliente().agregarCliente(elemento);
                });
    }

    @Override
    public Completable editarElemento(Cliente elemento) {

        return null;
    }

    @Override
    public Completable eliminarElemento(int id) {

        return null;
    }
}
