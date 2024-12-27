package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class gere_vendeur extends AppCompatActivity {


    EditText nom,prénom,email,pass,r_sociale,tel;
    Button add;
    ProgressDialog dialog;
TextView T;
    JSONParser parser =new JSONParser();
    int success;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gere_vendeur);

        nom =findViewById(R.id.nom);
        prénom=findViewById(R.id.prenom);
        email=findViewById(R.id.email);
        tel=findViewById(R.id.tel);
        r_sociale=findViewById(R.id.raison);
        pass=findViewById(R.id.passw);
        T=findViewById(R.id.loginText);
        T.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(gere_vendeur.this, vendeur_list.class);
                startActivity(i);
            }
        });

        add=findViewById(R.id.buttonSignUp);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!nom.getText().toString().isEmpty() && !pass.getText().toString().isEmpty() && !tel.getText().toString().isEmpty() && !r_sociale.getText().toString().isEmpty()  &&!prénom.getText().toString().isEmpty() && !email.getText().toString().isEmpty()){
                    new add().execute();
                }
                else {
                    Toast.makeText(gere_vendeur.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    class add extends AsyncTask<String,String,String>

    {

        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(gere_vendeur.this);
            dialog.setMessage("patientez SVP ");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map= new HashMap<String,String>();
            map.put("nom",nom.getText().toString());
            map.put("prénom",prénom.getText().toString()) ;
            map.put("email",email.getText().toString());
            map.put("pass",pass.getText().toString());
            map.put("tel",tel.getText().toString());
            map.put("r_social",r_sociale.getText().toString());


            JSONObject o= parser.makeHttpRequest(Config.IP+"vendeurs/addv.php","GET",
                    map );
            try {
                success=o.getInt("success");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            if (success==1){
                Toast.makeText(gere_vendeur.this, "success ", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(gere_vendeur.this,vendeur_list.class);
                startActivity(i);

            }
            else if (success==0 ){
                Toast.makeText(gere_vendeur.this, "ECHEC!!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}