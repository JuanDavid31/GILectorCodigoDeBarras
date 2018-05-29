package lector.gi.unibague.gilectorcodigodebarras;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
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
import java.util.List;

import room.entidades.Producto;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.AdminSingletons;
import lector.gi.unibague.gilectorcodigodebarras.persistencia.IPostLoaderConsulta;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioCompra;
import room.repositorio.RepositorioProducto;

public class EscaneoActivity extends AppCompatActivity implements IPostLoaderConsulta, Runnable {

    SurfaceView camara;

    private Long codigoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) { Log.i("EscaneoActivity", "onCreate");
        setTitle("Escaner");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaneo);
        camara = (SurfaceView) findViewById(R.id.sv_escaneo_camara);
        configurarCamara();
    }

    public void realizarConsulta(){
        new Handler(Looper.getMainLooper()).post(this);
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
        detector.setProcessor(new Procesador(this));
    }

    public class Procesador implements Detector.Processor<Barcode> {

        EscaneoActivity escaneoActivity;

        public Procesador(EscaneoActivity e){
            this.escaneoActivity = e;
        }

        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            SparseArray<Barcode> objetosDetectados = detections.getDetectedItems();
            if(objetosDetectados.size() > 0) { Log.i("EscaneoActivity","receiveDetections");
                if (escaneoActivity.codigoActual != null)return;
                Barcode barcode = objetosDetectados.valueAt(0);
                escaneoActivity.codigoActual =  Long.parseLong(barcode.displayValue);
                escaneoActivity.realizarConsulta();
            }
        }

    }

    @Override
    public void accionPostLoaderConsulta(List<Producto> cursor) {

    }

    @Override
    public void run() {
        Repositorio repo = new RepositorioProducto(getApplicationContext());
        Log.i("Run EscaneoAc", (Looper.myLooper() == Looper.getMainLooper())+ "");
        repo.darElemento(codigoActual).subscribe( producto -> {
            Log.i("Flowable EscaneoAc", (Looper.myLooper() == Looper.getMainLooper())+ "");
            validarProducto((Producto) producto);
        });
    }

    public void validarProducto(Producto p){
        Intent i;
        if(p == null){
            i = new Intent(this, AdicionProductoActivity.class);
            i.putExtra(AdicionProductoActivity.CODIGO_PRODUCTO,  codigoActual);
        }else{

            i = new Intent(this, CompraActivity.class);
            i.putExtra(CompraActivity.PRODUCTO_A_VENDER, p);
        }
        startActivity(i);
    }

    private Producto darProducto(Cursor cursor){
        return  null;
    }
}