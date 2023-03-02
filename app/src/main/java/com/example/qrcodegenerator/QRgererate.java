package com.example.qrcodegenerator;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QRgererate extends AppCompatActivity {
    ImageView image;
    EditText edit;
    Button genertor;
    Bitmap bitmap;
  QRGEncoder encoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgererate);
image=findViewById(R.id.image);
edit=findViewById(R.id.edit);
genertor=findViewById(R.id.generatorbtn);


genertor.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String text=edit.getText().toString();
        if(TextUtils.isEmpty(text))
        {
            Toast.makeText(QRgererate.this, "enter some code to generate QR", Toast.LENGTH_SHORT).show();
        }
        else
        {
            WindowManager manager=(WindowManager) getSystemService(WINDOW_SERVICE);
            Display display= manager.getDefaultDisplay();
            Point point=new Point();
            display.getSize(point);
            int width=point.x;
            int height=point.y;
            int dimension= width<height? width:height;
            dimension=dimension*3/4;
            encoder=new QRGEncoder(text,null, QRGContents.Type.TEXT,dimension);
            try {
                bitmap=encoder.encodeAsBitmap();
            }
            catch(WriterException e)
            {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmap);
        }
    }
});
    }
}