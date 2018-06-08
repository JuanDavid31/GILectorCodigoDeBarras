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

import java.util.List;

import io.reactivex.functions.Predicate;
import room.DatabaseApp;
import room.entidades.Producto;

@RunWith(AndroidJUnit4.class)
public class DaoProductoTest {

    private Producto productoDummy
            = new Producto(Long.parseLong("1"), "producto", 1, 100);
    private Producto productoDummy2
            = new Producto(Long.parseLong("2"), "Producto2", 2, 200);

    private DatabaseApp db;

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
    public void darProductoTest(){
        db.darDaoProducto()
            .darProducto(Long.parseLong("1"))
            .test()
            .assertValue(new Predicate<Producto>() {
                @Override
                public boolean test(Producto producto) throws Exception {
                    return producto.getNombre().equals(productoDummy.getNombre());
                }
            });
    }

    @Test
    public void darProductosTest(){
        db.darDaoProducto()
            .darProductos()
            .test()
            .assertValue(new Predicate<List<Producto>>() {
                @Override
                public boolean test(List<Producto> productos) throws Exception {
                    return productos.get(0).getNombre().equals(productoDummy.getNombre());
                }
            });
    }

    @Test
    public void actualizarProductos(){
        db.darDaoProducto().agregarProducto(productoDummy2);
        productoDummy.setNombre("Me cambian1");
        productoDummy2.setNombre("Me cambian2");
        db.darDaoProducto().actualizarProductos(productoDummy, productoDummy2);
        db.darDaoProducto()
            .darProductos()
            .test()
            .assertValue(new Predicate<List<Producto>>() {
                @Override
                public boolean test(List<Producto> productos) throws Exception {
                    Producto producto = productos.get(0);
                    Producto producto1 = productos.get(1);
                    return producto.getNombre().equals(productoDummy.getNombre()) &&
                            producto1.getNombre().equals(productoDummy2.getNombre());
                }
            });
    }

    @Test
    public void darProductoInexistente(){
        db.darDaoProducto()
            .darProducto(Long.parseLong("2"))
            .test()
            .assertComplete();
    }

    @Test
    public void darProductosInexistentes(){
        db.clearAllTables();
        db.darDaoProducto()
            .darProductos()
            .test()
            .assertValue(new Predicate<List<Producto>>() {
                @Override
                public boolean test(List<Producto> productos) throws Exception {
                    return productos.size() == 0;
                }
            });
    }
}