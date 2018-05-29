package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import io.reactivex.Completable;
import room.entidades.Producto;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderEscritura;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioProducto;

public class AdicionProductoActivity extends AppCompatActivity  implements IPostLoaderEscritura {

    private EditText etCodigoProducto;
    private EditText etNombreProducto;
    private EditText etCantidadProducto;
    private EditText etPrecioProducto;

    public final static String CODIGO_PRODUCTO = "Codigo producto";
    public final static String PRODUCTO = "Producto";
    public final static int LOADER_ESCRITOR_PRODUCTO_DB = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicion_producto);
        cargarVariablesIniciales();
        cargarCodigo();
    }

    private void cargarVariablesIniciales(){
        etCodigoProducto = findViewById(R.id.et_codigo_producto);
        etNombreProducto = findViewById(R.id.et_nuevo_nombre_producto);
        etCantidadProducto = findViewById(R.id.et_nueva_cantidad_producto);
        etPrecioProducto = findViewById(R.id.et_nuevo_precio_producto);
    }

    private void cargarCodigo(){
        Intent intent = getIntent();
        long codigo = intent.getLongExtra(CODIGO_PRODUCTO, 0);
        etCodigoProducto.setText(codigo + "");
    }

    public void agregarProducto(View v){
        Repositorio repo = new RepositorioProducto(getApplicationContext());
        Completable
                .fromAction(() -> repo.agregarElemento(darProducto()))
                .subscribe(() -> irAMainActivity());
    }

    private Producto darProducto(){
        long codigo = Long.parseLong(etCodigoProducto.getText().toString());
        String nombre = etNombreProducto.getText().toString();
        int cantidad = Integer.parseInt(etCantidadProducto.getText().toString());
        int precio = Integer.parseInt(etPrecioProducto.getText().toString());
        return new Producto(codigo, nombre, cantidad, precio);

    }

    public void irAMainActivity(){
        Intent i = new Intent(AdicionProductoActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void accionPostLoaderEscritura(Long l) {

    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(this, MainActivity.class);
        startActivity(startMain);
    }
}