package com.example.projet_pfa1;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_pfa1.Models.MonPanierModel;
import com.example.projet_pfa1.adapters.MonPanierAdapter;
import com.example.projet_pfa1.fragmentMenu.fragment_Acceuil;
import com.example.projet_pfa1.fragmentMenu.fragment_Panier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class paymentActivity extends AppCompatActivity {
    SharedPreferences sp;
    String id;
    TextView overAllAmount,all;
    RecyclerView recyclerview;
    List<MonPanierModel> cartModelList;
    MonPanierAdapter panierAdapter;
    JSONParser parser= new JSONParser();
    int success,ooo;
    TextView adresse;
    Button confirm;
    String add,mail;
    ScrollView totalart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        LocalBroadcastManager.getInstance(paymentActivity.this).registerReceiver(mMessageReceiver, new IntentFilter("mytotalamount"));


        totalart=findViewById(R.id.totalart);
        sp = getSharedPreferences("acheteur", Context.MODE_PRIVATE);
        id = sp.getString("id", null);
        mail=sp.getString("email",null);
        Toast.makeText(this, mail, Toast.LENGTH_SHORT).show();

        Bundle i = getIntent().getExtras();
        add = i.getString("adresse");
        adresse=findViewById(R.id.textView30);
        adresse.setText(add);
        all=findViewById(R.id.textView26);
        confirm=findViewById(R.id.button5);





        overAllAmount=findViewById(R.id.textView20);

        //recycler
        recyclerview = findViewById(R.id.view3);
        recyclerview.setLayoutManager(new LinearLayoutManager(paymentActivity.this));
        //list de panier
        cartModelList = new ArrayList<>();
        //adapter de panier
        panierAdapter = new MonPanierAdapter(paymentActivity.this, cartModelList);
        recyclerview.setAdapter(panierAdapter);
        new All().execute();

        confirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                new add().execute();
                new supp().execute();
                new email().execute();
               Intent i =new Intent(paymentActivity.this,AcceuilActivity2.class);
               startActivity(i);


            }
        });


    }

    class  email extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map=new HashMap<String,String>();

            map.put("mail",mail);

            JSONObject object=parser.makeHttpRequest(Config.IP+"work/mail.php","GET",map);


            return null;
        }



    }
    class supp extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String>map=new HashMap<String,String>();
            map.put("id",id);

            JSONObject o=parser.makeHttpRequest(Config.IP+"paniers/supp_panier.php","GET",map);
            try {
                ooo= o.getInt("success");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (ooo==1)
            {
                Toast.makeText(paymentActivity.this, "Panier est vide ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public BroadcastReceiver mMessageReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            double totalBill= intent.getDoubleExtra("prixtotale",0);
            overAllAmount.setText(totalBill+ " dt");
            double totall=totalBill+8;
            all.setText( totall +" dt");
            


        }
    };

    class  add extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map=new HashMap<String,String>();
            map.put("id",id);
            map.put("adresse",add);

            JSONObject object=parser.makeHttpRequest(Config.IP+"commande/ajout_commande.php","GET",map);
            try {
                success=object.getInt("success");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(success==1){
                Toast.makeText(paymentActivity.this, "votre commande est confirmé", Toast.LENGTH_SHORT).show();



            }
        }
    }
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