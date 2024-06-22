package com.ricardo.front;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.ricardo.front.model.UsuarioClienteDTO;
import com.ricardo.front.model.UsuarioDTO;
import com.ricardo.front.util.GenericResponse;
import com.ricardo.front.util.Global;
import com.ricardo.front.viewmodel.SharedViewModel;
import com.ricardo.front.viewmodel.UsuarioViewModel;

import java.time.LocalDate;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreateUserFragment extends DialogFragment {
    private UsuarioViewModel usuarioViewModel;
    private SharedViewModel sharedViewModel;
    private EditText fmUsername, fmEmail, fmPassword;
    private TextInputLayout fmTxtUsername, fmTxtEmail, fmTxtPassword;
    private Spinner spinner;
    private Button btn_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);

        fmUsername = view.findViewById(R.id.fmUsername);
        fmEmail = view.findViewById(R.id.fmEmail);
        fmPassword = view.findViewById(R.id.fmPassword);
        spinner = view.findViewById(R.id.sp);
        btn_save = view.findViewById(R.id.btn_save);

        fmTxtUsername = view.findViewById(R.id.fmTxtUsername);
        fmTxtEmail = view.findViewById(R.id.fmTxtEmail);
        fmTxtPassword = view.findViewById(R.id.fmTxtPassword);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        return view;
    }

    private void createUser() {
        String username = fmUsername.getText().toString();
        String email = fmEmail.getText().toString();
        String password = fmPassword.getText().toString();
        String role = spinner.getSelectedItem().toString();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            Toast.makeText(getContext(), "Por favor llena todos los espacios", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(username);
        usuarioDTO.setEmail(email);
        usuarioDTO.setContrasena(password);
        usuarioDTO.setVigencia(true);
        usuarioDTO.setRole(role);
        usuarioDTO.setFecha(LocalDate.now());

        UsuarioClienteDTO usuarioClienteDTO = new UsuarioClienteDTO();
        usuarioClienteDTO.setFecha(LocalDate.now());

        usuarioDTO.setUsuarioClienteDto(usuarioClienteDTO);

        usuarioViewModel.crearUsuario(usuarioDTO).observe(getViewLifecycleOwner(), new Observer<GenericResponse<UsuarioDTO>>() {
            @Override
            public void onChanged(GenericResponse<UsuarioDTO> response) {
                if (response.getRpta() == Global.RPTA_OK) {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Usuario Creado")
                            .setContentText(response.getMessage())
                            .setConfirmClickListener(sDialog -> {
                                sDialog.dismissWithAnimation();
                                sharedViewModel.setUserCreated(true);
                                dismiss();
                            })
                            .show();
                } else {
                    new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE).setTitleText(response.getMessage()).show();
                }
            }
        });
    }

    public void limpiarCampos(){
        if (fmUsername != null) fmUsername.setText("");
        if (fmEmail != null) fmEmail.setText("");
        if (fmPassword != null) fmPassword.setText("");
        if (spinner != null) spinner.setSelection(0);
    }
}