package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginAcheteur extends AppCompatActivity {

    EditText email,pass;
    TextView view;
    Button con;
    JSONParser parser= new JSONParser();
    int success;
    String id,nom,prenom,mail,password;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acheteur);
        email=findViewById(R.id.email);
        pass =findViewById(R.id.passw);
        con=findViewById(R.id.buttonLogin);
        view= findViewById(R.id.signUpText);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new connect().execute();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginAcheteur.this,InscrActivity.class);
                startActivity(i);
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

            JSONObject O=parser.makeHttpRequest(Config.IP+"acheteurs/login.php","GET",map);
            try {
                success=O.getInt("success");
                if(success==1){
                    JSONArray A=O.getJSONArray("acheteur");
                    JSONObject o=A.getJSONObject(0);
                    id=o.getString("id");
                    nom=o.getString("nom");
                    prenom=o.getString("prenom");
                    mail=o.getString("mail");
                    password=o.getString("pass");


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
                Toast.makeText(LoginAcheteur.this, "connection approuvé", Toast.LENGTH_SHORT).show();
                SharedPreferences sp=getSharedPreferences("acheteur",MODE_PRIVATE);
                SharedPreferences.Editor edit;
                edit=sp.edit();
                edit.putString("id",id);
                edit.putString("name",nom);
                edit.putString("surname",prenom);
                edit.putString("email",mail);
                edit.putString("password",password);
                edit.apply();

                Intent i= new Intent(LoginAcheteur.this,AcceuilActivity2.class);
                i.putExtra("id",id);

                startActivity(i);

            }
            else {
                Toast.makeText(LoginAcheteur.this, "cordonnés incorrecte ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}