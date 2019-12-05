package com.example.onthick;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtID,edtten;
    ListView lsvSinhVien;
    CustomAdapter customAdapter;
    public static MainActivity mainChinh;
    private ServiceConnection serviceConnection;
    private boolean isConnected = false;
    private MyService myService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtID = findViewById(R.id.edtID);
        edtten = findViewById(R.id.edtTen);
        lsvSinhVien = findViewById(R.id.lsvSinhVien);
        mainChinh = this;

        SqlLiteDB db = new SqlLiteDB(this);
        try {
            db.insert(new SinhVien(1,"Viet"));
            db.insert(new SinhVien(2,"Nam Anh"));
            db.insert(new SinhVien(3,"Tài"));
            db.insert(new SinhVien(4,"Huy"));
            db.insert(new SinhVien(5,"Kiệt"));
            db.insert(new SinhVien(6,"Hoàng"));
        }catch (Exception t){

        }
        customAdapter = new CustomAdapter(this,R.layout.customlistitems,db);
        lsvSinhVien.setAdapter(customAdapter);
        connectionService();
        lsvSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtID.setText(customAdapter.getSinhVien(i).getId() + "");
                edtten.setText(customAdapter.getSinhVien(i).getTen());
            }
        });

        lsvSinhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                myService.hienThiSinhVien(customAdapter.getSinhVien(i));
                return false;
            }
        });
    }

    private void connectionService() {
        Intent intent = new Intent(this,MyService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                MyService.myBinder myBinder = (MyService.myBinder) iBinder;
                myService = myBinder.getService();
                isConnected = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                isConnected = false;
                myService = null;
            }
        };
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    public void them(View view) {
        customAdapter.themSinhVien(new SinhVien(Integer.parseInt(edtID.getText().toString()),edtten.getText().toString()));
    }

    public void sua(View view) {
        if(edtten.getText().toString().trim().length()==0 ||edtID.getText().toString().trim().length()==0){
                return;
        }
        customAdapter.suaSinhVien(new SinhVien(Integer.parseInt(edtID.getText().toString()),edtten.getText().toString()));
    }

    public void xoa(View view) {
        AlertDialog.Builder d = new AlertDialog.Builder(this);
        d.setTitle("Hello").setMessage("Bạn có muốn xoá ko dmm?").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("OK dmm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                customAdapter.xoaSinhVien(Integer.parseInt(edtID.getText().toString()));
            }
        }).setNeutralButton("Con cac", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Dmm", Toast.LENGTH_SHORT).show();
            }
        });
        d.show();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
