package com.example.qrcodegenerator;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
@SuppressLint("MissingPermission")
public class QRscanner extends AppCompatActivity {
SurfaceView surface;
TextView text;
Button scan;
private BarcodeDetector barcode;
private CameraSource camera;
private static  final int REQUEST_CAMERA_PERMISSION=201;
String intentdata="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        surface=findViewById(R.id.surfaceview);
        text=findViewById(R.id.textbarcodevalue);
        scan=findViewById(R.id.scanbtn);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intentdata.length()>0)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(intentdata)));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseCameraResource();
    }

    private void initialiseCameraResource()
    {
        Toast.makeText(this, "initalise start", Toast.LENGTH_SHORT).show();
        barcode=new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        camera=new CameraSource.Builder(this,barcode)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1080,480)
                .build();
        surface.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
//                if(ActivityCompat.checkSelfPermission(QRscanner.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)
//                {
//
//                }
                try {
                    camera.start(surface.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
camera.stop();
            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(QRscanner.this, "relase resource there ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcode=detections.getDetectedItems();
        if(barcode.size()!=0)
        {
            text.post(new Runnable() {
                @Override
                public void run() {
                    scan.setText("LAUNCH url");
                    intentdata=barcode.valueAt(0).displayValue;
                    text.setText(intentdata);
                }
            });
        }
            }
        });
    }
}