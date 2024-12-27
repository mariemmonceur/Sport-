package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projet_pfa1.Models.CommandeModel;
import com.example.projet_pfa1.Models.MonPanierModel;
import com.example.projet_pfa1.adapters.CommandeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class page_commande extends AppCompatActivity {

    RecyclerView recyclerview;
    List<CommandeModel> commandeList;
    CommandeAdapter commandeAdapter;
    SharedPreferences sp;
    String id;
    JSONParser parser=new JSONParser();

    int success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_commande);

        sp =getSharedPreferences("vendeur", Context.MODE_PRIVATE);
        id = sp.getString("id", null);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        recyclerview =findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        commandeList=new ArrayList<>();
        commandeAdapter =new CommandeAdapter(this,commandeList);
        recyclerview.setAdapter(commandeAdapter);
        new All().execute();

    }
    class All extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HashMap<String, String> MAP = new HashMap<>();
            MAP.put("id", id);
            JSONObject O = parser.makeHttpRequest(Config.IP+"commande/select_commande.php", "GET", MAP);

            try {
                if (O != null) {
                    success = O.getInt("success");
                    if (success == 1) {
                        JSONArray categories = O.getJSONArray("commande");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject pr = categories.getJSONObject(i);

                            CommandeModel m = new CommandeModel(pr.getString("id_commande"), pr.getString("id_produit"), pr.getString("id_acheteur"), pr.getString("nom_produit"), pr.getString("prix"), pr.getString("quantite"),pr.getString("image"),pr.getString("id_vendeur"),pr.getString("id_categorie"),pr.getString("date_commande"),pr.getString("adresse"));
                            commandeList.add(m);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            commandeAdapter.notifyDataSetChanged();
        }
    }
}