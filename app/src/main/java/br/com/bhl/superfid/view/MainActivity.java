package br.com.bhl.superfid.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import br.com.bhl.superfid.R;

public class MainActivity extends ComumActivity {

    private Toolbar toolbar;
    private TextView txtNomeProduto;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Seja bem-vindo " /*+ firebaseUser.getDisplayName() + "!"*/);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    /* ***************************************************************************
    *                      METODOS DE CICLO DE VIDA DO ANDROID
    * *************************************************************************** */
    @Override
    protected void initViews() {

    }

    @Override
    protected void initUser() {

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
                startActivity(it);
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

}