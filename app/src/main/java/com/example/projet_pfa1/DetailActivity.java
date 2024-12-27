package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class DetailActivity extends AppCompatActivity {

    EditText e1,e2,e3,e4;
    Button B1,B2;
    String id ;
    ProgressDialog dialog,dialog1;
    JSONParser p =new JSONParser();

    String name , prenom,email,passs;
    int success,success1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        e1=findViewById(R.id.nomd);
        e2=findViewById(R.id.prenomd);
        e3=findViewById(R.id.emaild);
        e4=findViewById(R.id.pass2);
        B1=findViewById(R.id.button3);
        B2=findViewById(R.id.button4);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            id=extras.getString("id");
            new select().execute();

            B1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new modifier().execute();

                }
            });
            B2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new supprimer().execute();
                }
            });
        }

    }



    class supprimer  extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog1= new ProgressDialog(DetailActivity.this);
            dialog1.setMessage("en cours de suppression");
            dialog1.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map1= new HashMap<String,String>();
            map1.put("id",id);


            JSONObject MOD=p.makeHttpRequest(Config.IP+"acheteurs/delete.php","GET",map1);
            try {
                success1=MOD.getInt("success");
                if(success1==1){
                    Intent i1= new Intent(DetailActivity.this,ListAcheteur.class);
                    startActivity(i1);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog1.cancel();
            if (success1==1)
            {
                Toast.makeText(DetailActivity.this, "acheteur supprimé avec succés", Toast.LENGTH_SHORT).show();
            }

        }
    }







    class modifier extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog1= new ProgressDialog(DetailActivity.this);
            dialog1.setMessage("en cours de modification");
            dialog1.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map1= new HashMap<String,String>();
            map1.put("id",id);
            map1.put("nom",e1.getText().toString());
            map1.put("prénom",e2.getText().toString());
            map1.put("email",e3.getText().toString());
            map1.put("password",e4.getText().toString());


            JSONObject MOD=p.makeHttpRequest(Config.IP+"acheteurs/update.php","GET",map1);
            try {
                success1=MOD.getInt("success");
                if(success1==1){
                    Intent i1= new Intent(DetailActivity.this,ListAcheteur.class);
                    startActivity(i1);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog1.cancel();
            if (success1==1)
            {
                Toast.makeText(DetailActivity.this, "acheteur modifié avec succés", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class select extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(DetailActivity.this);
            dialog.setMessage("patientez svp");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map = new HashMap<String,String>() ;
            //la clé doit etre ientique a la clée qui existe dans le code php
            map.put("id",id);


            JSONObject O=p.makeHttpRequest(Config.IP+"acheteurs/select_one.php","GET",map);
            try {
                success=O.getInt("success");
                if (success == 1){
                    JSONArray A=O.getJSONArray("acheteur");
                    JSONObject o=A.getJSONObject(0);
                    name=o.getString("nom");
                    prenom= o.getString("prenom");
                    email=o.getString("mail");
                    passs=o.getString("pass");

                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            e1.setText(name);
            e2.setText(prenom);
            e3.setText(email);
            e4.setText(passs);
        }
    }
}