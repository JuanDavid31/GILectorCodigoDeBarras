package lector.gi.unibague.gilectorcodigodebarras;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
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

import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.MaybeObserver;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import room.entidades.Producto;
import room.repositorio.Repositorio;
import room.repositorio.RepositorioCompra;
import room.repositorio.RepositorioProducto;
import viewModel.EscaneoViewModel;
import viewModel.FabricaViewModel;

public class EscaneoActivity extends AppCompatActivity {

    SurfaceView camara;

    private EscaneoViewModel vmEscaneo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Escaner");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaneo);
        camara = (SurfaceView) findViewById(R.id.sv_escaneo_camara);
        FabricaViewModel.FabricaEscaneoViewModel fab = new FabricaViewModel.FabricaEscaneoViewModel(getApplicationContext());
        vmEscaneo = ViewModelProviders.of(this, fab).get(EscaneoViewModel.class);
        configurarCamara();
    }

    public void realizarConsulta(){
        vmEscaneo.darProducto()
            .subscribe((producto) -> irACompraActivity((Producto) producto),
                    (err) -> Log.i("Error", err.toString()),
                    () -> irAAdicionProductoACtivity());
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
            if(objetosDetectados.size() > 0) {
                if (vmEscaneo.codigoActual != null)return;
                Barcode barcode = objetosDetectados.valueAt(0);
                vmEscaneo.codigoActual =  Long.parseLong(barcode.displayValue);
                escaneoActivity.realizarConsulta();
            }
        }

    }

    public void irACompraActivity(Producto producto){
        Intent i = new Intent(this, CompraActivity.class);
        i.putExtra(CompraActivity.PRODUCTO_A_VENDER, producto);
        startActivity(i);
    }

    public void irAAdicionProductoACtivity(){
        Intent i = new Intent(this, AdicionProductoActivity.class);
        i.putExtra(AdicionProductoActivity.CODIGO_PRODUCTO,  vmEscaneo.codigoActual);
        startActivity(i);
    }


}