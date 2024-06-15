package com.ricardo.front;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.ricardo.front.adapter.AdaptadorUsuarios;
import com.ricardo.front.entity.GenericResponse;
import com.ricardo.front.entity.service.Usuario;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private UsuarioViewModel usuarioViewModel;
    private Toolbar toolbar;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    List<Usuario> usuariosList;
    RecyclerView rvLista;
    AdaptadorUsuarios adaptador;
    EditText etBuscador;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        this.init();

        // Inicializar ViewModel
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        // Observa la lista de usuarios
        usuarioViewModel.getUsuariosLista().observe(this, new Observer<GenericResponse<List<Usuario>>>() {
            @Override
            public void onChanged(GenericResponse<List<Usuario>> response) {
                if (response != null && response.getBody() != null) {
                    usuariosList.clear();
                    usuariosList.addAll(response.getBody());
                    adaptador.notifyDataSetChanged();
                }
            }
        });

        usuarioViewModel.getUsuariosLista();

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
        navigationView = findViewById(R.id.nav_View);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        adaptador = new AdaptadorUsuarios(HomeActivity.this, usuariosList);
        rvLista.setAdapter(adaptador);


        // Drawer item Click event ------
        navigationView.setNavigationItemSelectedListener(new  NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.mUser:
                        Toast.makeText(HomeActivity.this, "Crear Usuario", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent user = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(user);
                        break;

                    case R.id.mRoles:
                        Toast.makeText(HomeActivity.this, "Roles", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        Intent role = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(role);
                        break;

                    case R.id.mSalir:
                        editor.putString("isLoggedIn", "false");
                        editor.commit();
                        Toast.makeText(HomeActivity.this, "Cerrando la aplicación", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this , MainActivity.class));
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
            if(usuario.getEmail().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
        }
        adaptador.filtrar(filtrarLista);
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