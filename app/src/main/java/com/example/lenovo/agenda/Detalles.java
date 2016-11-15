package com.example.lenovo.agenda;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Detalles extends Activity {

    private TextView et1;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles);

        et1=(TextView) findViewById(R.id.detalles);
        Bundle bundle=getIntent().getExtras();
        nombre = bundle.getString("nombre");

        buscar_nombre(nombre);
    }

    public void buscar_nombre(String nombre){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase bd=admin.getWritableDatabase();

        Cursor fila=bd.rawQuery("select nombre, apellidos, telefono, email from contactos where nombre='" + nombre + "'", null);
        if(fila.moveToFirst()){
            et1.setText("Nombre: "+fila.getString(0)+"\n"
                    +"Apellidos: "+fila.getString(1)+"\n"
                    +"Telefono: "+fila.getString(2)+"\n"
                    +"Correo: "+fila.getString(3)
            );
        }else{
            Toast.makeText(this, "No existe contacto con ese nombre", Toast.LENGTH_SHORT).show();
            bd.close();
        }
    }

    public void volver(View v){
        refresh();
    }

    public void modificar(View v){
        Intent u= new Intent(this, Modificar.class);
        u.putExtra("nombre", nombre);
        startActivity(u);
        finish();
    }

    private void refresh() {
        finish();
        Intent myIntent = new Intent(this, Agenda.class);
        startActivity(myIntent);
    }

    public void eliminar(View v){
        AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        int cant=bd.delete("contactos", "nombre='"+nombre+"'", null);
        bd.close();

        Toast.makeText(this, "Se borró la persona con dicho documento", Toast.LENGTH_SHORT).show();
        refresh();
    }
}
