package lector.gi.unibague.gilectorcodigodebarras;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import room.entidades.Producto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioProducto;
import viewModel.AdicionProductoViewModel;
import viewModel.FabricaViewModel;

public class AdicionProductoActivity extends AppCompatActivity{

    private EditText etCodigoProducto;
    private EditText etNombreProducto;
    private EditText etCantidadProducto;
    private EditText etPrecioProducto;
    private AdicionProductoViewModel vmAdicionProducto;

    public final static String CODIGO_PRODUCTO = "Codigo producto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicion_producto);
        FabricaViewModel.FabricaAdicionProductoViewModel fab = new FabricaViewModel.FabricaAdicionProductoViewModel(getApplicationContext());
        vmAdicionProducto = ViewModelProviders.of(this, fab).get(AdicionProductoViewModel.class);
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
        vmAdicionProducto.agregarProductos(darProducto())
            .subscribe(() -> irAMainActivity());
    }

    private Producto darProducto(){
        long codigo = Long.parseLong(etCodigoProducto.getText().toString());
        System.out.println(codigo);
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
    public void onBackPressed() {
        Intent startMain = new Intent(this, MainActivity.class);
        startActivity(startMain);
    }
}