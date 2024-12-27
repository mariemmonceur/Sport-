package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projet_pfa1.Models.produitModel;
import com.example.projet_pfa1.adapters.produitAdapter;
import com.example.projet_pfa1.fragmentMenu.fragment_Acceuil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class detail_categorie extends AppCompatActivity {
    RecyclerView prodRecyclerView;

    List<produitModel>  produitModelList;
    produitAdapter produitadapter;
    JSONParser parser= new JSONParser();
    int success2;
    String id;


    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_categorie);
        prodRecyclerView =findViewById(R.id.prod_de_cate);


        Bundle extras = getIntent().getExtras();
        if (extras!=null){
             id=extras.getString("id");
            String nom=extras.getString("nom");
            String img=extras.getString("image");

            prodRecyclerView.setLayoutManager(new LinearLayoutManager(detail_categorie.this, RecyclerView.VERTICAL,false));
            produitModelList= new ArrayList<>();
            produitadapter = new produitAdapter(detail_categorie.this,produitModelList);
            prodRecyclerView.setAdapter(produitadapter);
            new Allp().execute();




        }
    }
    class Allp extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<String,String>();
            map.put("id",id);

            JSONObject O=parser.makeHttpRequest(Config.IP+"produits/select_category_products.php","GET",map);
            try {
                success2= O.getInt("success");
                if (success2==1){
                    JSONArray produits= O.getJSONArray("produit");
                    for(int i=0;i<produits.length();i++){
                        JSONObject produit =produits.getJSONObject(i);
                        produitModel m= new produitModel(produit.getString("nom_produit"), produit.getString("prix_produit"), produit.getString("image_produit"),produit.getString("id_produit"));
                        produitModelList.add(m);

                    }

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Mettre à jour l'adaptateur avec les données récupérées
            produitadapter.notifyDataSetChanged();
        }

    }
}