package com.codydev.agendasqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.codydev.agendasqlite.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {
    Context context;

    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long inseetarContacto(String nombre, String telefono, String coreo_electronico) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("coreo_electronico", coreo_electronico);

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception e) {
            e.toString();

        }
        return id;
    }

    public ArrayList<Contactos> mostrarContactos() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContacto = null;

        cursorContacto = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);

        if (cursorContacto.moveToFirst()) {
            do {
                contacto = new Contactos();
                contacto.setId(cursorContacto.getInt(0));
                contacto.setNombre(cursorContacto.getString(1));
                contacto.setTelefono(cursorContacto.getString(2));
                contacto.setCorreo(cursorContacto.getString(3));

                listaContactos.add(contacto);
            } while (cursorContacto.moveToNext());
        }
        cursorContacto.close();
        return listaContactos;
    }

    public Contactos verContacto(int id) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContacto;

        cursorContacto = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContacto.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContacto.getInt(0));
            contacto.setNombre(cursorContacto.getString(1));
            contacto.setTelefono(cursorContacto.getString(2));
            contacto.setCorreo(cursorContacto.getString(3));
        }
        cursorContacto.close();
        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String telefono, String coreo_electronico) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL(" UPDATE "+ TABLE_CONTACTOS + " SET nombre = '" + nombre + " ', telefono = '" + telefono + "', coreo_electronico = '" + coreo_electronico + "' WHERE id = '" + id + "'");
            correcto= true;
        } catch (Exception e) {
            e.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }

    public boolean eliminarContacto(int id) {
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL(" DELETE FROM "+ TABLE_CONTACTOS + "  WHERE id = '" + id + "'");
            correcto= true;
        } catch (Exception e) {
            e.toString();
            correcto = false;
        }finally {
            db.close();
        }
        return correcto;
    }
}
