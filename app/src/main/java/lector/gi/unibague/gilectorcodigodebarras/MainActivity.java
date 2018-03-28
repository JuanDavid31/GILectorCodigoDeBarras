package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import lector.gi.unibague.gilectorcodigodebarras.provider.AsistenteLectorCodigoDeBarras;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

public class MainActivity extends AppCompatActivity {

    private static TextView tvInformacion;
    private static ProgressBar pbBarraProgreso;
    private static RecyclerView rvListaProductos;

    public final static String REFRESCAR_DATOS = "Refrescas datos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarVariablesIniciales();
        realizarConsulta();
    }

    private void cargarVariablesIniciales(){
        tvInformacion = findViewById(R.id.tv_informacion_productos);
        pbBarraProgreso = findViewById(R.id.pb_indicador_carga);
        rvListaProductos = findViewById(R.id.rv_recycler_view);
        rvListaProductos.setHasFixedSize(true);
        rvListaProductos.setLayoutManager(new LinearLayoutManager(this));
        rvListaProductos.setAdapter(null);
    }

    public void realizarConsulta(){
        ocultarLista();
        new ConsultorDB(rvListaProductos).execute(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent != null){
            if(intent.getBooleanExtra(REFRESCAR_DATOS, false)){
                realizarConsulta();
            }
        }
    }

    public static void ocultarLista(){
        pbBarraProgreso.setVisibility(View.VISIBLE);
        tvInformacion.setVisibility(View.INVISIBLE);
        rvListaProductos.setVisibility(View.INVISIBLE);
    }

    public static void mostrarLista(){
        pbBarraProgreso.setVisibility(View.INVISIBLE);
        tvInformacion.setVisibility(View.INVISIBLE);
        rvListaProductos.setVisibility(View.VISIBLE);
    }

    public static void mostrarTexto(){
        pbBarraProgreso.setVisibility(View.INVISIBLE);
        tvInformacion.setVisibility(View.VISIBLE);
        rvListaProductos.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.acccion_escanear:
                Intent i = new Intent(this, EscaneoActivity.class);
                startActivity(i);
                Log.d( "Holi", "Debería estar escaneando");
        }
        return super.onOptionsItemSelected(item);
    }

    public class ConsultorDB extends AsyncTask<Integer, Void, Cursor>{

        private final AsistenteLectorCodigoDeBarras asistente = new AsistenteLectorCodigoDeBarras(MainActivity.this);
        RecyclerView rv;

        public ConsultorDB(RecyclerView rv){
            this.rv = rv;
        }

        @Override
        protected Cursor doInBackground(Integer... integers) {
            if(integers[0] == 0){
                return darProductos();
            }else{
                return darProducto(integers[0]);
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            rv.setAdapter(new AdaptadorProducto(cursor));
            if(cursor.getCount() == 0){
                mostrarTexto();
            }else{
                mostrarLista();
            }
        }

        public Cursor darProductos(){
            SQLiteDatabase db = asistente.getReadableDatabase();
            return db.query(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        }

        public Cursor darProducto(int id){
            SQLiteDatabase db = asistente.getReadableDatabase();
            return db.query(ContratoLectorCodigoDeBarras.Producto.NOMBRE_TABLA,
                    null,
                    ContratoLectorCodigoDeBarras.Producto._ID + " = ?",
                    new String[]{Integer.toString(id)},
                    null,
                    null,
                    null);
        }

    }
}