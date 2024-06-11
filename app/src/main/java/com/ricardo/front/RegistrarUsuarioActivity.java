package com.ricardo.front;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
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
import java.time.LocalDateTime;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    private ClienteViewModel clienteViewModel;
    private UsuarioViewModel usuarioViewModel;
    private Button btnGuardarDatos;

    private EditText etNombres, etApellidos, etTelefono, etTipoDoc, etNumDoc,
            etDireccion, etProvincia, etCapital, etEmail, etPassword;

    private TextInputLayout txtNombres, txtApellidos, txtTelefono, txtTipoDoc, txtNumDoc,
            txtDireccion, txtProvincia, txtCapital, txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);

        this.init();
        this.initViewModel();

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
        etNombres = findViewById(R.id.nombres);
        etApellidos = findViewById(R.id.apellidos);
        etTelefono = findViewById(R.id.telefono);
        etTipoDoc = findViewById(R.id.tipoDoc);
        etNumDoc = findViewById(R.id.numDoc);
        etDireccion = findViewById(R.id.direccion);
        etProvincia = findViewById(R.id.provincia);
        etCapital = findViewById(R.id.capital);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);

        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtTipoDoc = findViewById(R.id.txtTipoDoc);
        txtNumDoc = findViewById(R.id.txtNumDoc);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtProvincia = findViewById(R.id.txtProvincia);
        txtCapital = findViewById(R.id.txtCapital);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });
        ///ONCHANGE LISTENEER A LOS EDITEXT
        etNombres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtNombres.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etApellidos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtApellidos.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtTelefono.setErrorEnabled(false);
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
        etProvincia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtProvincia.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etCapital.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtCapital.setErrorEnabled(false);
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
                cliente.setNombres(etNombres.getText().toString());
                cliente.setApellidos(etApellidos.getText().toString());
                cliente.setTelefono(etTelefono.getText().toString());
                cliente.setTipoDoc(etTipoDoc.getText().toString());
                cliente.setNumDoc(etNumDoc.getText().toString());
                cliente.setDireccion(etDireccion.getText().toString());
                cliente.setProvincia(etProvincia.getText().toString());
                cliente.setCapital(etCapital.getText().toString());
                cliente.setFecha(LocalDate.now());

                UsuarioRegistroDTO usuarioRegistroDTO = new UsuarioRegistroDTO();
                usuarioRegistroDTO.setEmail(etEmail.getText().toString());
                usuarioRegistroDTO.setContrasena(etPassword.getText().toString());
                usuarioRegistroDTO.setVigencia(true);

                cliente.setUsuarioRegistroDTO(usuarioRegistroDTO);

                clienteViewModel.guardarCliente(cliente).observe(this, response -> {
                    if (response != null && response.getRpta() == Global.RPTA_OK) {
                        successMessage("Cliente registrado correctamente");
                        limpiarCampos();
                        Intent intent = new Intent(RegistrarUsuarioActivity.this,MainActivity.class);
                        startActivity(intent);
                    } else if (response != null && response.getRpta() == Global.RPTA_WARNING) {
                        warningMessage(response.getMessage());
                    } else {
                        errorMessage("Error al registrar cliente");
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
        etNombres.setText("");
        etApellidos.setText("");
        etTelefono.setText("");
        etTipoDoc.setText("");
        etNumDoc.setText("");
        etDireccion.setText("");
        etProvincia.setText("");
        etCapital.setText("");
        etEmail.setText("");
        etPassword.setText("");
    }

    private boolean validar() {
        boolean retorno = true;
        String name, lastname, phone, typeDoc, numDoc, address, province, capital,
                email, password;
        name = etNombres.getText().toString();
        lastname = etApellidos.getText().toString();
        phone = etTelefono.getText().toString();
        typeDoc = etTipoDoc.getText().toString();
        numDoc = etNumDoc.getText().toString();
        address = etDireccion.getText().toString();
        province = etProvincia.getText().toString();
        capital = etCapital.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (name.isEmpty()) {
            txtNombres.setError("Ingresar nombres");
            retorno = false;
        } else {
            txtNombres.setErrorEnabled(false);
        }
        if (lastname.isEmpty()) {
            txtApellidos.setError("Ingresar apellidos");
            retorno = false;
        } else {
            txtApellidos.setErrorEnabled(false);
        }
        if (phone.isEmpty()) {
            txtTelefono.setError("Ingresar numero telefono");
            retorno = false;
        } else {
            txtTelefono.setErrorEnabled(false);
        }
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
        if (province.isEmpty()) {
            txtProvincia.setError("Ingresar provincia");
            retorno = false;
        } else {
            txtProvincia.setErrorEnabled(false);
        }
        if (capital.isEmpty()) {
            txtCapital.setError("Ingresar capital");
            retorno = false;
        } else {
            txtCapital.setErrorEnabled(false);
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

}