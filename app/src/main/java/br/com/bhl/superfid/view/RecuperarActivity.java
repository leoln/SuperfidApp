package br.com.bhl.superfid.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Usuario;


public class RecuperarActivity extends ComumActivity {

    private Toolbar toolbar;
    private AutoCompleteTextView email;

    private FirebaseAuth firebaseAuth;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        initViews();
    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */
    @Override
    protected void initUser() {
        usuario = new Usuario();
        usuario.setEmail(email.getText().toString());
    }

    @Override
    protected void initViews() {
        toolbar.setTitle(getResources().getString(R.string.lbl_recuperar));
        email = (AutoCompleteTextView) findViewById(R.id.email);
    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */
    public void recuperarAcesso(View view) {
        initUser();
        firebaseAuth.sendPasswordResetEmail( usuario.getEmail() )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    email.setText("");
                    showToast("Recuperação de acesso iniciada. Email enviado.");
                } else {
                    showToast("Falhou! Tente novamente");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseCrash.report(e);
            }
        });
    }

} // fim da classe