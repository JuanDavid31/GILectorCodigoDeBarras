package lector.gi.unibague.gilectorcodigodebarras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

public class FacturaActivity extends AppCompatActivity {

    private TextView tvFactura;
    private ArrayList<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Factura");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        cargarProductos();
        cargarVariablesIniciales();
    }

    public void cargarProductos(){
        Bundle b = getIntent().getExtras();
        productos = (ArrayList<Producto>) b.getSerializable(CompraActivity.PRODUCTOS);
    }

    public void cargarVariablesIniciales(){
        tvFactura = findViewById(R.id.tv_factura);
        tvFactura.setText(darFactura());
    }

    public String darFactura(){
        String factura = "";
        factura +=" -- Nombre empresa -- \n\n";
        factura += "    NIT XXXXXXXX     \n\n";
        factura += "  Gran contribuyente \n\n";
        factura += " Facturacion N° XXXXX\n\n";
        factura += "PRODUCTO        VALOR\n\n";
        factura += "=======================\n\n";
        for(Producto p: productos){
        factura += p.getNombre() + " x" +p.getCantidadVendida() +"                    $" +  (p.getPrecio()*p.getCantidadVendida()) + "\n";
        }
        factura += "\n";
        factura += "=======================\n\n";
        factura += "TOTAL                       $" + darPagoTotal();

        return factura;
    }

    public int darPagoTotal(){
        int total = 0;
        for(Producto p: productos){
            total += (p.getPrecio() * p.getCantidadVendida());
        }
        return total;
    }

    public void volverInicio(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
