package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.projet_pfa1.fragmentMenu.fragment_Acceuil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class detailproduit extends AppCompatActivity {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView t1, t2, t4,qte;
    ImageView t3,plus,minuis;
    Button addToCart,buyNow;
    String id, idAcheteur;
    String saveCurrentTime, saveCurrentDate;
    JSONParser parser = new JSONParser();
    int success;
    String img;
    String prix;
    int totalqte =1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailproduit);
        t1 = findViewById(R.id.nomprod);
        t4 = findViewById(R.id.marquee);
        t2 = findViewById(R.id.prixx);
        t3 = findViewById(R.id.improd);
        qte=findViewById(R.id.quantité);
        plus=findViewById(R.id.add);
        minuis=findViewById(R.id.minus);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow=findViewById(R.id.buynow);

        sp = getSharedPreferences("acheteur", Context.MODE_PRIVATE);
        idAcheteur = sp.getString("id", null);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            prix = extras.getString("prix");
            id = extras.getString("id");
            String nom = extras.getString("nom");
            img = extras.getString("img");
            String marque = extras.getString("marque");
            t1.setText(nom);
            t2.setText(prix);
            t4.setText(marque);

            // Charger l'image dans ImageView en utilisant Glide
            Glide.with(this)
                    .load(img) // URL de l'image
                    .transition(DrawableTransitionOptions.withCrossFade()) // optionnel
                    .into(t3); // ImageView pour charger l'image


            //byu now :
            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   startActivity(new Intent(detailproduit.this,address.class));
                }
            });



            // Ajouter au panier
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToCart();
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(totalqte<10)
                    {
                        totalqte++;
                        qte.setText(String.valueOf(totalqte));
                        // Convertir le prix en double et calculer le montant total
                        double prixDouble = Double.parseDouble(prix);
                        double montantTotal = prixDouble * totalqte;
                        Toast.makeText(detailproduit.this, String.valueOf(montantTotal), Toast.LENGTH_SHORT).show();
                        t2.setText(String.valueOf(montantTotal));
                    }
                }
            });

            minuis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(totalqte>1)
                    {
                        totalqte--;
                        qte.setText(String.valueOf(totalqte));
                        // Convertir le prix en double et calculer le montant total
                        double prixDouble = Double.parseDouble(prix);
                        double montantTotal = prixDouble * totalqte;
                        Toast.makeText(detailproduit.this, String.valueOf(montantTotal), Toast.LENGTH_SHORT).show();
                        t2.setText(String.valueOf(montantTotal));

                    }
                }
            });




        }
    }

    private void addToCart() {
        // Calendrier pour la date
        Calendar calForDate = Calendar.getInstance();

        // Enregistrer la date
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        // Enregistrer l'heure
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
        new AddToCart().execute();
    }

    class AddToCart extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("nom", t1.getText().toString());
            cartMap.put("prix", t2.getText().toString());
            cartMap.put("temps", saveCurrentTime);
            cartMap.put("date", saveCurrentDate);
            cartMap.put("id_produit", id);
            cartMap.put("quantité",qte.getText().toString());
            cartMap.put("id", idAcheteur);
            cartMap.put("image",img);
            JSONObject o = parser.makeHttpRequest(Config.IP+"paniers/addpanier.php", "GET", cartMap);

            if (o != null) {
                try {
                    success = o.getInt("success");
                } catch (JSONException e) {
                    // Gérer l'exception JSON
                    e.printStackTrace();
                }
            } else {
                // Gérer le cas où JSONObject est null
                success = -1; // Définir une valeur par défaut pour success indiquant un échec
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (success == 1) {
                Toast.makeText(detailproduit.this, "Produit ajouté avec succès au panier", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
