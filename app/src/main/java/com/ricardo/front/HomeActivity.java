package com.ricardo.front;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricardo.front.model.UsuarioDTO;
import com.ricardo.front.util.DateSerializer;
import com.ricardo.front.util.TimeSerializer;

import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

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

        // Navagation Drawar------------------------------
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Eliminar el título de la aplicación en el Toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerView = findViewById(R.id.recyclerView);

        // Drawer item Click event ------
        navigationView.setNavigationItemSelectedListener(new  NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mTest1:
                        Toast.makeText(HomeActivity.this, "test uno", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent user = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(user);
                        break;

                    case R.id.mTest2:
                        Toast.makeText(HomeActivity.this, "test dos", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent role = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(role);
                        break;

                    case R.id.mSalir:
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("UsuarioJson");
                        editor.apply();
                        Toast.makeText(HomeActivity.this, "Cerrando la aplicación", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        break;
                }
                return false;
            }
        });

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

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    private void loadData() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        String usuarioJson = sp.getString("UsuarioJson", null);

        if (usuarioJson != null) {
            UsuarioDTO usuarioDTO = gson.fromJson(usuarioJson, UsuarioDTO.class);
            NavigationView navigationView = findViewById(R.id.navigationView);
            View headerView = navigationView.getHeaderView(0);
            TextView tvCorreo = headerView.findViewById(R.id.tvCorreo);
            TextView tvRole = headerView.findViewById(R.id.tvRole);
            TextView tvNombre = headerView.findViewById(R.id.tvNombre);

            tvNombre.setText(usuarioDTO.getUsuarioClienteDTO().getNombres());

            tvCorreo.setText(usuarioDTO.getEmail());
            tvRole.setText(usuarioDTO.getRole());
            tvNombre.setText(usuarioDTO.getRole());
        }
    }


    public void Quieto() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.WARNING_TYPE)
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