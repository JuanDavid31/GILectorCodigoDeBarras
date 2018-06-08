package lector.gi.unibague.gilectorcodigodebarras.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import io.reactivex.functions.Predicate;
import room.DatabaseApp;
import room.entidades.Cliente;
import room.entidades.Compra;
import room.entidades.Producto;

@RunWith(AndroidJUnit4.class)
public class DaoClienteTest {

    private DatabaseApp db;
    private Producto productoDummy
            = new Producto(Long.parseLong("1"), "producto", 1, 100);
    private Cliente clienteDummy =
            new Cliente(1, "Juan");
    private Compra compraDummy
            = new Compra(1, 1, "hoy");

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                DatabaseApp.class)
                .allowMainThreadQueries()
                .build();
        db.darDaoProducto().agregarProducto(productoDummy);
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void hacerDeTodoTest(){
        ArrayList<Producto> productos = new ArrayList<>();
        productos.add(productoDummy);
        db.darDaoCliente().hacerDeTodo(db, clienteDummy, compraDummy, productos);
        db.darDaoCliente()
            .darCliente(1)
            .test()
            .assertValue(new Predicate<Cliente>() {
                @Override
                public boolean test(Cliente cliente) throws Exception {
                    return cliente.getNombre().equals(cliente.getNombre());
                }
            });
        db.darDaoCompra()
            .darCompraPorFecha("hoy")
            .test()
            .assertValue(new Predicate<Compra>() {
                @Override
                public boolean test(Compra compra) throws Exception {
                    return compra.getCodigo() == compraDummy.getCodigo();
                }
            });
        //TODO: Ampliar este test
    }

}
