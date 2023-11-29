package com.codydev.agendasqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.codydev.agendasqlite.adaptadores.ListaContactoAdapter;
import com.codydev.agendasqlite.db.DbContactos;
import com.codydev.agendasqlite.db.DbHelper;
import com.codydev.agendasqlite.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    RecyclerView listaContactos;
    SearchView svBuscar;
    FloatingActionButton faNuevo;
    ArrayList<Contactos> listaArrayContactos;
    ListaContactoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaContactos = findViewById(R.id.listaContactos);
        svBuscar = findViewById(R.id.svBuscar);
        faNuevo = findViewById(R.id.faNuevo);

        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        DbContactos dbContactos = new DbContactos(MainActivity.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListaContactoAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        faNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nuevoRegistro();
            }
        });

        svBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == getResources().getIdentifier("menu_nuevo", "id", getPackageName())) {
            nuevoRegistro();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nuevoRegistro() {
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}