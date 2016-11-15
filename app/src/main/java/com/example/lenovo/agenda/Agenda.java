package com.example.lenovo.agenda;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Agenda extends Activity {
    private ArrayList<String> resultados = new ArrayList<String>();
    private ListView lv;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        name = (EditText) findViewById(R.id.nombre);
        lv = (ListView) findViewById(R.id.listView);

        abrirDBAdapter();
        mostrarResultados();

        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                        buscar((String) lv.getItemAtPosition(posicion));
                        //String textItem = String.valueOf(parent.getItemAtPosition(posicion));
                        //Toast.makeText(Agenda.this, textItem, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        resultados.size();
        Toast.makeText(this, String.valueOf(resultados.size()) ,Toast.LENGTH_LONG ).show();
    }

    private void abrirDBAdapter() {
        try {
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "agenda", null, 1);
            SQLiteDatabase bd = admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select nombre from contactos order by nombre asc", null);
            if (fila.moveToFirst()){
                do {
                    addToLista(fila);
                } while (fila.moveToNext());
            }

            bd.close();
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "NO se ha podido acceder a la base de datos");
        }
    }

    private void addToLista(Cursor fila) {
        resultados.add(fila.getString(0));
    }

    private void mostrarResultados() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, resultados);
        lv.setAdapter(adapter);
    }

    public void agregar(View v){
        Intent i= new Intent(this, Nuevo.class);
        startActivity(i);
        finish();
    }

    private void buscar(String dato){
        Intent u= new Intent(this, Detalles.class);
        u.putExtra("nombre", dato);
        startActivity(u);
        finish();
    }

    public void busqueda(View v){
        Intent u= new Intent(this, Busqueda.class);
        u.putExtra("nombre", name.getText().toString());
        startActivity(u);
        finish();
    }
    //el adaftador define la forma en que se van a verse los items
}
