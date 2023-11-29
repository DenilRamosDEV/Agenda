package com.codydev.agendasqlite;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codydev.agendasqlite.db.DbContactos;
import com.codydev.agendasqlite.entidades.Contactos;

public class EditarActivity extends AppCompatActivity {

    EditText etNombre, etTelefono, etCorreo;
    Button btnGuardar, btnEditar, btnEliminar;

    boolean correcto= false;

    Contactos contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);

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
        DbContactos dbContactos = new DbContactos(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null){
            etNombre.setText(contacto.getNombre());
            etTelefono.setText(contacto.getTelefono());
            etCorreo.setText(contacto.getCorreo());

            btnEditar.setVisibility(View.INVISIBLE);
            btnEliminar.setVisibility(View.INVISIBLE);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNombre.getText().toString().equals("") && !etTelefono.getText().toString().equals("")){
                    correcto= dbContactos.editarContacto(id, etNombre.getText().toString(),etTelefono.getText().toString(), etCorreo.getText().toString());
                    if (correcto){
                        Toast.makeText(EditarActivity.this,"Contacto modificado", Toast.LENGTH_SHORT).show();
                        verRegistro();
                    }else {
                        Toast.makeText(EditarActivity.this,"Error al modificar contacto", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(EditarActivity.this,"Rellena todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verRegistro(){
        Intent intent = new Intent(this,VerActivity.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }
}
