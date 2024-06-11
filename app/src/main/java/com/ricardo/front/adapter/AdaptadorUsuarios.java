package com.ricardo.front.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.front.R;
import com.ricardo.front.entity.service.Usuario;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder>{

    Context context;
    List<Usuario> usuariosList;

    public AdaptadorUsuarios(Context context, List<Usuario> usuariosList) {
        this.context = context;
        this.usuariosList = usuariosList;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuarios.UsuarioViewHolder holder, int position) {
        Usuario usuario=usuariosList.get(position);
        holder.tv1.setText(usuario.getRole());
        holder.tv2.setText(usuario.getEmail());
        holder.tv3.setText(usuario.getContrasena());
        holder.tv4.setText(String.valueOf(usuario.isVigencia()));
        holder.tvId.setText(String.valueOf(usuario.getId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tv1 , tv2, tv3, tv4;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);

        }
    }

    public void filtrar(ArrayList<Usuario> filtroUsuarios) {
        this.usuariosList = filtroUsuarios;
        notifyDataSetChanged();
    }
}
