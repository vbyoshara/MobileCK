package com.example.onthick;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new myBinder();
    }

    public class myBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    public void hienThiSinhVien(SinhVien sv){
        Toast.makeText(MainActivity.mainChinh, sv.toString(), Toast.LENGTH_SHORT).show();
    }
}
