package lector.gi.unibague.gilectorcodigodebarras;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.vision.barcode.Barcode;

import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

public class AdicionProductoActivity extends AppCompatActivity  implements IRespuesta {

    private EditText etCodigoProducto;
    private EditText etNombreProducto;
    private EditText etCantidadProducto;

    public final static String CODIGO_PRODUCTO = "Codigo producto";

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
    }

    private void cargarCodigo(){
        Bundle bundle = getIntent().getExtras();
        Barcode codigoBarras = (Barcode) bundle.get(CODIGO_PRODUCTO);
        etCodigoProducto.setText(codigoBarras.displayValue);
    }

    public void agregarProducto(View v){
        long codigo = Long.parseLong(etCodigoProducto.getText().toString());
        String nombre = etNombreProducto.getText().toString();
        int cantidad = Integer.parseInt(etCantidadProducto.getText().toString());

        ContentValues valores = new ContentValues();
        valores.put(ContratoLectorCodigoDeBarras.Producto._ID, codigo);
        valores.put(ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE, nombre);
        valores.put(ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD, cantidad);

        new EscritorBD(this).execute(valores);
    }

    @Override
    public void postRespuesta(long id) {
        Intent i = new Intent(AdicionProductoActivity.this, MainActivity.class);
        if(id != -1){
             i.putExtra(MainActivity.REFRESCAR_DATOS, true);
        }
        startActivity(i);
    }

    public class EscritorBD extends AsyncTask<ContentValues, Void, Long>{

        AsistenteLectorCodigoDeBarras asistente = new AsistenteLectorCodigoDeBarras(AdicionProductoActivity.this);
        private IRespuesta ir;

        public EscritorBD(IRespuesta ir){
            this.ir = ir;
        }

        @Override
        protected Long doInBackground(ContentValues... valores) {
            SQLiteDatabase db = asistente.getWritableDatabase();
            return db.insert(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA, null, valores[0]);
        }

        @Override
        protected void onPostExecute(Long id) {
            super.onPostExecute(id);
            ir.postRespuesta(id);
        }
    }
}