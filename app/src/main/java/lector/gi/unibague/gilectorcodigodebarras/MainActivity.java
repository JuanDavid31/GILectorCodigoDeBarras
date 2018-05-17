package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderConsulta;

public class MainActivity extends AppCompatActivity {

    public final static int LOADER_CONSULTOR_PRODUCTOS_DB = 11;
    public final static int LOADER_CONSULTOR_COMPRAS_DB = 12;
    public final static String REFRESCAR_DATOS = "Refrescas datos";
    private ViewPager vpPaginador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpPaginador = findViewById(R.id.vp_paginador_main);
        vpPaginador.setAdapter(new MainAdaptadorPaginador(getSupportFragmentManager()));
        //TODO: Hasta aquí no hay pestañas, sería bueno seguir el ejemplo de android studio
        //TODO: Probar en un proyecto vacio con solo pestañas.

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


}