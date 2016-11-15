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
import android.widget.ListView;

import java.util.ArrayList;

public class Busqueda extends Activity {
    private ArrayList<String> resultados = new ArrayList<String>();
    private ListView lv;
    String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busqueda);

        lv = (ListView) findViewById(R.id.lvresultados);

        Bundle bundle = getIntent().getExtras();
        nombre = bundle.getString("nombre");

        buscando();
        mostrarResultados();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                buscar((String) parent.getItemAtPosition(posicion));
            }
        });
    }

    private void buscando() {
        try {
            AdminSQLiteOpenHelper admin=new AdminSQLiteOpenHelper(this, "agenda", null, 1);
            SQLiteDatabase bd=admin.getWritableDatabase();
            Cursor fila = bd.rawQuery("select nombre from contactos where nombre like '"+nombre+"%' order by nombre asc", null);
            if (fila.moveToFirst()){
                do {
                    addToLista(fila);
                } while (fila.moveToNext());
            }
            bd.close();
        }catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "NO se ha podido acceder a la base de datos");
        }
    }

    private void addToLista(Cursor fila) {
        resultados.add(fila.getString(0));
    }

    public void mostrarResultados(){
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultados);
        lv.setAdapter(adaptador);
    }

    public void cancelar(View v){
        Intent u= new Intent(this, Agenda.class);
        startActivity(u);
        finish();
    }

    private void buscar(String dato){
        Intent u = new Intent(this, Detalles.class);
        u.putExtra("nombre", dato);
        startActivity(u);
        finish();
    }

}
