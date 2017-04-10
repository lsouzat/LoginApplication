package br.com.app.loginapplication.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.app.loginapplication.R;
import br.com.app.loginapplication.activitys.control.ConectaHttp;
import br.com.app.loginapplication.activitys.control.Criptografia;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog load;
    private TextView txtEmail;
    private EditText edtSenha;
    private Button btLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail=(TextView)findViewById(R.id.txt_email_login);
        edtSenha=(EditText)findViewById(R.id.edt_email_senha);
        btLogar=(Button)findViewById(R.id.bt_logar_login);

        txtEmail.setText(getIntent().getStringExtra("email"));


        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSenha.getText().toString().equals("")||edtSenha.getText().toString().equals(null)){
                    Toast.makeText(LoginActivity.this,getString(R.string.login_not),Toast.LENGTH_SHORT).show();
                }else{
                    new Get().execute(getString(R.string.login_ws)+
                            txtEmail.getText().toString()+"/"+
                            Criptografia.criptografar(edtSenha.getText().toString()));
                }

            }
        });

    }

    private class Get extends AsyncTask<String,Void,String> {

        private ConectaHttp conectaHttp;

        @Override
        protected void onPreExecute(){
            // create dialog here
            load= new ProgressDialog(LoginActivity.this);
            load.setMessage(getString(R.string.loading));
            load.show();
        }

        @Override
        protected String doInBackground(String... urls){

            conectaHttp=new ConectaHttp();

            return  conectaHttp.getDados(urls[0]);

        }
        @Override
        protected void onPostExecute(String resultado){


            try{
                if(resultado.contains("false")){
                    Toast.makeText(LoginActivity.this,getString(R.string.login_not),Toast.LENGTH_SHORT).show();
                    load.dismiss();
                }else{
                    JSONObject jsonObject=new JSONObject(resultado);
                    String nome = jsonObject.getString("nome");

                    Intent irParaHome= new Intent(LoginActivity.this,HomeActivity.class);
                    irParaHome.putExtra("nome",nome);
                    irParaHome.putExtra("email",getIntent().getStringExtra("email"));
                    startActivity(irParaHome);
                    load.dismiss();
                    finish();
                }

            }catch (JSONException e){

            }



        }

    }
}
