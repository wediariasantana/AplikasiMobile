package com.gmail.wediariasantana;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MyListAdapter extends ArrayAdapter<String> {
    //TODO 1 : bagian ini adalah klas yang menghandle kostumasi dari list yang sebelumnya dipanggil dari MainActivity dengan paramter maintitle,subtitle, dan imgid
    //TODO 2 : deklarasi objek untuk menampung parameter
    private final Activity context;
    private final String[] desc;
    private final String[] imgpath;

    //TODO 3 : ini adalah konstruktor yang menerima data yang dikirim sebagai parameter
    public MyListAdapter(Activity context,String[]maintitle,String[]imgid){
        //TODO 4 : super(context,R.layout.mylist,maintitle); berati class ini menggunakan layout mylist sebagai tampilanya
        super(context,R.layout.view2,maintitle);
        //TODOAuto-generatedconstructorstub
        this.context=context;
        this.desc=maintitle;
        this.imgpath=imgid;
    }
    //TODO 5 : method getview adalah method yang berfungsi untuk menampilkan data dari array kedalam list karena class ii merupakan turunan dari ArrayAdapter dan disertai<String> maka nilai yang ditampung klas ini dapat lebih dari satu
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.view2, null, true);
        //TODO 6 : menghubungkan object dengan komponen view
        TextView titleText = (TextView) rowView.findViewById(R.id.desc);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.photos);

        //TODO 6 : meng-set komponen view melalui object terkait dengan isian nilai array
        if (desc[position].length()>25){
            titleText.setText(desc[position].substring(0,23)+" ...Slengkapnya");
        }else
        titleText.setText(desc[position]);
        File imgFile = new File(imgpath[position]);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }else {
            System.out.println("Result of find : Cant Find Photo");
        }
        //imageView.setImageURI(Uri.parse("https://belajarkoding.net/wp-content/uploads/2017/08/Ada-Apa-Saja-Fitur-Terbaru-Di-Android-O.png"));
        //TODO 7 : mengembalikan nilai view
        return rowView;
    }
}
