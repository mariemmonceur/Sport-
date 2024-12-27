package com.example.projet_pfa1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.MediaStore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ajout_produit extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY =1 ;
    Button bring,ajouter;
    String imagePath;
    ImageView imageView;
    String idc;
    ListView l;
    SharedPreferences sp;
    int success,success1;
    SharedPreferences.Editor edit;
JSONParser parser =new JSONParser();
ProgressDialog p;
    ArrayList<HashMap<String,String>> values =new ArrayList<>();



    EditText nom,marque,prix,qte;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_produit);
        imageView = findViewById(R.id.image_view);
        nom=findViewById(R.id.editTextText);
        marque=findViewById(R.id.editTextText4);
        qte=findViewById(R.id.editTextNumber);
        prix=findViewById(R.id.editTextText5);
        bring=findViewById(R.id.button_choose_image);
        ajouter=findViewById(R.id.button_add);
        l=findViewById(R.id.listaa);
        sp=getSharedPreferences("vendeur",MODE_PRIVATE);
        new All().execute();

        //................importer une image.............//
        bring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView v = view.findViewById(R.id.textView4);
                if (v != null) { // Check if TextView is not null
                    idc = v.getText().toString();

                    Toast.makeText(ajout_produit.this, idc, Toast.LENGTH_SHORT).show();
                } else {
                    // Handle the case where TextView is not found in the clicked view
                    // You may display an error message or handle it in any other appropriate way
                    // For example:
                    Toast.makeText(ajout_produit.this, "TextView not found in clicked item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idc != null) {
                    new add().execute();
                    Toast.makeText(ajout_produit.this, idc, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ajout_produit.this, "Please select a category", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imagePath = imageUri.toString();
            imageView.setImageURI(imageUri);


        }

    }

    class  add extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String>map=new HashMap<String,String>();
            map.put("nom_produit",nom.getText().toString());
            map.put("marque_produit",marque.getText().toString());
            map.put("prix_produit",prix.getText().toString());
            map.put("image_produit",imagePath);
            map.put("quantite_produit",qte.getText().toString());
            map.put("id_category",idc);

            String id=sp.getString("id",null);
            map.put("id_vendeur",id);
            JSONObject object=parser.makeHttpRequest(Config.IP+"produits/addp.php","GET",map);
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
                Toast.makeText(ajout_produit.this, "PRODUIT AJOUT2 AVEC SUCCES", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(ajout_produit.this,espace_vendeur.class);
                    startActivity(i);


            }
        }
    }
    class All extends AsyncTask<String ,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p= new ProgressDialog(ajout_produit.this);
            p.setMessage("patientez svp");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> MAP= new HashMap<>();
            JSONObject O =parser.makeHttpRequest(Config.IP+"categories/allc.php","GET",MAP);

            try {
                success1 =O.getInt("success");
                if (success1==1) {
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
            SimpleAdapter adapter = new SimpleAdapter(ajout_produit.this, values, R.layout.listecategoriedansproduit,
                    new String[]{"id_category", "img_url", "name"},
                    new int[]{R.id.textView4, R.id.imageView2, R.id.textView6});

            // Bind adapter to listview
            l.setAdapter(adapter);

            // Load images using Glide
            for (int i = 0; i < l.getChildCount(); i++) {
                View listItem = l.getChildAt(i);
                ImageView imageView = listItem.findViewById(R.id.imageView2);
                HashMap<String, String> item = values.get(i);
                String imageUrl = item.get("img_url");

                Glide.with(ajout_produit.this)
                        .load(imageUrl)
                        .into(imageView);
            }

        }
    }


}