package com.ricardo.front;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgotPasswordActivity extends AppCompatActivity {
    private UsuarioViewModel usuarioViewModel;
    private Button btnEnviar;
    private EditText correo;
    private TextInputLayout txtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_pasword);

        this.init();
        this.initViewModel();
        Retroceder();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmarEmail();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void init(){
        correo = findViewById(R.id.correo);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnEnviar = findViewById(R.id.btnEnviar);
    }
    private void initViewModel() {
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void confirmarEmail() {
        String email = correo.getText().toString().trim();
        if (!email.isEmpty()) {
            usuarioViewModel.forgotPassword(email).observe(this, response -> {
                if (response != null && response.getRpta() == 1) {
                    Toast.makeText(ForgotPasswordActivity.this, (response.getMessage()), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this,ResetPasswordActivity.class);
                    startActivity(intent);
                } else {
                    new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(response != null ? response.getMessage() : "Error al solicitar OTP")
                            .show();
                }
            });
        } else {
            new SweetAlertDialog(ForgotPasswordActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Campo vacío")
                    .setContentText("Por favor, introduce tu correo electrónico")
                    .show();
        }
    }

    public void Retroceder() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent i = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

}