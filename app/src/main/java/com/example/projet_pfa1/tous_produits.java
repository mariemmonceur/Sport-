package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projet_pfa1.Models.produitModel;
import com.example.projet_pfa1.Models.showAllModel;
import com.example.projet_pfa1.adapters.Categorie_Adapter;
import com.example.projet_pfa1.adapters.showAllAdapter;
import com.example.projet_pfa1.fragmentMenu.fragment_Acceuil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class tous_produits extends AppCompatActivity {

    RecyclerView showAllRecycler;
    List<showAllModel> showAllModelList;
    showAllAdapter showalladapter;

    JSONParser parser= new JSONParser();
    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tous_produits);
        showAllRecycler=findViewById(R.id.liste_toud_produits);

        showAllRecycler.setLayoutManager(new GridLayoutManager(this,2));
        showAllModelList = new ArrayList<>();
        showalladapter = new showAllAdapter(tous_produits.this,showAllModelList);

        showAllRecycler.setAdapter(showalladapter);
        new Allp().execute();
    }

    class Allp extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<String,String>();
            JSONObject O=parser.makeHttpRequest(Config.IP+"produits/produit_complet.php","GET",map);
            try {
                success= O.getInt("success");
                if (success==1){
                    JSONArray produits= O.getJSONArray("produit");
                    for(int i=0;i<produits.length();i++){
                        JSONObject produit =produits.getJSONObject(i);
                        showAllModel m= new showAllModel(produit.getString("nom"),produit.getString("prix_produit"),produit.getString("image"),produit.getString("id"),produit.getString("marque_produit"),produit.getString("name"));
                        showAllModelList.add(m);

                    }

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (success==1) {
                Toast.makeText(tous_produits.this, "SUCCES"+showAllModelList.get(1).getName(), Toast.LENGTH_SHORT).show();
                showalladapter.notifyDataSetChanged();
            } else {
                // Gérer l'échec de la récupération des données, si nécessaire
            }
    }}}
