package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText nom,prénom,email,pass;

    Button add,liste;
    ProgressDialog dialog;
    ImageView back;

    JSONParser parser= new JSONParser();
    int success ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nom=findViewById(R.id.nom);
        prénom=findViewById(R.id.prénom);
        email=findViewById(R.id.pre);
        pass=findViewById(R.id.passw);
        add=findViewById(R.id.add);
        back=findViewById(R.id.imageView10);
        liste=findViewById(R.id.button6);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });

        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(MainActivity.this,ListAcheteur.class);
                startActivity(i);

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nom.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()  &&!prénom.getText().toString().isEmpty() && !email.getText().toString().isEmpty()){
                    new add().execute();
                }
                else {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


   //le but de cette classe: executer un thread dans le background
    //il on a plus de conflis avec ui thread
    //donc on va executer un thread qui va nous permettre de consommer le resultat de web service

    class add extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("patientez SVP ");
            dialog.show();

        }

        protected String doInBackground(String... objects) {
            //le code dans cette mthode sexecute dans le worker threads
            HashMap<String,String> map= new HashMap<String,String>();
            map.put("nom",nom.getText().toString());
            map.put("prénom",prénom.getText().toString()) ;
            map.put("email",email.getText().toString());
            map.put("pass",pass.getText().toString());


            JSONObject o= parser.makeHttpRequest(Config.IP+"acheteurs/add.php","GET",
                  map );
            try {
                success=o.getInt("success");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            if (success==1){
                Toast.makeText(MainActivity.this, "success ", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(MainActivity.this,ListAcheteur.class);
                startActivity(i);
            }
            else if (success==0 ){
                Toast.makeText(MainActivity.this, "ECHEC!!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}