 package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projet_pfa1.Models.AdressModel;
import com.example.projet_pfa1.Models.MonPanierModel;
import com.example.projet_pfa1.adapters.AdressAdapter;
import com.example.projet_pfa1.fragmentMenu.fragment_Panier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class address extends AppCompatActivity implements AdressAdapter.SelectedAdress {


    Button btn,paymentBtn;
    RecyclerView recyclerView;
    private List<AdressModel>adressList;
    private AdressAdapter addressAdapter;
    Toolbar toolbar;
    JSONParser parser= new JSONParser();
    int success;
    String mAdress= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //toolbar
        toolbar= findViewById(R.id.address_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recyclerView/payment/adress
        recyclerView=findViewById(R.id.address_recycler);
        paymentBtn=findViewById(R.id.payment_btn);
        btn=findViewById(R.id.add_address_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adressList=new ArrayList<>();
        addressAdapter =new AdressAdapter(adressList,getApplicationContext(),this);
        recyclerView.setAdapter(addressAdapter);
        new All().execute();





        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( address.this, paymentActivity.class);
                i.putExtra("adresse",mAdress);
                startActivity(i);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( address.this, addAdress.class);
                startActivity(i);
            }
        });
    }

     class All extends AsyncTask<Void, Void, Void> {

         @Override
         protected Void doInBackground(Void... voids) {
             HashMap<String, String> MAP = new HashMap<>();
             MAP.put("id","28");
             JSONObject O = parser.makeHttpRequest(Config.IP+"paniers/allAdress.php", "GET", MAP);

             try {
                 if (O != null) {
                     success = O.getInt("success");
                     if (success == 1) {
                         JSONArray addresses = O.getJSONArray("adresse");
                         for (int i = 0; i < addresses.length(); i++) {
                             JSONObject addressJson = addresses.getJSONObject(i);
                             String userAddress = addressJson.getString("adresse");
                             AdressModel m = new AdressModel();
                             m.setUserAdress(userAddress);
                             adressList.add(m);
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
             addressAdapter.notifyDataSetChanged(); // Notify adapter after adding data
         }
     }


     @Override
     public void setAdress(String adress) {
            mAdress =adress;
     }
 }