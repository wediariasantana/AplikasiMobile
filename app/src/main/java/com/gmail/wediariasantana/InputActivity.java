package com.gmail.wediariasantana;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

@SuppressWarnings("ALL")
public class InputActivity extends AppCompatActivity {
    ImageView picture;
    Button open;
    Button save;
    Bitmap bi;
    MySQLHelper dbHelper;
    TextView descript;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        picture=(ImageView)findViewById(R.id.PhotoView);
        open=(Button)findViewById(R.id.OpenCamera);
        save=(Button)findViewById(R.id.SaveView);
        dbHelper = new MySQLHelper(this);
        descript=(TextView)findViewById(R.id.Descript);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,0);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData(bi);
            }
        });
        save.setEnabled(false);
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        save.setEnabled(false);
        if (resultCode==RESULT_OK) {
            bi = (Bitmap) data.getExtras().get("data");
            picture.setImageBitmap(bi);
            save.setEnabled(true);
        }else if (resultCode==RESULT_CANCELED){
            Toast.makeText(this, "Kamera Dibatalkan", Toast.LENGTH_LONG).show();
        }
    }
    private void SaveData(Bitmap finalBitmap) {
        File myDir = new File(this.getFilesDir(),"mydir");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }

        //TODO 11 : menampilkan lokasi penyimpanan dalam bentuk Toast
        Toast.makeText(this, myDir.toString(), Toast.LENGTH_LONG).show();

        //TODO 12 memberi nama file secara acak
        Random generator = new Random();
        int n = 10000;
        File file;
        do {
            n = generator.nextInt(n);
            file = new File(myDir, "Image-" + n + ".jpg");
        }while (file.exists());

        //TODO 13 : memasukan data gambar kedalam file yang telah dibuat
        try {
            FileOutputStream out = new FileOutputStream(file);
            //TODO 14 : mengkopres data kedalam file jpg ter kompres (seperti yang kita tahu format jpg merupakan fil gambar ter kompresi)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            //TODO 15 : menutup file
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String isi= (String) descript.getText().toString();
        addData(file.getAbsolutePath(), isi);
    }
     void addData(String photo,String desc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("insert into " + MySQLHelper.TABLE
                    + " values(null,'" +photo
                    + "','" +desc+ "')");
        } catch (Exception ignored) {}
         finish();
    }
}
