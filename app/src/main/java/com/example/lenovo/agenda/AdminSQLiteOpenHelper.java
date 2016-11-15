package com.example.lenovo.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        
        super(context, name, factory, version);
    }

    
    
    @Override
    public void onCreate(SQLiteDatabase db) {
      
      db.execSQL("create table contactos(nombre text, apellidos text, telefono text, email text)");
    }

    
    
    @Override  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
        db.execSQL("drop table if exists contactos");
        
        db.execSQL("create table contactos(nombre text, apellidos text, telefono text, email text)");
    }
}

