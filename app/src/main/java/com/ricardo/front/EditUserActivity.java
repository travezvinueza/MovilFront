package com.ricardo.front;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ricardo.front.databinding.ActivityEditUserBinding;

public class EditUserActivity extends AppCompatActivity {
    ActivityEditUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.init();

    }

    private void init() {
        Intent intent = getIntent();
        if (intent != null) {
//            binding.tvId.setText(String.valueOf(intent.getLongExtra("id", 0)));
//            binding.tvUsername.setText(intent.getStringExtra("username"));
            binding.email.setText(intent.getStringExtra("email"));
            binding.password.setText(intent.getStringExtra("contrasena"));
//            binding.tvVigencia.setText(String.valueOf(intent.getBooleanExtra("vigencia", true)));
//            binding.tvRole.setText(intent.getStringExtra("role"));
//            binding.tvClienteId.setText(String.valueOf(intent.getLongExtra("clienteId", 0)));

            binding.nombres.setText(intent.getStringExtra("nombres"));
            binding.apellidos.setText(intent.getStringExtra("apellidos"));
            binding.telefono.setText(intent.getStringExtra("apellidos"));
            binding.tipoDoc.setText(intent.getStringExtra("tipoDoc"));
            binding.numDoc.setText(intent.getStringExtra("numDoc"));
            binding.direccion.setText(intent.getStringExtra("direccion"));
            binding.provincia.setText(intent.getStringExtra("provincia"));
            binding.capital.setText(intent.getStringExtra("capital"));
//            binding.tvFecha.setText(intent.getStringExtra("fecha"));
        } else {
            Toast.makeText(this, "No se pudo obtener los datos", Toast.LENGTH_SHORT).show();
        }
    }


}