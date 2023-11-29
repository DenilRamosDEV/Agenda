package com.codydev.agendasqlite.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codydev.agendasqlite.MainActivity;
import com.codydev.agendasqlite.NewActivity;
import com.codydev.agendasqlite.R;
import com.codydev.agendasqlite.VerActivity;
import com.codydev.agendasqlite.entidades.Contactos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaContactoAdapter extends RecyclerView.Adapter<ListaContactoAdapter.ContactoViewHolder> {
    ArrayList<Contactos> listaContactos;
    ArrayList<Contactos> listaOriginal;

    public ListaContactoAdapter(ArrayList<Contactos> listaContactos){
        this.listaContactos= listaContactos;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaContactos);
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_contacto, null, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        holder.tvNombre.setText(listaContactos.get(position).getNombre());
        holder.tvTelefono.setText(listaContactos.get(position).getTelefono());
        holder.tvCorreo.setText(listaContactos.get(position).getCorreo());
    }

    public void filtrado(String txtBuscar){
        int longitud= txtBuscar.length();
        if (longitud == 0){
            listaContactos.clear();
            listaContactos.addAll(listaOriginal);
        }else{
            List<Contactos> coleccion = listaContactos.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                    .collect(Collectors.toList());
            listaContactos.clear();
            listaContactos.addAll(coleccion);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTelefono, tvCorreo;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre=itemView.findViewById(R.id.tvNombre);
            tvTelefono=itemView.findViewById(R.id.tvTelefono);
            tvCorreo=itemView.findViewById(R.id.tvCorreo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent=new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaContactos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
