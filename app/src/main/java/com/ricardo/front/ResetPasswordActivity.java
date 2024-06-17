package com.ricardo.front;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ResetPasswordActivity extends AppCompatActivity {
    private UsuarioViewModel usuarioViewModel;
    private Button btnConfirmar;
    private EditText otp, password;
    private TextInputLayout txtOtp, txtNewPassword;
    TextView redirigirSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reset_password);

        this.init();
        this.initViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

    }

    private void init(){
        redirigirSendEmail = findViewById(R.id.redirigirSendEmail);
        redirigirSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        otp = findViewById(R.id.otp);
        password = findViewById(R.id.password);

        txtOtp = findViewById(R.id.txtOtp);
        txtNewPassword = findViewById(R.id.txtNewPassword);

        btnConfirmar = findViewById(R.id.btnConfirmar);
    }

    private void initViewModel() {
        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
    }

    private void resetPassword() {
        String ottp = otp.getText().toString().trim();
        String newPassword = password.getText().toString().trim();
        if (!ottp.isEmpty() && !newPassword.isEmpty()) {
            usuarioViewModel.resetPassword(ottp, newPassword).observe(this, response -> {
                if (response != null && response.getRpta() == 1) {
                    Toast.makeText(ResetPasswordActivity.this, (response.getMessage()), Toast.LENGTH_SHORT).show();

                    // Guardar credenciales en SharedPreferences
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("lastPassword", password.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(ResetPasswordActivity.this,MainActivity.class);
                    startActivity(intent);
                } else {
                    new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText(response != null ? response.getMessage() : "Error al restablecer contraseña")
                            .show();
                }
            });
        } else {
            new SweetAlertDialog(ResetPasswordActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Campos vacíos")
                    .setContentText("Por favor, introduce el OTP y la nueva contraseña")
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}