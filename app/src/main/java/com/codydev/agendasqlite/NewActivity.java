package com.codydev.agendasqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codydev.agendasqlite.db.DbContactos;

public class NewActivity extends AppCompatActivity {
    EditText etNombre,etTelefono,etCorreo;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        etNombre= findViewById(R.id.etNombre);
        etTelefono= findViewById(R.id.etTelefono);
        etCorreo= findViewById(R.id.etCorreo);
        btnGuardar= findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContactos dbContactos = new DbContactos(NewActivity.this);
                long id= dbContactos.inseetarContacto(etNombre.getText().toString(),etTelefono.getText().toString(),etCorreo.getText().toString());
                if (id > 0){
                    Toast.makeText(NewActivity.this, "Contacto registrado", Toast.LENGTH_SHORT).show();
                    limpiar();
                }else{
                    Toast.makeText(NewActivity.this, "error al registrar contacto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private  void limpiar(){
        etNombre.setText("");
        etTelefono.setText("");
        etCorreo.setText("");
    }
}