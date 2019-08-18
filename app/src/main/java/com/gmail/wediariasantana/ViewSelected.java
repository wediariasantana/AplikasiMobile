package com.gmail.wediariasantana;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ViewSelected extends AppCompatActivity {
    String photo_addres;
    String detail;
    TextView detailvar;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        detailvar=(TextView)findViewById(R.id.Desc);
        imageView=(ImageView)findViewById(R.id.PhotoDetail);
        Bundle extras = getIntent().getExtras();
        photo_addres= extras.getString("photo_addres");
        detail= extras.getString("detail");
        File imgFile = new File(photo_addres);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            detailvar.setText(detail);
        }else {
            finish();
        }
    }
}
