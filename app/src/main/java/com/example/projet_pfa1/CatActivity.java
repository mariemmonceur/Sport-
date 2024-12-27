package com.example.projet_pfa1;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CatActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
   ImageView add;
    int success;
    ListView l;
    ArrayList<HashMap<String,String>> values =new ArrayList<>();
    ProgressDialog p;
    JSONParser parser=new JSONParser();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            // La permission est déjà accordée
            // Vous pouvez exécuter votre code ici
            initialize();
        }}

    private void initialize()
    {
        //ajout dun produit
        add= findViewById(R.id.AJOUTCAT);
        l=findViewById(R.id.listi);

        new All().execute();


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i=new Intent(CatActivity.this,ajout_categorie.class);
                startActivity(i);
            }

        });
        //liste des produits

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t= view.findViewById(R.id.textView4) ;
                Intent I = new Intent(CatActivity.this,gerer_categorie.class);
                I.putExtra("id_category",t.getText().toString());
                startActivity(I);
            }
        });
    }

    // Gérer la réponse de l'utilisateur à la demande de permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // La permission a été accordée, vous pouvez exécuter votre code ici
                initialize();
            } else {
                // La permission a été refusée par l'utilisateur
                // Vous pouvez afficher un message d'erreur ou demander à nouveau la permission
            }
        }
    }

    class All extends AsyncTask<String ,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p= new ProgressDialog(CatActivity.this);
            p.setMessage("patientez svp");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> MAP= new HashMap<>();
            JSONObject O =parser.makeHttpRequest(Config.IP+"categories/allc.php","GET",MAP);

            try {
                success =O.getInt("success");
                if (success==1) {
                    JSONArray categories = O.getJSONArray("categorie");
                    for (int i=0;i<categories.length();i++) {
                        JSONObject pr =categories.getJSONObject(i);
                        HashMap<String,String> M= new HashMap<>();
                        M.put("id_category",pr.getString("id_category"));
                        M.put("img_url",pr.getString("img_url"));
                        M.put("name",pr.getString("name"));
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

            // Set up SimpleAdapter
            SimpleAdapter adapter = new SimpleAdapter(CatActivity.this, values, R.layout.item_list_categories,
                    new String[]{"id_category", "img_url", "name"},
                    new int[]{R.id.textView4, R.id.imageView2, R.id.textView6});

            // Bind adapter to listview
            l.setAdapter(adapter);

            // Load images using Glide
            for (int i = 0; i < l.getChildCount(); i++) {
                View listItem = l.getChildAt(i);
                ImageView imageView = listItem.findViewById(R.id.imageView_produit);
                HashMap<String, String> item = values.get(i);
                String imageUrl = item.get("name");

                Glide.with(CatActivity.this)
                        .load(imageUrl)
                        .into(imageView);
            }
        }
    }





    }
