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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class InscrActivity extends AppCompatActivity {
    EditText nom,prénom,email,pass;
    Button add;
    ProgressDialog dialog;
    JSONParser parser = new JSONParser();
    int success;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscr);

        nom=findViewById(R.id.name);
        prénom=findViewById(R.id.pre);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.passw);
        add=findViewById(R.id.buttonSignUp);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nom.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()  &&!prénom.getText().toString().isEmpty() && !email.getText().toString().isEmpty()){
                    new add().execute();

                }
                else {
                    Toast.makeText(InscrActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }








    class add extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(InscrActivity.this);
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
                Toast.makeText(InscrActivity.this, "inscription validé ", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(InscrActivity.this,LoginAcheteur.class);
                startActivity(i);
            }
            else if (success==0 ){
                Toast.makeText(InscrActivity.this, "ECHEC!!!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}