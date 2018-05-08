package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Intent;
import android.database.Cursor;
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

import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderConsulta;

public class MainActivity extends AppCompatActivity implements IPostLoaderConsulta {

    private static TextView tvInformacion;
    private static ProgressBar pbBarraProgreso;
    private static RecyclerView rvListaProductos;
    public final static int LOADER_CONSULTOR_DB = 11;

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

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();

        if(intent == null) return; //TODO: intent sera null al iniciar la app?
        if(intent.getBooleanExtra(REFRESCAR_DATOS, false)) realizarConsulta();
    }

    public void realizarConsulta(){
        ocultarLista();
        getSupportLoaderManager().initLoader(LOADER_CONSULTOR_DB, null, AdminSingletons.darInstancia(this));
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

    public static void mostrarMensaje(){
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


    @Override
    public void accionPostLoaderConsulta(Cursor cursor) {
        rvListaProductos.setAdapter(new AdaptadorProductoEnStock(cursor));
        if(cursor.getCount() == 0){
            mostrarMensaje();
        }else{
            mostrarLista();
        }
    }
}