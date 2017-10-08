package br.com.bhl.superfid.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Usuario;
import br.com.bhl.superfid.util.FirebaseConnection;

public class LoginActivity extends ComumActivity {

    private AutoCompleteTextView edt_email;
    private EditText edt_senha;

    private Usuario usuario;

    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseConnection.getFirebaseAuth();
    }

    /* ***************************************************************************
    *                       METODOS HERDADOS DA CLASSE PAI
    * *************************************************************************** */
    @Override
    protected void initViews() {
        edt_email = (AutoCompleteTextView) findViewById(R.id.edt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        progressBar = (ProgressBar) findViewById(R.id.login_progressbar);
    }

    @Override
    protected void initUser() {
        usuario = new Usuario();
        usuario.setEmail(edt_email.getText().toString());
        usuario.setSenha(edt_senha.getText().toString());
    }

    /* ***************************************************************************
    *                      METODOS DE
    * *************************************************************************** */
    private void chamarMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void chamarCadastrarActivity(View view) {
        Intent intent = new Intent(this, CadastrarActivity.class);
        startActivity(intent);
    }

    public void chamarRecuperarActivity(View view) {
        Intent intent = new Intent(this, RecuperarActivity.class);
        startActivity(intent);
    }

    public void chamarIntroducaoActivity() {
        Intent intent = new Intent(this, IntroducaoActivity.class);
        startActivity(intent);
        finish();
    }

    public void signIn(View view) {
        if (!validarFormulario()) {
            return;
        }

        openProgressBar();
        initUser();

        FirebaseCrash.log("LoginActivity:signIn()");
        firebaseAuth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    closeProgressBar();
                    chamarMainActivity();
                } else {
                    closeProgressBar();
                    showSnackbar("Login falhou");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseCrash.report(e);
            }
        });
    }

    private boolean validarFormulario() {
        boolean valido = true;

        String email = edt_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            edt_email.setError("Obrigatório");
            valido = false;
        } else {
            edt_email.setError(null);
        }

        String senha = edt_senha.getText().toString();
        if (TextUtils.isEmpty(senha)) {
            edt_senha.setError("Obrigatório");
            valido = false;
        } else {
            edt_senha.setError(null);
        }

        return valido;
    }
}