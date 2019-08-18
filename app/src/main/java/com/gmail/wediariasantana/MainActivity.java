package com.gmail.wediariasantana;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    Button adddata;
    Intent forminput;
    MySQLHelper dbHelper;
    protected ListView fhotolist;
    protected Cursor cursor;
    MyListAdapter adapter;
    ListView list;
    Button rfr;
    Button clean;
    ArrayList<String> photo;
    ArrayList<String> desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MySQLHelper(this);
        adddata=(Button) findViewById(R.id.AddData);
        list=(ListView)findViewById(R.id.ListView01);
        rfr=(Button)findViewById(R.id.Refresh);
        clean =(Button)findViewById(R.id.Clear);
        photo = new ArrayList<String>();
        desc = new ArrayList<String>();
        this.fhotolist = (ListView) this.findViewById(R.id.ListView01);
        list.setOnItemClickListener(this);
        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forminput=new Intent(MainActivity.this, InputActivity.class);
                startActivity(forminput);

            }
        });
        rfr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo.clear();
                desc.clear();
                view();
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("delete from "+ "data");
                photo.clear();
                desc.clear();
                view();
            }
        });
        view();

    }

    private void view() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT * FROM data", null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                photo.add(cursor.getString(cursor.getColumnIndex("photo"))); //add the item
                desc.add(cursor.getString(cursor.getColumnIndex("desc"))); //add the item
                cursor.moveToNext();
            }
            String[] photoarray = photo.toArray(new String[0]);
            String[] descarray = desc.toArray(new String[0]);
            adapter=new MyListAdapter(this,descarray,photoarray);
            list.setAdapter(adapter);
        } catch (Exception ignored) {}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String photo_local=photo.get(position);
        String detail_local=desc.get(position);
        Intent data_rincian = new Intent(MainActivity.this, ViewSelected.class);
        data_rincian.putExtra("photo_addres", photo_local);
        data_rincian.putExtra("detail", detail_local);
        startActivity(data_rincian);
    }
}
