package com.example.projet_pfa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class espace_vendeur extends AppCompatActivity {

    Button add;
    ListView l;
    ArrayList<HashMap<String,String>> values =new ArrayList<>();
    ProgressDialog p;
    int success;
    JSONParser parser= new JSONParser();
    SharedPreferences sp;
    SharedPreferences.Editor edit;
    String id;
    ImageView bell;//pour allez voir les commandes

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_vendeur);

        // Demander la permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            // La permission est déjà accordée
            // Vous pouvez exécuter votre code ici
            initialize();
        }
    }

    private void initialize() {
        add = findViewById(R.id.button2);
        l = findViewById(R.id.listee);
        bell=findViewById(R.id.commande);



        sp = getSharedPreferences("vendeur", MODE_PRIVATE);
        id = sp.getString("id", null);
        new All().execute();
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t= view.findViewById(R.id.textView13);
                Intent I = new Intent(espace_vendeur.this,MainActivity0.class);
                I.putExtra("id",t.getText().toString());
                startActivity(I);
            }
        });

        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i= new Intent(espace_vendeur.this,page_commande.class) ;
               startActivity(i);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(espace_vendeur.this, ajout_produit.class);
                startActivity(i);
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
                Toast.makeText(this, "La permission de lire le stockage externe est nécessaire pour cette application.",Toast.LENGTH_LONG).show();
            }
        }
    }

    class All extends AsyncTask<String ,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p= new ProgressDialog(espace_vendeur.this);
            p.setMessage("patientez svp");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> MAP= new HashMap<>();
            MAP.put("id",id);
            JSONObject O =parser.makeHttpRequest(Config.IP+"produits/select_produits.php","GET",MAP);

            try {
                success =O.getInt("success");
                if (success==1) {
                    JSONArray produits = O.getJSONArray("produit");
                    for (int i=0;i<produits.length();i++) {
                        JSONObject pr =produits.getJSONObject(i);
                        HashMap<String,String> M= new HashMap<>();
                        M.put("id_produit",pr.getString("id_produit"));
                        M.put("nom_produit",pr.getString("nom_produit"));
                        M.put("marque_produit",pr.getString("marque_produit"));
                        M.put("prix_produit",pr.getString("prix_produit"));
                        M.put("image_produit",pr.getString("image_produit"));
                        M.put("quantite_produit",pr.getString("quantite_produit"));
                        M.put("id_vendeur",pr.getString("id_vendeur"));
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
            SimpleAdapter adapter = new SimpleAdapter(espace_vendeur.this, values, R.layout.item_list_produit,
                    new String[]{"id_produit", "nom_produit", "marque_produit", "prix_produit", "quantite_produit", "image_produit"},
                    new int[]{R.id.textView13, R.id.textView12, R.id.textView14, R.id.textView17, R.id.textView16, R.id.imageView_produit});

            // Bind adapter to listview
            l.setAdapter(adapter);

            // Load images using Glide
            for (int i = 0; i < l.getChildCount(); i++) {
                View listItem = l.getChildAt(i);
                ImageView imageView = listItem.findViewById(R.id.imageView_produit);
                HashMap<String, String> item = values.get(i);
                String imageUrl = item.get("image_produit");

                Glide.with(espace_vendeur.this)
                        .load(imageUrl)
                        .into(imageView);
            }
        }
    }
}
