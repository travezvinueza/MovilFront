package com.ricardo.front.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.front.AdminActivity;
import com.ricardo.front.HomeActivity;
import com.ricardo.front.R;
import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder>{
//    private final UsuarioViewModel usuarioViewModel;

    Context context;
    List<Usuario> usuariosList;
//    Activity activity;

    public AdaptadorUsuarios(Context context, List<Usuario> usuariosList) {
        this.context = context;
//        this.activity = activity;
        this.usuariosList = usuariosList;
//        this.usuarioViewModel = usuarioViewModel;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuarios.UsuarioViewHolder holder, int position) {
        Usuario usuario=usuariosList.get(position);
        holder.tvId.setText(String.valueOf(usuario.getId()));
        holder.tv1.setText(usuario.getRole());
        holder.tv2.setText(usuario.getEmail());
        holder.tv3.setText(usuario.getContrasena());
        holder.tv4.setText(String.valueOf(usuario.isVigencia()));

        if (usuario.isVigencia()) {
            holder.btnToggleVigencia.setImageResource(R.drawable.ic_unlocked);
        } else {
            holder.btnToggleVigencia.setImageResource(R.drawable.ic_locked);
        }

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context , UsuarioDetalleActivity.class)
//                        .putExtra("Id", usuariosList.get(position).getId())
//                        .putExtra("Role" , usuariosList.get(position).getRole())
//                        .putExtra("Correo"), usuariosList.get(position).getEmail()
//                        .putExtra("Contrasena", usuariosList.get(position).getContrasena())
//                        .putExtra("Vigencia", usuariosList.get(position).isVigencia()));
//
//            }
//        });
//
//        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(activity, EditUserActivity.class);
//                i.putExtra("id_pet", id);
//                activity.startActivity(i);
//            }
//        });
//
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long idUsuario = usuario.getId();
               ((AdminActivity) context).eliminarUsuario(idUsuario);
            }
        });

        holder.btnToggleVigencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la posición actual
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Usuario usuarioActual = usuariosList.get(currentPosition);
                    boolean nuevaVigencia = !usuarioActual.isVigencia();

                    // Actualizar el estado de vigencia en el ViewModel
                    ((AdminActivity) context).toggleUsuarioVigencia(usuarioActual.getId(), nuevaVigencia);

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tv1 , tv2, tv3, tv4;
        ImageView btnEliminar, btnEditar, btnToggleVigencia;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnToggleVigencia = itemView.findViewById(R.id.btnToggleVigencia);

        }
    }

    public void filtrar(ArrayList<Usuario> filtroUsuarios) {
        this.usuariosList = filtroUsuarios;
        notifyDataSetChanged();
    }
}
