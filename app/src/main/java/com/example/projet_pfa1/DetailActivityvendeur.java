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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DetailActivityvendeur extends AppCompatActivity {

    int success1,success;
    ProgressDialog dialog1;
    EditText e1,e2,e3,e4,e5,e6;
    String id,nom,prenom,raisonsocial,tel,email,pass;
    Button modifier,supprimer;
    JSONParser p= new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activityvendeur);
         modifier=findViewById(R.id.button3);
         supprimer=findViewById(R.id.button4);
        e1=findViewById(R.id.nomd);
        e2=findViewById(R.id.prenomd);
        e3=findViewById(R.id.emaild);
        e4=findViewById(R.id.emaild2);
        e5=findViewById(R.id.pass2);
        e6=findViewById(R.id.pass3);

        Bundle extras = getIntent().getExtras();
        if (extras!=null)
        {
            id=extras.getString("id");
            nom=extras.getString("nom");
            prenom=extras.getString("prenom");
            email=extras.getString("email");
            tel=extras.getString("tel");
            pass=extras.getString("password");
            raisonsocial=extras.getString("raison");

            e1.setText(nom);
            e2.setText(prenom);
            e3.setText(email);
            e4.setText(tel);
            e5.setText(pass);
            e6.setText(raisonsocial);





            modifier.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

                   new modifier().execute();

               }
           });
            supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new supprimer().execute();
                }
            });

        }
    }

    class modifier extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map1= new HashMap<String,String>();
            map1.put("id",id);
            map1.put("nom",e1.getText().toString());
            map1.put("prénom",e2.getText().toString());
            map1.put("email",e5.getText().toString());
            map1.put("pass",e6.getText().toString());
            map1.put("tel",e4.getText().toString());
            map1.put("r_social",e3.getText().toString());


            JSONObject MOD=p.makeHttpRequest(Config.IP+"vendeur/updatev.php","GET",map1);
            try {
                success1=MOD.getInt("success");
                if(success1==1){
                    Intent i1= new Intent(DetailActivityvendeur.this,vendeur_list.class);
                    startActivity(i1);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (success1==1)
            {
                Toast.makeText(DetailActivityvendeur.this, "vendeur modifié", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class supprimer  extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog1= new ProgressDialog(DetailActivityvendeur.this);
            dialog1.setMessage("en cours de suppression");
            dialog1.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map1= new HashMap<String,String>();
            map1.put("id",id);


            JSONObject MOD=p.makeHttpRequest(Config.IP+"vendeurs/deletev.php","GET",map1);
            try {
                success=MOD.getInt("success");


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog1.cancel();
            if (success==1)
            {
                Toast.makeText(DetailActivityvendeur.this, "ACHETEUR SUPPRIMe", Toast.LENGTH_SHORT).show();
                Intent i1= new Intent(DetailActivityvendeur.this,vendeur_list.class);
                startActivity(i1);
            }

        }
    }


}