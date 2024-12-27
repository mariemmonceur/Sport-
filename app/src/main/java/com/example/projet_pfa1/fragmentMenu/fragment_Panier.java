package com.example.projet_pfa1.fragmentMenu;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.projet_pfa1.Config;
import com.example.projet_pfa1.JSONParser;
import com.example.projet_pfa1.Models.MonPanierModel;
import com.example.projet_pfa1.R;
import com.example.projet_pfa1.adapters.MonPanierAdapter;
import com.example.projet_pfa1.address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class fragment_Panier extends Fragment {

   double prix_panier;
    RecyclerView recyclerview;
    List<MonPanierModel> cartModelList;
    MonPanierAdapter panierAdapter;
    ProgressDialog p;
    int success;
    String id;
    JSONParser parser = new JSONParser();
    SharedPreferences sp;
    TextView overAllAmount;
    Button commande;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment__panier, container, false);

        sp = requireActivity().getSharedPreferences("acheteur", Context.MODE_PRIVATE);
        id = sp.getString("id", null);
        //le prix du panier
        //GET DATA FROM panier
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter("mytotalamount"));


        commande= root.findViewById(R.id.echri);
        overAllAmount=root.findViewById(R.id.id_prixtotal);

        //recycler
        recyclerview = root.findViewById(R.id.cart_rec);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //list de panier
        cartModelList = new ArrayList<>();
        //adapter de panier
        panierAdapter = new MonPanierAdapter(getContext(), cartModelList);
        recyclerview.setAdapter(panierAdapter);
        new All().execute();

       commande.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i= new Intent(getContext(), address.class);
               startActivity(i);
           }
       });
        return root;
    }

    public BroadcastReceiver mMessageReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            double totalBill= intent.getDoubleExtra("prixtotale",0);
            overAllAmount.setText(totalBill+ " dt");


        }
    };

    class All extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HashMap<String, String> MAP = new HashMap<>();
            MAP.put("id", id);
            JSONObject O = parser.makeHttpRequest(Config.IP+"paniers/AllPanier_acheteur.php", "GET", MAP);

            try {
                if (O != null) {
                    success = O.getInt("success");
                    if (success == 1) {
                        JSONArray categories = O.getJSONArray("panier");
                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject pr = categories.getJSONObject(i);

                            MonPanierModel m = new MonPanierModel(pr.getString("nom"), pr.getString("prix"), pr.getString("id_produit"), pr.getString("temps"), pr.getString("date"), pr.getString("quantite"),pr.getString("image"));
                            cartModelList.add(m);
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
            panierAdapter.notifyDataSetChanged();
        }
    }
}
