package com.ricardo.front;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
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
import com.ricardo.front.entity.service.Cliente;
import com.ricardo.front.entity.service.dto.UsuarioRegistroDTO;
import com.ricardo.front.utils.Global;
import com.ricardo.front.viewmodel.ClienteViewModel;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.time.LocalDate;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private ClienteViewModel clienteViewModel;
    private UsuarioViewModel usuarioViewModel;
    private Button btnGuardarDatos;
    private AutoCompleteTextView dropdownTipoDoc;
    private EditText  etUsername, etEmail, etPassword,etTipoDoc, etNumDoc, etDireccion;
    private TextInputLayout txtUsername, txtEmail, txtPassword, txtTipoDoc, txtNumDoc, txtDireccion;
    TextView redirigirLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);

        this.init();
        this.initViewModel();
        this.Retroceder();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViewModel() {
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void init() {
        redirigirLogin = findViewById(R.id.redirigirLogin);
        redirigirLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrarUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        etTipoDoc = findViewById(R.id.tipoDoc);
        etNumDoc = findViewById(R.id.numDoc);
        etDireccion = findViewById(R.id.direccion);
        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);

        txtTipoDoc = findViewById(R.id.txtTipoDoc);
        txtNumDoc = findViewById(R.id.txtNumDoc);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });

        ///ONCHANGE LISTENEER A LOS EDITEXT
        etTipoDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtTipoDoc.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etNumDoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtNumDoc.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etDireccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtDireccion.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtUsername.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtPassword.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void guardarDatos() {
        if (validar()) {
            Cliente cliente = new Cliente();
            try {
                cliente.setTipoDoc(etTipoDoc.getText().toString());
                cliente.setNumDoc(etNumDoc.getText().toString());
                cliente.setDireccion(etDireccion.getText().toString());
                cliente.setFecha(LocalDate.now());

                UsuarioRegistroDTO usuarioRegistroDTO = new UsuarioRegistroDTO();
                usuarioRegistroDTO.setUsername(etUsername.getText().toString());
                usuarioRegistroDTO.setEmail(etEmail.getText().toString());
                usuarioRegistroDTO.setContrasena(etPassword.getText().toString());
                usuarioRegistroDTO.setVigencia(true);
                usuarioRegistroDTO.setFecha(LocalDate.now());

                cliente.setUsuarioRegistroDTO(usuarioRegistroDTO);

                clienteViewModel.guardarCliente(cliente).observe(this, response -> {
                    if (response != null && response.getRpta() == Global.RPTA_OK) {
                        Toast.makeText(RegistrarUsuarioActivity.this, (response.getMessage()), Toast.LENGTH_SHORT).show();

                        // Guardar credenciales en SharedPreferences
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("lastUsername", etUsername.getText().toString());
                        editor.putString("lastPassword", etPassword.getText().toString());
                        editor.apply();

                        limpiarCampos();
                        Intent intent = new Intent(RegistrarUsuarioActivity.this,MainActivity.class);
                        startActivity(intent);
                    } else if (response != null && response.getRpta() == Global.RPTA_WARNING) {
                        warningMessage(response.getMessage());
                    } else {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText(response.getMessage()).show();
                    }
                });
            } catch (Exception e) {
                errorMessage("Error al registrar cliente: " + e.getMessage());
            }
        } else {
            errorMessage("Por favor, complete todos los campos del formulario");
        }
    }

    private void limpiarCampos() {
        etTipoDoc.setText("");
        etNumDoc.setText("");
        etDireccion.setText("");
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
    }

    private boolean validar() {
        boolean retorno = true;
        String  usuario, email, password, typeDoc, numDoc, address;

        typeDoc = etTipoDoc.getText().toString();
        numDoc = etNumDoc.getText().toString();
        address = etDireccion.getText().toString();
        usuario = etUsername.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (typeDoc.isEmpty()) {
            txtTipoDoc.setError("Ingresar tipo documento ");
            retorno = false;
        } else {
            txtTipoDoc.setErrorEnabled(false);
        }
        if (numDoc.isEmpty()) {
            txtNumDoc.setError("Ingresar número identificacion");
            retorno = false;
        } else {
            txtNumDoc.setErrorEnabled(false);
        }
        if (address.isEmpty()) {
            txtDireccion.setError("Ingresar dirección ");
            retorno = false;
        } else {
            txtDireccion.setErrorEnabled(false);
        }
        if (usuario.isEmpty()) {
            txtUsername.setError("Ingresar tu usuario");
            retorno = false;
        } else {
            txtUsername.setErrorEnabled(false);
        }
        if (email.isEmpty()) {
            txtEmail.setError("Ingresar email");
            retorno = false;
        } else {
            txtEmail.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtPassword.setError("Ingresar contraseña");
            retorno = false;
        } else {
            txtPassword.setErrorEnabled(false);
        }

        return retorno;
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }

    public void Retroceder() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(RegistrarUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}