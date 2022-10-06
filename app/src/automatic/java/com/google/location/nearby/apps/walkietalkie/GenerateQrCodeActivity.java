package com.google.location.nearby.apps.walkietalkie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQrCodeActivity extends AppCompatActivity {
    private TextView qrCodeTV;
    private ImageView qrCodeIV;
    private EditText dataEdt;
    private Button generateQRBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr_code);
        qrCodeTV= findViewById(R.id.idTVGenerateQr);
        qrCodeIV= findViewById(R.id.idIVQRcode);
        dataEdt=findViewById(R.id.idEdtData);
        generateQRBtn=findViewById(R.id.idBtnGenerateQR);
        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data= dataEdt.getText().toString();
                MultiFormatWriter writer = new MultiFormatWriter();
                if(data.isEmpty())
                {
                    Toast.makeText(GenerateQrCodeActivity.this, "Please enter some data", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    WindowManager manager= (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display= manager.getDefaultDisplay();
                    Point point=new Point();
                    display.getSize(point);
                    int width=point.x;
                    int height= point.y;
                    int dimen= width <height? width:height;
                    dimen =dimen *3/4;
                    try {
                        BitMatrix matrix= writer.encode(data,BarcodeFormat.QR_CODE,dimen,dimen);
                        BarcodeEncoder m=new BarcodeEncoder();
                        Bitmap bitmap= m.createBitmap(matrix);
                        qrCodeIV.setImageBitmap(bitmap);
                        qrCodeTV.setVisibility(View.GONE);
                        InputMethodManager manager1=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager1.hideSoftInputFromWindow(dataEdt.getApplicationWindowToken(),0);
                    }
                    catch (WriterException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}