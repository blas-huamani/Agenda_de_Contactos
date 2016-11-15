package com.example.lenovo.agenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



public class Modificar extends ActionBarActivity {

    
    private EditText nom, ape, tel, email;
    String nombre1;

    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.modificar);

        
        nom = (EditText)findViewById(R.id.e_nombres);
        
        ape = (EditText)findViewById(R.id.e_apellidos);
        
        tel = (EditText)findViewById(R.id.e_telefono);
        
        email = (EditText)findViewById(R.id.e_correo);

        
        Bundle bundle = getIntent().getExtras();
        
        nombre1 = bundle.getString("nombre");

        
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery("select nombre, apellidos, telefono, email from contactos where nombre='" + nombre1 + "'", null);
        
        
        if (fila.moveToFirst()){
            
            nom.setText(fila.getString(0));
            
            ape.setText(fila.getString(1));
            
            tel.setText(fila.getString(2))
                
            email.setText(fila.getString(3));
        }else{
            
            Toast.makeText(this, "No existe contacto con ese nombre", Toast.LENGTH_SHORT).show();
            bd.close();
        }
    }

    
    
    public void gurdar_cambios(View v){
        
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
        
        SQLiteDatabase bd = admin.getWritableDatabase();

        String nombres = nom.getText().toString();
        
        String apellidos = ape.getText().toString();
        
        String telefono = tel.getText().toString();
        
        String correo = email.getText().toString();

        
        ContentValues registros = new ContentValues();

        
        registros.put("nombre", nombres );
        registros.put("apellidos", apellidos );
        registros.put("telefono", telefono );
        registros.put("email", correo );

        
        bd.update("contactos", registros, "nombre ='" + nombre1 + "'", null);
        
        Toast.makeText(this, "Se Modificó correctamente", Toast.LENGTH_SHORT).show();
        
        nombre1 = nombres;
        
        refresh();
    }

    
    
    private void refresh() {
        
        finish();
        
        Intent myIntent = new Intent(this, Detalles.class);
        
        myIntent.putExtra("nombre", nombre1);
        startActivity(myIntent);
    }

    
    
    public void cancelar(View v){
        
        refresh();
    }

}
