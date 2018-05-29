package room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import room.entidades.Cliente;

@Dao
public interface DaoCliente {

    @Insert
    void agregarCliente(Cliente cliente);

}
