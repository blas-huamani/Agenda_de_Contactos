package com.example.lenovo.agenda;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Nuevo extends Activity {
    private EditText nom, ape, tel, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo);

        nom = (EditText)findViewById(R.id.nombres);
        ape = (EditText)findViewById(R.id.apellidos);
        tel = (EditText)findViewById(R.id.telefono);
        email = (EditText)findViewById(R.id.email);
    }

    public  void guardar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String nombres = nom.getText().toString();
        String apellidos = ape.getText().toString();
        String telefono = tel.getText().toString();
        String correo = email.getText().toString();

        Cursor fila = bd.rawQuery("select nombre, apellidos, telefono, email from contactos where nombre='" + nombres + "'", null);
        if (fila.moveToFirst()){
            Toast.makeText(this, "Ya existe un registro con el nombre: ", Toast.LENGTH_SHORT).show();
        }else {
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombres);
            registro.put("apellidos", apellidos);
            registro.put("telefono", telefono);
            registro.put("email", correo);

            bd.insert("contactos", null, registro);
            bd.close();

            Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_SHORT).show();
            refresh();
        }
    }

    private void refresh() {
        finish();
        Intent myIntent = new Intent(this, Agenda.class);
        startActivity(myIntent);
    }

    public void finalizar(View v){
        refresh();
    }

}
