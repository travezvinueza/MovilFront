package com.ricardo.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricardo.front.adapter.AdapterUsuarios;
import com.ricardo.front.databinding.ActivityAdminBinding;
import com.ricardo.front.model.UsuarioClienteDTO;
import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.model.UsuarioDTO;
import com.ricardo.front.util.LocalDateSerializer;
import com.ricardo.front.util.LocalTimeSerializer;
import com.ricardo.front.viewmodel.SharedViewModel;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private UsuarioViewModel usuarioViewModel;
    private SharedViewModel sharedViewModel;
    private List<UsuarioDTO> usuariosList;
    private RecyclerView rvLista;
    AdapterUsuarios adaptador;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText etBuscador;
    ActionBarDrawerToggle toggle;

    private Handler handler;
    private Runnable runnable;
//    private WebSocket webSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        setupRecyclerView();
        setupToolbar();
        setupNavigationDrawer();
        setupViewModels();
        setupListeners();
        this.Quieto();
//        connectWebSocket();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startAutoRefresh();

    }

    private void initViews() {
        sharedPreferences = getSharedPreferences("LoginFile" , MODE_PRIVATE);
        editor = sharedPreferences.edit();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        etBuscador = findViewById(R.id.etBuscador);
        rvLista = findViewById(R.id.rvLista);
    }

    private void setupRecyclerView() {
        usuariosList = new ArrayList<>();
        adaptador = new AdapterUsuarios(this, usuariosList, getSupportFragmentManager());
        rvLista.setLayoutManager(new GridLayoutManager(this, 1));
        rvLista.setAdapter(adaptador);
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void setupNavigationDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupViewModels(){

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Observa la lista de usuarios
        usuarioViewModel.getUsuariosLista().observe(this, new Observer<GenericResponse<List<UsuarioDTO>>>() {
            @Override
            public void onChanged(GenericResponse<List<UsuarioDTO>> response) {
                if (response != null && response.getBody() != null) {
                    Log.d("AdminActivity", "Usuarios obtenidos: " + response.getBody().size());
//                    usuariosList.clear();
//                    usuariosList.addAll(response.getBody());
                    adaptador.setUsuariosList(response.getBody());
                    adaptador.notifyDataSetChanged();
                } else {
                    Log.d("AdminActivity", "No se obtuvieron usuarios");
                }
            }
        });

        // Observa los cambios en el estado de creación de usuario
        sharedViewModel.getUserCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userCreated) {
                if (userCreated != null && userCreated) {
                    usuarioViewModel.refreshUsuarios();
                    sharedViewModel.setUserCreated(false); // Reinicia la bandera
                }
            }
        });
    }

    private void startAutoRefresh() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                usuarioViewModel.refreshUsuarios();
                handler.postDelayed(this, 1000); // Refrescar cada 1 segundos
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }

//    private void connectWebSocket() {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("ws://192.168.200.7:8080/ws/users").build();
//        WebSocketClient listener = new WebSocketClient(usuarioViewModel);
//        webSocket = client.newWebSocket(request, listener);
//        client.dispatcher().executorService().shutdown();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (webSocket != null) {
//            webSocket.close(1000, null); // Cierra la conexión WebSocket al destruir la actividad
//        }
//    }

    public void setupListeners(){

        binding.btnAddFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUserFragment fm = new CreateUserFragment();
                fm.show(getSupportFragmentManager(), "fracmento abierto");
            }
        });

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

        navigationView.setNavigationItemSelectedListener(new  NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mUser:
                        Toast.makeText(AdminActivity.this, "CrearUsuario", Toast.LENGTH_SHORT).show();
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

    // Método para filtrar los usuarios por nombre
    public void filtrar(String texto) {
        ArrayList<UsuarioDTO> filtrarLista = new ArrayList<>();

        for(UsuarioDTO usuarioDTO : usuariosList) {
            if(usuarioDTO.getUsername().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuarioDTO);
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
                                    adaptador.setUsuariosList(usuariosList);
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
        usuarioViewModel.toggleVigencia(idUsuario, nuevaVigencia).observe(this, new Observer<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onChanged(GenericResponse<UsuarioDTO> response) {
                if (response != null && response.getBody() != null) {
                    // Actualizar la lista de usuarios en adaptador
                    for (UsuarioDTO usuarioDTO : usuariosList) {
                        if (usuarioDTO.getId() == idUsuario) {
                            usuarioDTO.setVigencia(nuevaVigencia);
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
                .registerTypeAdapter(Date.class, new LocalDateSerializer())
                .registerTypeAdapter(Time.class, new LocalTimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);
        if(usuarioJson != null){
            UsuarioDTO usuarioDTO = g.fromJson(usuarioJson, UsuarioDTO.class);
            View headerView = navigationView.getHeaderView(0);
            TextView tvCorreo = headerView.findViewById(R.id.tvCorreo);
            TextView tvRole = headerView.findViewById(R.id.tvRole);
            TextView tvUsername = headerView.findViewById(R.id.tvUsername);

            TextView tvName = headerView.findViewById(R.id.tvName);
            TextView tvLastName = headerView.findViewById(R.id.tvLastName);

            if (usuarioDTO != null && usuarioDTO.getUsuarioClienteDto() != null) {
                UsuarioClienteDTO usuarioClienteDTO = usuarioDTO.getUsuarioClienteDto();

                tvName.setText(usuarioClienteDTO.getNombres());
                tvLastName.setText(usuarioClienteDTO.getApellidos());

                tvCorreo.setText(usuarioDTO.getEmail());
                tvRole.setText(usuarioDTO.getRole());
                tvUsername.setText(usuarioDTO.getUsername());

            } else {

                tvName.setText("vacios");
                tvLastName.setText("vacios");
            }
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