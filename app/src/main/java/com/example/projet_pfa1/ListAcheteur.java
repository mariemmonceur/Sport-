package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ListAcheteur extends AppCompatActivity {

    ListView ls;
    ProgressDialog p;
    JSONParser parser= new JSONParser();
    int success;

    ArrayList<HashMap<String,String>> values = new ArrayList<HashMap<String,String>>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_acheteur);
        ls=findViewById(R.id.lst);
        new All().execute();

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t= view.findViewById(R.id.idl);
                Intent I = new Intent(ListAcheteur.this,DetailActivity.class);
                I.putExtra("id",t.getText().toString());
                startActivity(I);
            }
        });
    }

    class All extends AsyncTask<String ,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p= new ProgressDialog(ListAcheteur.this);
            p.setMessage("patientez svp");
            p.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> MAP= new HashMap<String,String>();
            JSONObject O =parser.makeHttpRequest(Config.IP+"acheteurs/all.php","GET",MAP);

            try {
                success =O.getInt("success");
                if (success==1)
                {

                    JSONArray acheteurs = O.getJSONArray("acheteur");
                    for (int i=0;i<acheteurs.length();i++)
                    {
                        JSONObject acheteur =acheteurs.getJSONObject(i);
                        HashMap<String,String> M= new HashMap<String,String>();
                        M.put("id",acheteur.getString("id"));
                        M.put("nom",acheteur.getString("nom"));
                        M.put("prenom",acheteur.getString("prenom"));
                        M.put("email",acheteur.getString("email"));
                        M.put("pass",acheteur.getString("pass"));


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
            if (success==1){

                SimpleAdapter adapter= new SimpleAdapter(ListAcheteur.this,values,R.layout.item,
                        new String[]{"id","nom","prenom","email","pass"},
                        new int[]{R.id.idl,R.id.noml,R.id.prénoml,R.id.emaill,R.id.pass});
                ls.setAdapter(adapter);
            }

        }
    }
}