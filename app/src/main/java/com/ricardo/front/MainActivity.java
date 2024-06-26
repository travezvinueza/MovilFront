package com.ricardo.front;

import static com.ricardo.front.util.Global.RPTA_ERROR;
import static com.ricardo.front.util.Global.RPTA_OK;
import static com.ricardo.front.util.Global.RPTA_WARNING;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ricardo.front.model.UsuarioDTO;
import com.ricardo.front.util.LocalDateSerializer;
import com.ricardo.front.util.LocalTimeSerializer;
import com.ricardo.front.viewmodel.UsuarioViewModel;


import java.sql.Date;
import java.sql.Time;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    private EditText editUsername, editPassword;
    private Button btnIngresar;
    private UsuarioViewModel viewModel;
    private TextInputLayout txtInputUsername, txtInputPassword;
    TextView olvidasteContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        this.init();
        this.initViewModel();
        this.Salir();

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
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

        txtInputUsername = findViewById(R.id.txtInputUsername);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnIngresar = findViewById(R.id.btnIngresar);

        olvidasteContrasena = findViewById(R.id.olvidasteContra);
        olvidasteContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Recuperar las credenciales almacenadas
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String lastUsername = preferences.getString("lastUsername", "");
//        String lastPassword = preferences.getString("lastPassword", "");

        // Rellenar los campos de inicio de sesión con las credenciales recuperadas
//        editUsername.setText(lastUsername);
//        editPassword.setText(lastPassword);

        txtInputUsername = findViewById(R.id.txtInputUsername);
        txtInputPassword = findViewById(R.id.txtInputPassword);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(v -> {
            try {
                if (validar()) {
                    viewModel.login(editUsername.getText().toString(), editPassword.getText().toString()).observe(this, response -> {
                        if (response != null) {
                            switch (response.getRpta()) {
                                case RPTA_OK:
                                    Toast.makeText(MainActivity.this, (response.getMessage()), Toast.LENGTH_SHORT).show();
//                                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
//                                            .setTitleText(response.getMessage())
//                                            .show();
                                    UsuarioDTO u = response.getBody();
                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    Gson g = new GsonBuilder()
                                            .registerTypeAdapter(Date.class, new LocalDateSerializer())
                                            .registerTypeAdapter(Time.class, new LocalTimeSerializer())
                                            .create();
                                    editor.putString("UsuarioJson", g.toJson(u, new TypeToken<UsuarioDTO>() {}.getType()));
                                    String usuarioJson = g.toJson(u, new TypeToken<UsuarioDTO>() {}.getType());
                                    Log.d("UsuarioJson", usuarioJson);
                                    editor.apply();
                                    editUsername.setText("");
                                    editPassword.setText("");


                                    if ("ADMIN".equals(u.getRole())) {
                                        startActivity(new Intent(this, AdminActivity.class));
                                    } else if ("USER".equals(u.getRole())) {
                                        startActivity(new Intent(this, HomeActivity.class));
                                    }
                                    break;
                                case RPTA_WARNING:
//                                    Toast.makeText(MainActivity.this, (response.getMessage()), Toast.LENGTH_SHORT).show();
                                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText(response.getMessage())
                                            .show();
                                    break;
                                case RPTA_ERROR:
                                default:
                                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Credenciales inválidas")
                                            .show();
                                    break;
                            }
                        } else {
                            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Error en la respuesta del servidor")
                                    .show();
                        }
                    });
                } else {
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Complete todos los campos")
                            .show();
                }
            } catch (Exception e) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Se ha producido un error al intentar loguearte: " + e.getMessage())
                        .show();
            }
        });


        editUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInputUsername.setErrorEnabled(false);
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
        usuario = editUsername.getText().toString();
        password = editPassword.getText().toString();
        if (usuario.isEmpty()) {
            txtInputUsername.setError("Ingrese su Usuario");
            retorno = false;
        } else {
            txtInputUsername.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPassword.setError("Ingrese su contraseña");
            retorno = false;
        } else {
            txtInputPassword.setErrorEnabled(false);
        }
        return retorno;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pref  = preferences.getString("UsuarioJson", "");

        if (!pref .isEmpty()) {
            Gson gson = new Gson();
            UsuarioDTO usuarioDTO = gson.fromJson(pref , new TypeToken<UsuarioDTO>() {}.getType());

            if (usuarioDTO != null) {
                String rol = usuarioDTO.getRole();
                Intent intent;
                if ("ADMIN".equals(rol)) {
                    Toast.makeText(this, "Se detecto una cuenta activa!", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Se detecto una cuenta activa!");
                    intent = new Intent(this, AdminActivity.class);
                } else if ("USER".equals(rol)) {
                    Toast.makeText(this, "Se detecto una cuenta activa!", Toast.LENGTH_SHORT).show();
//                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Se detecto una cuenta activa!");
                    intent = new Intent(this, HomeActivity.class);
                } else {
                    intent = new Intent(this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }
    }

    public void Salir() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Salir de la Aplicación")
                        .setContentText("¿Realmente quieres cerrar la aplicación?")
                        .setCancelText("No")
                        .setConfirmText("Sí")
                        .showCancelButton(true)
                        .setCancelClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Operación cancelada")
                                    .setContentText("No saliste de la app")
                                    .show();
                        })
                        .setConfirmClickListener(sweetAlertDialog -> {
                            sweetAlertDialog.dismissWithAnimation();
                            finishAffinity();
                        })
                        .show();
            }
        });
    }

    public void activityRegistrase(View view) {
        Intent intent = new Intent(MainActivity.this,RegistrarUsuarioActivity.class);
        startActivity(intent);
    }

}