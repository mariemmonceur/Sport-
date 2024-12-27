package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class vendeur_list extends AppCompatActivity {
    ListView l;
    TextView T;
    ArrayList<HashMap<String,String>> values = new ArrayList<HashMap<String,String>>() ;
    ProgressDialog p;
    JSONParser parser = new JSONParser();
    int success;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendeur_list);
        l=findViewById(R.id.lst2);


        new  All().execute();
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t1= view.findViewById(R.id.idl);
                TextView t2= view.findViewById(R.id.noml);
                TextView t3= view.findViewById(R.id.prénoml);
                TextView t4= view.findViewById(R.id.emaill);
                TextView t5= view.findViewById(R.id.tel);
                TextView t6= view.findViewById(R.id.password);
                TextView t7= view.findViewById(R.id.raisonv);

                Intent I = new Intent(vendeur_list.this,DetailActivityvendeur.class);
                I.putExtra("id",t1.getText().toString());
                I.putExtra("nom",t2.getText().toString());
                I.putExtra("prenom",t3.getText().toString());
                I.putExtra("email",t4.getText().toString());
                I.putExtra("tel",t5.getText().toString());
                I.putExtra("password",t6.getText().toString());
                I.putExtra("raison",t7.getText().toString());


                startActivity(I);
            }
        });
    }

    class All extends AsyncTask<String ,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p= new ProgressDialog(vendeur_list.this);
            p.setMessage("patientez svp");
            p.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> MAP= new HashMap<String,String>();
            JSONObject O =parser.makeHttpRequest(Config.IP+"vendeurs/allv.php","GET",MAP);

            try {
                success =O.getInt("success");
                if (success==1)
                {

                    JSONArray vendeurs = O.getJSONArray("vendeur");
                    for (int i=0;i<vendeurs.length();i++)
                    {
                        JSONObject vendeur =vendeurs.getJSONObject(i);
                        HashMap<String,String> M= new HashMap<String,String>();
                        M.put("id",vendeur.getString("id"));
                        M.put("nom",vendeur.getString("nom"));
                        M.put("prenom",vendeur.getString("prenom"));
                        M.put("tel",vendeur.getString("tel"));
                        M.put("email",vendeur.getString("email"));
                        M.put("password",vendeur.getString("password"));
                        M.put("raison_social",vendeur.getString("raison_social"));


                        values.add(M);
                    }

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.cancel();
            SimpleAdapter adapter= new SimpleAdapter(vendeur_list.this,values,R.layout.itemvendeur,
                    new String[]{"id","nom","prenom","email","tel","password","raison_social"},
                    new int[]{R.id.idl,R.id.noml,R.id.prénoml,R.id.emaill,R.id.tel,R.id.password,R.id.raisonv});
            l.setAdapter(adapter);
        }
    }


}