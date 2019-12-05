package com.example.onthick;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqlLiteDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "SinhVien";
    private static final String ID = "id";
    private static final String NAME = "name";

    public SqlLiteDB(Context context) {
        super(context, "SinhVienDB", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME +" ("+ ID +" int primary key, " + NAME+ " nvarchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void insert(SinhVien sv){
        ContentValues ct = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        ct.put(ID,sv.getId());
        ct.put(NAME,sv.getTen());
        db.insert(TABLE_NAME,null,ct);
        db.close();
    }

    public SinhVien getSinhVienById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] { ID,
                        NAME}, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SinhVien hs = new SinhVien(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        cursor.close();
        db.close();
        return hs;
    }

    public int editSinhVien(SinhVien hs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME,hs.getTen());

        return db.update(TABLE_NAME,values,ID +"=?",new String[] { String.valueOf(hs.getId()+ "")});
    }

    public ArrayList<SinhVien> getAllSinhVien() {
        ArrayList<SinhVien> listSinhVien = new ArrayList<SinhVien>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SinhVien student = new SinhVien();
                student.setId(cursor.getInt(0));
                student.setTen(cursor.getString(1));
                listSinhVien.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listSinhVien;
    }

    public void deleteSinhVien(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public int getHocSinhCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
