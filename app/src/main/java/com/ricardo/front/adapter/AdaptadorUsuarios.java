package com.ricardo.front.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.front.AdminActivity;
import com.ricardo.front.EditUserActivity;
import com.ricardo.front.R;
import com.ricardo.front.entity.service.Usuario;

import java.time.format.DateTimeFormatter;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorUsuarios.UsuarioViewHolder holder, int position) {
        Usuario usuario=usuariosList.get(position);
        holder.tvId.setText(String.valueOf(usuario.getId()));
        holder.tv0.setText(usuario.getUsername());
        holder.tv1.setText(usuario.getRole());
        holder.tv2.setText(usuario.getEmail());
        holder.tv3.setText(usuario.getContrasena());
        holder.tv4.setText(String.valueOf(usuario.isVigencia()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (usuario.getFecha() != null) {
            holder.tvFecha.setText(usuario.getFecha().format(formatter));
        } else {
            holder.tvFecha.setText("Fecha no disponible");
        }

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
//                        .putExtra("Correo"), usuariosList.get(position).getEmail();
//            }
//        });

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditUserActivity.class);
                intent.putExtra("Id", usuario.getId());
                intent.putExtra("Role", usuario.getRole());
                intent.putExtra("Username", usuario.getUsername());
                intent.putExtra("Correo", usuario.getEmail());
                intent.putExtra("Contrasena", usuario.getContrasena());
                intent.putExtra("Vigencia", usuario.isVigencia());
                intent.putExtra("Nombres", usuario.getUsuarioClienteDTO().getNombres());
                intent.putExtra("Apellidos", usuario.getUsuarioClienteDTO().getApellidos());
                intent.putExtra("Telefono", usuario.getUsuarioClienteDTO().getTelefono());
                intent.putExtra("TipoDoc", usuario.getUsuarioClienteDTO().getTipoDoc());
                intent.putExtra("NumDoc", usuario.getUsuarioClienteDTO().getNumDoc());
                intent.putExtra("Direccion", usuario.getUsuarioClienteDTO().getDireccion());
                intent.putExtra("Provincia", usuario.getUsuarioClienteDTO().getProvincia());
                intent.putExtra("Capital", usuario.getUsuarioClienteDTO().getCapital());
                intent.putExtra("Fecha", usuario.getUsuarioClienteDTO().getFecha().toString());
                context.startActivity(intent);
            }
        });

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
        TextView tvId, tv0, tv1 , tv2, tv3, tv4, tvFecha;
        ImageView btnEliminar, btnEditar, btnToggleVigencia;
        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tv0 = itemView.findViewById(R.id.tv0);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tvFecha = itemView.findViewById(R.id.tvFecha);
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
