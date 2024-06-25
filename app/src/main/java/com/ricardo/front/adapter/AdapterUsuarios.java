package com.ricardo.front.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.front.AdminActivity;
import com.ricardo.front.EditUserActivity;
import com.ricardo.front.R;
import com.ricardo.front.model.UsuarioClienteDTO;
import com.ricardo.front.model.UsuarioDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.UsuarioViewHolder> {
    Context context;
    List<UsuarioDTO> usuariosList;
    FragmentManager fm;

    public AdapterUsuarios(Context context, List<UsuarioDTO> usuariosList, FragmentManager fm) {
        this.context = context;
        this.usuariosList = usuariosList;
        this.fm = fm;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUsuarios.UsuarioViewHolder holder, int position) {
        UsuarioDTO usuarioDTO = usuariosList.get(position);
        holder.tvId.setText(String.valueOf(usuarioDTO.getId()));
        holder.tv0.setText(usuarioDTO.getUsername());
        holder.tv1.setText(usuarioDTO.getRole());
        holder.tv2.setText(usuarioDTO.getEmail());
//        holder.tv3.setText(usuarioDTO.getContrasena());
        holder.tv4.setText(String.valueOf(usuarioDTO.isVigencia()));


        if (usuarioDTO.isVigencia()) {
            holder.btnToggleVigencia.setImageResource(R.drawable.ic_unlocked);
        } else {
            holder.btnToggleVigencia.setImageResource(R.drawable.ic_locked);
        }

        // Obtener y asignar datos de UsuarioClienteDTO
        UsuarioClienteDTO cliente = usuarioDTO.getUsuarioClienteDto();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (cliente != null) {
            holder.tvDireccion.setText(cliente.getDireccion() != null ? cliente.getDireccion() : "sin datos");
            holder.tvTelefono.setText(cliente.getTelefono() != null ? cliente.getTelefono() : "sin datos");
            holder.tvFecha.setText(cliente.getFecha().format(formatter) != null ? cliente.getFecha().format(formatter) : "sin datos");

        } else {
            holder.tvDireccion.setText("sin datos");
            holder.tvTelefono.setText("sin datos");
            holder.tvFecha.setText("sin datos");

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    UsuarioDTO currentUsuario = usuariosList.get(currentPosition);
                    Intent intent = new Intent(context, EditUserActivity.class)
                            .putExtra("id", currentUsuario.getId())
                            .putExtra("username", currentUsuario.getUsername())
                            .putExtra("email", currentUsuario.getEmail())
                            .putExtra("contrasena", currentUsuario.getContrasena())
                            .putExtra("vigencia", currentUsuario.isVigencia())
                            .putExtra("role", currentUsuario.getRole())
                            .putExtra("clienteId", currentUsuario.getClienteId());

                    UsuarioClienteDTO currentCliente = currentUsuario.getUsuarioClienteDto();
                    if (currentCliente != null) {
                        intent.putExtra("nombres", currentCliente.getNombres())
                                .putExtra("apellidos", currentCliente.getApellidos())
                                .putExtra("tipoDoc", currentCliente.getTipoDoc())
                                .putExtra("numDoc", currentCliente.getNumDoc())
                                .putExtra("direccion", currentCliente.getDireccion())
                                .putExtra("provincia", currentCliente.getProvincia())
                                .putExtra("capital", currentCliente.getCapital())
                                .putExtra("fecha", currentCliente.getFecha());
                    }
                    context.startActivity(intent);
                }
            }
        });


//        holder.btnAgregar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CreateUserFragment createUserFragment = new CreateUserFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("id", id);
//                createUserFragment.setArguments(bundle);
//                createUserFragment.show(fm, "fracmento abierto");
//            }
//        });


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

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tv0, tv1, tv2, tv3, tv4, tvFecha, tvDireccion, tvTelefono;
        ImageView btnEliminar, btnAgregar, btnToggleVigencia;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tv0 = itemView.findViewById(R.id.tv0);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
//            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
//            btnAgregar = itemView.findViewById(R.id.btnAgregar);
            btnToggleVigencia = itemView.findViewById(R.id.btnToggleVigencia);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);

        }
    }

    public void setUsuariosList(List<UsuarioDTO> usuariosList) {
        this.usuariosList = usuariosList;
        notifyDataSetChanged();
    }

    public void filtrar(ArrayList<UsuarioDTO> filtroUsuarioDTOS) {
        this.usuariosList = filtroUsuarioDTOS;
        notifyDataSetChanged();
    }
}
