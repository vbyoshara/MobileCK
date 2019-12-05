package com.example.onthick;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Activity activity;
    int customList;
    ArrayList<SinhVien> lsSinhVien;
    SqlLiteDB db;

    public CustomAdapter(Activity activity, int customList, SqlLiteDB db) {
        this.activity = activity;
        this.customList = customList;
        this.lsSinhVien = db.getAllSinhVien();
        this.db = db;
    }

    public void themSinhVien(SinhVien sv){
        db.insert(sv);
        capNhatList();
    }

    public void suaSinhVien(SinhVien sv){
        db.editSinhVien(sv);
        capNhatList();
    }

    public void xoaSinhVien(int id){
        db.deleteSinhVien(id);
        capNhatList();
    }

    public SinhVien getSinhVien(int stt){
        return lsSinhVien.get(stt);
    }

    private void capNhatList(){
        this.lsSinhVien = db.getAllSinhVien();
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lsSinhVien.size();
    }

    @Override
    public Object getItem(int i) {
        return lsSinhVien.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        view = layoutInflater.inflate(customList,null);
        TextView txtId,txtName;

        txtId = view.findViewById(R.id.txtId);
        txtName = view.findViewById(R.id.txtTen);
        txtId.setText(lsSinhVien.get(i).getId() + "");
        txtName.setText(lsSinhVien.get(i).getTen());

        return view;
    }
}
