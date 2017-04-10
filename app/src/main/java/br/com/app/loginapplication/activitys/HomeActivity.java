package br.com.app.loginapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import br.com.app.loginapplication.R;


public class HomeActivity extends AppCompatActivity {

    private TextView txtNome,txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtNome=(TextView)findViewById(R.id.txt_nome_home);
        txtEmail=(TextView)findViewById(R.id.txt_email_home);

        txtNome.setText(getIntent().getStringExtra("nome"));
        txtEmail.setText(getIntent().getStringExtra("email"));



    }
}
