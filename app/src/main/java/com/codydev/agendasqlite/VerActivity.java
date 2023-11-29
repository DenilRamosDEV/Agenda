package com.codydev.agendasqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codydev.agendasqlite.db.DbContactos;
import com.codydev.agendasqlite.entidades.Contactos;

public class VerActivity extends AppCompatActivity {

    EditText etNombre, etTelefono, etCorreo;
    Button btnGuardar, btnEditar, btnEliminar;

    Contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar= findViewById(R.id.btnGuardar);

        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }
        DbContactos dbContactos = new DbContactos(VerActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null){
            etNombre.setText(contacto.getNombre());
            etTelefono.setText(contacto.getTelefono());
            etCorreo.setText(contacto.getCorreo());

            btnGuardar.setVisibility(View.INVISIBLE);
            etNombre.setInputType(InputType.TYPE_NULL);
            etTelefono.setInputType(InputType.TYPE_NULL);
            etCorreo.setInputType(InputType.TYPE_NULL);
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(VerActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(dbContactos.eliminarContacto(id)){
                                    Toast.makeText(VerActivity.this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
                                    eliminar();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }
    private void eliminar(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}