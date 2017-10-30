package br.com.bhl.superfid.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.bhl.superfid.R;
import br.com.bhl.superfid.model.Usuario;
import br.com.bhl.superfid.util.MaskUtil;
import br.com.bhl.superfid.util.StringUtil;
import br.com.bhl.superfid.util.WebClient;

public class CadastrarActivity extends ComumActivity {

    private AutoCompleteTextView edt_nome;
    private AutoCompleteTextView edt_sobrenome;
    private AutoCompleteTextView edt_cpf;
    private EditText edt_ddd;
    private EditText edt_telefone;
    private EditText edt_dtnascimento;
    private AutoCompleteTextView edt_email;
    private EditText edt_senha;

    private FirebaseAuth firebaseAuth;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Cadastro");
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();

        initViews();
    }

    /* ***************************************************************************
    *                       METODOS HERDADOS DA CLASSE PAI
    * *************************************************************************** */
    @Override
    protected void initViews() {
        edt_nome = (AutoCompleteTextView) findViewById(R.id.edt_nome);
        edt_sobrenome = (AutoCompleteTextView) findViewById(R.id.edt_sobrenome);
        edt_cpf = (AutoCompleteTextView) findViewById(R.id.edt_cpf);
        edt_cpf.addTextChangedListener(MaskUtil.insert("###.###.###-##", edt_cpf));
        edt_ddd = (EditText) findViewById(R.id.edt_ddd);
        edt_telefone = (EditText) findViewById(R.id.edt_telefone);
        edt_dtnascimento = (EditText) findViewById(R.id.edt_dtnascimento);
        edt_email = (AutoCompleteTextView) findViewById(R.id.edt_email);
        edt_senha = (EditText) findViewById(R.id.edt_senha);
        progressBar = (ProgressBar) findViewById(R.id.cadastrar_progressbar);
    }

    @Override
    protected void initUser() {
        usuario = new Usuario();

        usuario.setNome(edt_nome.getText().toString());
        usuario.setSobrenome(edt_sobrenome.getText().toString());

        if (!TextUtils.isEmpty(edt_cpf.getText().toString()))
            usuario.setNumeroCPF(Long.parseLong(StringUtil.tirarCaracteresEspeciais(edt_cpf.getText().toString())));
        else
            usuario.setNumeroCPF((long)0);


        if (!TextUtils.isEmpty(edt_ddd.getText().toString()))
            usuario.setNumeroDDD(Integer.parseInt(edt_ddd.getText().toString()));
        else
            usuario.setNumeroDDD(0);


        if (!TextUtils.isEmpty(edt_telefone.getText().toString()))
            usuario.setNumeroTelefone(Long.parseLong(edt_telefone.getText().toString()));
        else
            usuario.setNumeroTelefone((long)0);


        if (!TextUtils.isEmpty(edt_dtnascimento.getText().toString()))
            usuario.setDataNascimento(edt_dtnascimento.getText().toString());
        else
            edt_dtnascimento.setText(null);

        usuario.setEmailAutenticacao(edt_email.getText().toString());
    }

    /* ***************************************************************************
    *                      METODOS DE
    * *************************************************************************** */
    public void enviarDadosCadastro(View view) {
        criarUsuario();
    }

    private void criarUsuario() {

        if (!validarFormulario()) {
            return;
        }

        initUser();

        openProgressBar();

        String senha = edt_senha.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmailAutenticacao(), senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            usuario.setCodigoAutenticacao(firebaseAuth.getCurrentUser().getUid());

                            JsonUsuario jsonUsuario = new JsonUsuario();
                            jsonUsuario.execute(usuario);

                            closeProgressBar();
                            showToast("Conta criada com sucesso!");
                            firebaseAuth.signOut();
                            finish();
                        } else {
                            closeProgressBar();
                            showToast("Falha ao criar usuário.");
                        }

                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                FirebaseCrash.report(e);
                showSnackbar(e.getMessage());
            }
        });
    }

    private boolean validarFormulario() {
        boolean valido = true;

        String nome = edt_nome.getText().toString();
        if (TextUtils.isEmpty(nome)) {
            edt_nome.setError("Obrigatório");
            valido = false;
        } else {
            edt_nome.setError(null);
        }

        String cpf = edt_cpf.getText().toString();
        if (TextUtils.isEmpty(cpf)) {
            edt_cpf.setError("Obrigatório");
            valido = false;
        } else {
            edt_cpf.setError(null);
        }

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

    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditText edt_dtnascimento = getActivity().findViewById(R.id.edt_dtnascimento);
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

            Calendar data = Calendar.getInstance();
            data.set(year, month, day);

            edt_dtnascimento.setText(formatoData.format(data.getTime()));
        }
    }

    private class JsonUsuario extends AsyncTask<Usuario, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openProgressBar();
        }

        @Override
        protected String doInBackground(Usuario... usuarios) {
            Usuario usuario = usuarios[0];
            Gson gson = new Gson();

            try {
                WebClient.post("/usuario/cadastrar", gson.toJson(usuario));
            } catch (IOException e) {
                String s = e.getMessage();
                Log.v("ERRO POST", s);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);

            closeProgressBar();

        }

    }

}