package br.com.app.loginapplication.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.app.loginapplication.R;
import br.com.app.loginapplication.activitys.control.ConectaHttp;
import br.com.app.loginapplication.activitys.control.Criptografia;

public class CadastraActivity extends AppCompatActivity {

    private EditText edtNome,edtEmail,edtSenha,edtSenhaConf;
    private Button btCadastra,btCancela;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra);

        edtNome=(EditText)findViewById(R.id.edt_nome_cadastra);
        edtEmail=(EditText)findViewById(R.id.edt_email_cadastra);
        edtSenha=(EditText)findViewById(R.id.edt_senha_cadastra);
        edtSenhaConf = (EditText)findViewById(R.id.edt_senha_cadastra_conf);
        btCadastra=(Button)findViewById(R.id.bt_cadastra);
        btCancela=(Button)findViewById(R.id.bt_cancela_cadastro);

        btCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(CadastraActivity.this,MainActivity.class);
                startActivity(intent);

                finish();
            }
        });

        btCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtSenha.getText().toString().equals("")||edtSenha.getText().toString().equals(null)||
                   edtNome.getText().toString().equals("")||edtNome.getText().toString().equals(null)||
                   edtEmail.getText().toString().equals("")||edtEmail.getText().toString().equals(null)){

                    Toast.makeText(CadastraActivity.this,getString(R.string.campos_vazios),Toast.LENGTH_SHORT).show();

                }else{

                    if (edtSenha.getText().toString().equals(edtSenhaConf.getText().toString())){
                        try{
                            JSONObject jsonObject=new JSONObject();

                            jsonObject.put("nome",edtNome.getText().toString());
                            jsonObject.put("email",edtEmail.getText().toString());
                            jsonObject.put("password", Criptografia.criptografar(edtSenha.getText().toString()));

                            //   Toast.makeText(CadastraActivity.this,,Toast.LENGTH_LONG).show();

                            new Put().execute(getString(R.string.insert_ws),jsonObject.toString());

                        }catch (JSONException e){

                        }
                    }else {
                        Toast.makeText(CadastraActivity.this,getString(R.string.campos_conf_senha),Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }

    private class Put extends AsyncTask<String,Void,String> {

        private ConectaHttp conectaHttp;

        @Override
        protected void onPreExecute() {
            // create dialog here
            load = new ProgressDialog(CadastraActivity.this);
            load.setMessage(getString(R.string.loading));
            load.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            conectaHttp = new ConectaHttp();

            return conectaHttp.putDados(urls[0], urls[1]);

        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado == null) {
                load.dismiss();
                Toast.makeText(CadastraActivity.this, getString(R.string.servidor_off), Toast.LENGTH_LONG).show();
            } else {

                load.dismiss();
                Toast.makeText(CadastraActivity.this, getString(R.string.insert_ok), Toast.LENGTH_LONG).show();
            }

        }
    }
}
