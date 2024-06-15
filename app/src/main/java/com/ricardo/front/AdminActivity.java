package com.ricardo.front;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ricardo.front.adapter.AdaptadorUsuarios;
import com.ricardo.front.databinding.ActivityAdminBinding;
import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private List<Usuario> usuariosList;
    private RecyclerView rvLista;
    private AdaptadorUsuarios adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar el Toolbar
        setSupportActionBar(binding.toolbar);

        rvLista = findViewById(R.id.rvLista2);
        rvLista.setLayoutManager(new LinearLayoutManager(this));

        usuariosList = new ArrayList<>();

        adaptador = new AdaptadorUsuarios(this, usuariosList);
        rvLista.setAdapter(adaptador);

        // Inicializar ViewModel
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        // Observa la lista de usuarios
        usuarioViewModel.getUsuariosLista().observe(this, new Observer<GenericResponse<List<Usuario>>>() {
            @Override
            public void onChanged(GenericResponse<List<Usuario>> response) {
                if (response != null && response.getBody() != null) {
                    Log.d("AdminActivity", "Usuarios obtenidos: " + response.getBody().size());
                    usuariosList.clear();
                    usuariosList.addAll(response.getBody());
                    adaptador.notifyDataSetChanged();
                } else {
                    Log.d("AdminActivity", "No se obtuvieron usuarios");
                }
            }
        });

        // Llamar al método para obtener la lista de usuarios
        usuarioViewModel.getUsuariosLista();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    // Método para eliminar un usuario por su ID
    public void eliminarUsuario(long idUsuario) {
        new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("¿Estás seguro?")
                .setContentText("Esta acción no se puede deshacer")
                .setConfirmText("Sí")
                .setConfirmClickListener(sDialog -> {
                    // Proceder con la eliminación
                    usuarioViewModel.eliminarUsuario(idUsuario).observe(AdminActivity.this, response -> {
                        if (response != null && response.getRpta() == 1) {
                            Toast.makeText(AdminActivity.this, (response.getMessage()), Toast.LENGTH_SHORT).show();
//                            new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                    .setTitleText(response.getMessage())
//                                    .setConfirmClickListener(SweetAlertDialog::dismissWithAnimation)
//                                    .show();

                            // Actualizar la lista de usuarios y notificar al adaptador
                            usuarioViewModel.getUsuariosLista().observe(AdminActivity.this, listaResponse -> {
                                if (listaResponse != null && listaResponse.getBody() != null) {
                                    usuariosList.clear();
                                    usuariosList.addAll(listaResponse.getBody());
                                    adaptador.notifyDataSetChanged();
                                }
                            });
                        } else {
                            new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error")
                                    .setContentText(response != null ? response.getMessage() : "Error al eliminar usuario")
                                    .show();
                        }
                    });
                    sDialog.dismissWithAnimation();
                })
                .setCancelText("No")
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .show();
    }


    // Método para cambiar la vigencia de un usuario por su ID
    public void toggleUsuarioVigencia(long idUsuario, boolean nuevaVigencia) {
        usuarioViewModel.toggleVigencia(idUsuario, nuevaVigencia).observe(this, new Observer<GenericResponse<Usuario>>() {
            @Override
            public void onChanged(GenericResponse<Usuario> response) {
                if (response != null && response.getBody() != null) {
                    // Actualizar la lista de usuarios en adaptador
                    for (Usuario usuario : usuariosList) {
                        if (usuario.getId() == idUsuario) {
                            usuario.setVigencia(nuevaVigencia);
                            adaptador.notifyDataSetChanged();
                            break;
                        }
                    }
//                    usuarioViewModel.getUsuariosLista();
                    new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText(response.getMessage()).show();
                } else {
                    // Mostrar mensaje de error si falla el cambio de vigencia
                    Toast.makeText(AdminActivity.this, "Error al cambiar vigencia del usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mCerrar:
                this.logout();
                break;
            case R.id.mPerfil:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para cerrar sesión
    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }


}