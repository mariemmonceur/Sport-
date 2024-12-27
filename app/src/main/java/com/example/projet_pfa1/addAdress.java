package com.example.projet_pfa1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addAdress extends AppCompatActivity {

    EditText name, adress, city, postcode, phoneNumber;
    Toolbar toolbar;
    Button btn;
    Map<String, String> map;
    SharedPreferences sp;
    String id;
    JSONParser parser = new JSONParser();
    String[] cities = {"Tunis", "Sfax", "Sousse", "Bizerte", "Nabeul", "Gabès", "Kairouan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);

        sp = getSharedPreferences("acheteur", Context.MODE_PRIVATE);
        id = sp.getString("id", null);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        // Toolbar:
        toolbar = findViewById(R.id.add_address_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.ad_name);
        adress = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);
        postcode = findViewById(R.id.ad_code);
        phoneNumber = findViewById(R.id.ad_phone);
        btn = findViewById(R.id.ad_add_address);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String userCity = city.getText().toString();
                String pusercode = postcode.getText().toString();
                String phone = phoneNumber.getText().toString();
                String adres = adress.getText().toString();

                String final_adress = "";
                if (!username.isEmpty()) {
                    final_adress += username + " ,";
                }
                if (!userCity.isEmpty()) {
                    final_adress += userCity + " ,";
                }
                if (!pusercode.isEmpty()) {
                    final_adress += pusercode + " ,";
                }
                if (!phone.isEmpty()) {
                    final_adress += phone + " ,";
                }
                if (!adres.isEmpty()) {
                    final_adress += adres + " ,";
                }
                if (!username.isEmpty() && !userCity.isEmpty() && !pusercode.isEmpty() && !phone.isEmpty() && !adres.isEmpty()) {
                    map = new HashMap<>();
                    map.put("useradress", final_adress);
                    Toast.makeText(addAdress.this, map.get("useradress"), Toast.LENGTH_SHORT).show();
                    new AddAddressTask().execute();
                    finish();
                }
            }
        });
    }

    class AddAddressTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> mapa = new HashMap<>();
            mapa.put("id", id);
            mapa.put("adresse", map.get("useradress"));
            JSONObject o = parser.makeHttpRequest(Config.IP+"paniers/ajoutAdresse.php", "GET", mapa);
            return null;
        }
    }
}
