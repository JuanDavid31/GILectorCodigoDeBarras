package repositorio;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.hamcrest.MatcherAssert.assertThat;

import room.entidades.Producto;
import room.repositorio.RepositorioProducto;

import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class RepositorioProductoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private Producto productoDummy
            = new Producto(Long.parseLong("1"), "producto", 1, 100);
    private Producto productoDummy2
            = new Producto(Long.parseLong("2"), "Producto2", 2, 200);

    @Captor
    private ArgumentCaptor<Producto> argumentCaptor;

    @Mock
    private RepositorioProducto repo;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void insertarProductoTest(){
        repo.agregarElemento(productoDummy)
            .test()
            .assertComplete();

        verify(repo).darInstanciaDB().darDaoProducto().agregarProducto(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getNombre(), Matchers.is("producto"));
    }
}
