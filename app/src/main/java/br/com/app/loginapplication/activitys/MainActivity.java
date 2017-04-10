package br.com.app.loginapplication.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.app.loginapplication.R;
import br.com.app.loginapplication.activitys.bean.Usuario;
import br.com.app.loginapplication.activitys.control.ConectaHttp;

public class MainActivity extends AppCompatActivity {

    private Button btCad;
    private ListView listViewMain;
    private Usuario usuario;
    private ProgressDialog load;
    private List<String> usuarioList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCad = (Button) findViewById(R.id.bt_cadastrar_usuario);

        btCad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastraActivity.class);
                startActivity(intent);
                finish();

            }
        });

        new Get().execute(getString(R.string.get_ws));

    }


    private class Get extends AsyncTask<String, Void, String> {

        private ConectaHttp conectaHttp;

        @Override
        protected void onPreExecute() {
            // create dialog here
            load = new ProgressDialog(MainActivity.this);
            load.setMessage(getString(R.string.loading));
            load.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            conectaHttp = new ConectaHttp();

            return conectaHttp.getDados(urls[0]);

        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado == null) {
                load.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.servidor_off), Toast.LENGTH_LONG).show();
            } else {
                usuarioList = new ArrayList<String>();

                try {
                    //captura o retorno com u jsonarray
                    JSONArray jsonArray = new JSONArray(resultado);

                    //preencher a lista com os emails cadastrados
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        usuario = new Usuario(jsonObject.getInt("id"),
                                jsonObject.getString("nome"),
                                jsonObject.getString("email"),
                                jsonObject.getString("password"));

                        if (usuario.getEmail().toString().equals("") || usuario.getEmail().toString().equals(null)) {

                        } else {
                            usuarioList.add(usuario.getEmail().toString());
                        }
                    }

                    //preencher o list view

                    listViewMain = (ListView) findViewById(R.id.list_view_main);

                    arrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                            android.R.layout.simple_list_item_1, usuarioList);

                    listViewMain.setAdapter(arrayAdapter);

                    //finaliza progress dialog
                    load.dismiss();

                    //exibe toast
                    Toast.makeText(MainActivity.this, getString(R.string.lista_main_ok), Toast.LENGTH_LONG).show();

                    //função de click no item da lista
                    listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String email = parent.getItemAtPosition(position).toString();
                            Intent irParaLogin = new Intent(MainActivity.this, LoginActivity.class);
                            irParaLogin.putExtra("email", email);
                            startActivity(irParaLogin);

                        }
                    });

                } catch (JSONException e) {

                }

            }
        }
    }
}
