package br.com.bhl.superfid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.crash.FirebaseCrash;

import br.com.bhl.superfid.main.Usuario;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends ComumActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN_GOOGLE = 7896;

    private AutoCompleteTextView edt_email;
    private EditText edt_senha;

    private LoginButton btn_facebook;
    private CallbackManager callbackManager;

    private SignInButton btn_google;
    private GoogleApiClient googleApiClient;

    // Variaveis para autenticar usuario
    private FirebaseAuth myAuth;
    private FirebaseAuth.AuthStateListener myAuthListener;

    private Usuario usuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // FACEBOOK LOG IN
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        btn_facebook = (LoginButton) findViewById(R.id.btn_facebook);
        btn_facebook.setReadPermissions("email", "public_profile");
        btn_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                showToast("Login com Facebook cancelado.");
            }

            @Override
            public void onError(FacebookException error) {
                showToast("Erro Facebook 1! Tente mais tarde.");
            }
        });

        // GOOGLE SIGN IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        btn_google = (SignInButton) findViewById(R.id.btn_google);
        btn_google.setSize(SignInButton.SIZE_WIDE);
        btn_google.setColorScheme(SignInButton.COLOR_DARK);
        btn_google.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, SIGN_IN_GOOGLE);
            }
        });

        myAuth = FirebaseAuth.getInstance();
        myAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuario = myAuth.getCurrentUser();
                if (usuario != null) {
                    chamarMainActivity();
                }
            }
        };

        initViews();
        initUser();

    }

    /**********************************************************************************
     *********************************************************************************/
    @Override
    protected void onStart() {
        super.onStart();
        myAuth.addAuthStateListener(myAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myAuthListener != null) {
            myAuth.removeAuthStateListener(myAuthListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        FirebaseCrash
                .report(
                        new Exception(
                                connectionResult.getErrorCode() + ": " + connectionResult.getErrorMessage()
                        )
                );
        showSnackbar(connectionResult.getErrorMessage());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_GOOGLE) {
            GoogleSignInResult resultado = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignResult(resultado);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**********************************************************************************
     *********************************************************************************/
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

    /**********************************************************************************
     *********************************************************************************/
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

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        myAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showToast("Erro Facebook 2! Tente mais tarde.");
                }
            }
        });
    }

    private void handleSignResult(GoogleSignInResult resultado) {
        if (resultado.isSuccess()) {
            firebaseAuthWithGoogle(resultado.getSignInAccount());
        } else {
            showToast("Erro Google 1! Tente mais tarde.");
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        myAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    showToast("Erro Google 2! Tente mais tarde.");
                }
            }
        });
    }

    public void signIn(View view) {
        if (!validarForm()) {
            return;
        }

        openProgressBar();
        initUser();

        FirebaseCrash.log("LoginActivity:signIn()");
        myAuth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        )
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            showSnackbar("Login falhou");
                            return;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseCrash.report(e);
            }
        });
    }

    private boolean validarForm() {
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

}// fim da classe