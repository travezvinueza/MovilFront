package com.ricardo.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricardo.front.adapter.AdaptadorUsuarios;
import com.ricardo.front.databinding.ActivityAdminBinding;
import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;
import com.ricardo.front.utils.DateSerializer;
import com.ricardo.front.utils.TimeSerializer;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private List<Usuario> usuariosList;
    private RecyclerView rvLista;
    private AdaptadorUsuarios adaptador;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText etBuscador;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.init();
        this.Quieto();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void init() {
        sharedPreferences = getSharedPreferences("LoginFile" , MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Configurar el Toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        setSupportActionBar(binding.toolbar);
        // Eliminar el título de la aplicación en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        toggle = new ActionBarDrawerToggle(AdminActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        rvLista = findViewById(R.id.rvLista);
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

        etBuscador = findViewById(R.id.etBuscador);
        etBuscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });

        rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new GridLayoutManager(this, 1));

        usuariosList = new ArrayList<>();

        adaptador = new AdaptadorUsuarios(AdminActivity.this, usuariosList);
        rvLista.setAdapter(adaptador);


        navigationView.setNavigationItemSelectedListener(new  NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mUser:
                        Toast.makeText(AdminActivity.this, "test uno", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent user = new Intent(AdminActivity.this, AdminActivity.class);
                        startActivity(user);
                        break;

                    case R.id.mRoles:
                        Toast.makeText(AdminActivity.this, "test dos", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent role = new Intent(AdminActivity.this, AdminActivity.class);
                        startActivity(role);
                        break;

                    case R.id.mSalir:
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AdminActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("UsuarioJson");
                        editor.apply();
                        Toast.makeText(AdminActivity.this, "Cerrando la aplicación", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                }
                return false;
            }
        });

    }

    public void filtrar(String texto) {
        ArrayList<Usuario> filtrarLista = new ArrayList<>();

        for(Usuario usuario : usuariosList) {
            if(usuario.getUsername().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
        }
        adaptador.filtrar(filtrarLista);
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

                    Toast.makeText(AdminActivity.this, "Error al cambiar vigencia del usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private void loadData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson g = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if(usuarioJson != null){
            final Usuario u = g.fromJson(usuarioJson, Usuario.class);
            final View vistaHeader = binding.navView.getHeaderView(0);
            final TextView tvCorreo = vistaHeader.findViewById(R.id.tvCorreo),
                           tvRole = vistaHeader.findViewById(R.id.tvRole);

            tvCorreo.setText(u.getEmail());
            tvRole.setText(u.getRole());
        }
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

    private void logout() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("UsuarioJson");
        editor.apply();
        this.finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void Quieto() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new SweetAlertDialog(AdminActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Cerrar Sesión")
                        .setContentText("¿Estás seguro de que quieres cerrar sesión?")
                        .setCancelText("No")
                        .setConfirmText("Sí")
                        .showCancelButton(true)
                        .setCancelClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                        })
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismissWithAnimation();
                            logout();
                        })
                        .show();
            }
        });
    }


}