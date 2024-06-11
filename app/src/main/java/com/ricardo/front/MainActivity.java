package com.ricardo.front;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ricardo.front.entity.service.Usuario;
import com.ricardo.front.utils.DateSerializer;
import com.ricardo.front.utils.TimeSerializer;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private Button btnIngresar;
    private UsuarioViewModel viewModel;
    private TextInputLayout txtInputEmail, txtInputPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.init();
        this.initViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }


    private void init() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        txtInputEmail = findViewById(R.id.txtInputEmail);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(v -> {
            try {
                if (validar()) {
                    viewModel.login(editEmail.getText().toString(), editPassword.getText().toString()).observe(this, response -> {
                        if (response.getRpta() == 1) {
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE).setTitleText(response.getMessage()).show();
                            Usuario u = response.getBody();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                            SharedPreferences.Editor editor = preferences.edit();
                            final Gson g = new GsonBuilder()
                                    .registerTypeAdapter(Date.class, new DateSerializer())
                                    .registerTypeAdapter(Time.class, new TimeSerializer())
                                    .create();
                            editor.putString("UsuarioJson", g.toJson(u, new TypeToken<Usuario>() {}.getType()));
                            editor.apply();
                            editEmail.setText("");
                            editPassword.setText("");

                            if (u.getRole().equals("ADMIN")) {
                                startActivity(new Intent(this, AdminActivity.class));
                            } else if (u.getRole().equals("USER")) {
                                startActivity(new Intent(this, HomeActivity.class));
                            }
                        } else {
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Credenciales inválidas").show();
                        }
                    });
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Complete todos los campos").show();
                }
            } catch (Exception e) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Se ha producido un error al intentar loguearte: " + e.getMessage()).show();
            }
        });


        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputEmail.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private boolean validar() {
        boolean retorno = true;
        String usuario, password;
        usuario = editEmail.getText().toString();
        password = editPassword.getText().toString();
        if (usuario.isEmpty()) {
            txtInputEmail.setError("Ingrese su correo electrónico");
            retorno = false;
        } else {
            txtInputEmail.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPassword.setError("Ingrese su contraseña");
            retorno = false;
        } else {
            txtInputPassword.setErrorEnabled(false);
        }
        return retorno;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String pref = preferences.getString("UsuarioJson", "");
//        if(!pref.equals("")){
//            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Se detecto una sesión activa, el login será omitido!");
//            this.startActivity(new Intent(this, HomeActivity.class));
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("has oprimido el botón atrás")
                .setContentText("¿Quieres cerrar la aplicación?")
                .setCancelText("No, Cancelar!").setConfirmText("Sí, Cerrar")
                .showCancelButton(true).setCancelClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Operación cancelada")
                            .setContentText("No saliste de la app")
                            .show();
                }).setConfirmClickListener(sweetAlertDialog -> {
                    sweetAlertDialog.dismissWithAnimation();
                    System.exit(0);
                }).show();
    }

    public void activityRegistrase(View view) {
        Log.d("MainActivity", "activityRegistrase called");
        Intent intent = new Intent(MainActivity.this,RegistrarUsuarioActivity.class);
        startActivity(intent);
    }
}