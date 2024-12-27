package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginVendeur extends AppCompatActivity {

    EditText email,pass;
    Button conn;
    int success;
    String id,nom,prenom,raison,tel,emailv,passv;
    JSONParser parser= new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vendeur);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.passw);
        conn= findViewById(R.id.buttonLogin);

        conn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new connect().execute();
            }
        });

    }

    class connect extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("email",email.getText().toString());
            map.put("password",pass.getText().toString());

            JSONObject O=parser.makeHttpRequest(Config.IP+"vendeurs/loginv.php","GET",map);
            try {
                success=O.getInt("success");
                if(success==1){
                    JSONArray A=O.getJSONArray("vendeur");
                    JSONObject o=A.getJSONObject(0);
                    id=o.getString("id");
                    nom=o.getString("nom");
                    prenom=o.getString("prenom");
                    raison=o.getString("raison");
                    tel=o.getString("tel");
                    emailv=o.getString("email");
                    passv=o.getString("pass");


                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (success==1){
                Toast.makeText(LoginVendeur.this, "connection approuvé", Toast.LENGTH_SHORT).show();
                SharedPreferences sp=getSharedPreferences("vendeur",MODE_PRIVATE);
                SharedPreferences.Editor edit;
                edit=sp.edit();
                edit.putString("id",id);
                edit.putString("nom",nom);
                edit.putString("prenom",prenom);
                edit.putString("raison",raison);
                edit.putString("tel",tel);
                edit.putString("email",emailv);
                edit.putString("pass",passv);
                edit.apply();

                Intent i= new Intent(LoginVendeur.this,espace_vendeur.class);

                startActivity(i);

            }
            else {
                Toast.makeText(LoginVendeur.this, "cordonnés incorrecte ", Toast.LENGTH_SHORT).show();
            }
        }
    }

}