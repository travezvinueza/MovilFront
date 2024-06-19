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
import com.ricardo.front.model.UsuarioClienteDTO;
import com.ricardo.front.model.UsuarioDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder>{
    Context context;
    List<UsuarioDTO> usuariosList;

    public AdaptadorUsuarios(Context context, List<UsuarioDTO> usuariosList) {
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
        UsuarioDTO usuarioDTO = usuariosList.get(position);
        holder.tvId.setText(String.valueOf(usuarioDTO.getId()));
        holder.tv0.setText(usuarioDTO.getUsername());
        holder.tv1.setText(usuarioDTO.getRole());
        holder.tv2.setText(usuarioDTO.getEmail());
        holder.tv3.setText(usuarioDTO.getContrasena());
        holder.tv4.setText(String.valueOf(usuarioDTO.isVigencia()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (usuarioDTO.getFecha() != null) {
            holder.tvFecha.setText(usuarioDTO.getFecha().format(formatter));
        } else {
            holder.tvFecha.setText("Fecha no disponible");
        }

        if (usuarioDTO.isVigencia()) {
            holder.btnToggleVigencia.setImageResource(R.drawable.ic_unlocked);
        } else {
            holder.btnToggleVigencia.setImageResource(R.drawable.ic_locked);
        }

        // Obtener y asignar datos de UsuarioClienteDTO
        UsuarioClienteDTO cliente = usuarioDTO.getUsuarioClienteDTO();
        if (cliente != null) {
            holder.tvNombres.setText(cliente.getNombres() != null ? cliente.getNombres() : "Nombres no disponible");
            holder.tvApellidos.setText(cliente.getApellidos() != null ? cliente.getApellidos() : "Apellidos no disponible");
            holder.tvTelefono.setText(cliente.getTelefono() != null ? cliente.getTelefono() : "Telefono no disponible");
            // Asignar otros campos de cliente si es necesario
        } else {
            holder.tvNombres.setText("Nombres no disponible");
            holder.tvApellidos.setText("Apellidos no disponible");
            holder.tvTelefono.setText("Telefono no disponible");
            // Manejar otros campos de cliente si son null
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
                intent.putExtra("Id", usuarioDTO.getId());
                intent.putExtra("Role", usuarioDTO.getRole());
                intent.putExtra("Username", usuarioDTO.getUsername());
                intent.putExtra("Correo", usuarioDTO.getEmail());
                intent.putExtra("Contrasena", usuarioDTO.getContrasena());
                intent.putExtra("Vigencia", usuarioDTO.isVigencia());
                intent.putExtra("Nombres", usuarioDTO.getUsuarioClienteDTO().getNombres());
                intent.putExtra("Apellidos", usuarioDTO.getUsuarioClienteDTO().getApellidos());
                intent.putExtra("Telefono", usuarioDTO.getUsuarioClienteDTO().getTelefono());
                intent.putExtra("TipoDoc", usuarioDTO.getUsuarioClienteDTO().getTipoDoc());
                intent.putExtra("NumDoc", usuarioDTO.getUsuarioClienteDTO().getNumDoc());
                intent.putExtra("Direccion", usuarioDTO.getUsuarioClienteDTO().getDireccion());
                intent.putExtra("Provincia", usuarioDTO.getUsuarioClienteDTO().getProvincia());
                intent.putExtra("Capital", usuarioDTO.getUsuarioClienteDTO().getCapital());
                intent.putExtra("Fecha", usuarioDTO.getUsuarioClienteDTO().getFecha().toString());
                context.startActivity(intent);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long idUsuario = usuarioDTO.getId();
               ((AdminActivity) context).eliminarUsuario(idUsuario);
            }
        });

        holder.btnToggleVigencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la posición actual
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    UsuarioDTO usuarioDTOActual = usuariosList.get(currentPosition);
                    boolean nuevaVigencia = !usuarioDTOActual.isVigencia();

                    // Actualizar el estado de vigencia en el ViewModel
                    ((AdminActivity) context).toggleUsuarioVigencia(usuarioDTOActual.getId(), nuevaVigencia);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder{
        TextView tvId, tv0, tv1 , tv2, tv3, tv4, tvFecha,tvNombres, tvApellidos, tvTelefono;
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
            tvNombres = itemView.findViewById(R.id.tvNombres);
            tvApellidos = itemView.findViewById(R.id.tvApellidos);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);

        }
    }

    public void filtrar(ArrayList<UsuarioDTO> filtroUsuarioDTOS) {
        this.usuariosList = filtroUsuarioDTOS;
        notifyDataSetChanged();
    }
}
