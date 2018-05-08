package lector.gi.unibague.gilectorcodigodebarras;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import lector.gi.unibague.gilectorcodigodebarras.modelo.Producto;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.ConsultorBD;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderConsulta;
import lector.gi.unibague.gilectorcodigodebarras.provider.ContratoLectorCodigoDeBarras;

public class EscaneoActivity extends AppCompatActivity implements IPostLoaderConsulta {

    SurfaceView camara;

    private static Barcode codigoActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Escaner");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaneo);
        camara = (SurfaceView) findViewById(R.id.sv_escaneo_camara);
        configurarCamara();
    }

    public void configurarCamara() {
        BarcodeDetector detector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, detector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        camara.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(EscaneoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    //TODO: Si no tiene los permisos, entonces se deberían solicitar via ActivityCompat.requestPermissions - https://developer.android.com/training/permissions/requesting.html?hl=es-419#perm-request
                    return;
                }

                try {
                    cameraSource.start(camara.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            
            @Override
            public void release() {
                
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> objetosDetectados = detections.getDetectedItems();
                if(objetosDetectados.size() > 0) {
                    Log.d("Detector", "Coño! hemos detectado algo: " + objetosDetectados.get(0));
                    Barcode barcode = objetosDetectados.valueAt(0);
                    EscaneoActivity.codigoActual = barcode;
                    Bundle b = new Bundle();
                    b.putInt(ConsultorBD.X, Integer.parseInt(barcode.toString()));
                    getSupportLoaderManager().initLoader(MainActivity.LOADER_CONSULTOR_DB, b, AdminSingletons.darInstancia(EscaneoActivity.this));
                }
            }
        });
    }


    @Override
    public void accionPostLoaderConsulta(Cursor cursor) {
        Intent i;
        if(cursor.getCount() == 0){
            i = new Intent(this, AdicionProductoActivity.class);
        }else{
            i = new Intent(this, CompraActivity.class); //TODO: Debería pasar un objeto tipo producto
            i.putExtra(CompraActivity.PRODUCTO_A_VENDER, darProducto(cursor));
        }
        startActivity(i);
    }

    private Producto darProducto(Cursor cursor){
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto._ID));
        String nombre = cursor.getString(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto.COLUMNA_NOMBRE));
        int cantidad = cursor.getInt(cursor.getColumnIndex(ContratoLectorCodigoDeBarras.Producto.COLUMNA_CANTIDAD));

        return new Producto(id, nombre, cantidad);
    }
}