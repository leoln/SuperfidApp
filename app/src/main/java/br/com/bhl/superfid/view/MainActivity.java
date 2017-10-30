package br.com.bhl.superfid.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.IOException;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Usuario;
import br.com.bhl.superfid.util.WebClient;

public class MainActivity extends ComumActivity {

    private Toolbar toolbar;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        initUser();

    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */
    @Override
    protected void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    protected void initUser() {
        GetUsuarioService getUsuarioService = new GetUsuarioService();
        getUsuarioService.execute(firebaseUser.getUid());
    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Intent it = new Intent(this, MainBluetoothActivity.class);
                it.putExtra("qrResult", result.getContents());
                it.putExtra("usuario",usuario);
                startActivity(it);
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */
    public void scanQrCode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Aproxime do QRCode do Carrinho");
        integrator.setTimeout(8000);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.initiateScan();
    }

    private class GetUsuarioService extends AsyncTask<String, Void, Usuario> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openProgressBar();
        }

        @Override
        protected Usuario doInBackground(String... strings) {
            Usuario user = null;
            String json = "";

            Gson gson = new Gson();

            try {
                json = WebClient.get("/usuario/parseJson?codigoAutenticacao=", strings[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }

            user = gson.fromJson(json, Usuario.class);

            return user;
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            super.onPostExecute(usuario);
            closeProgressBar();

            setUsuario(usuario);

            toolbar.setTitle("Seja bem-vindo " + getUsuario().getNome());
            setSupportActionBar(toolbar);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}