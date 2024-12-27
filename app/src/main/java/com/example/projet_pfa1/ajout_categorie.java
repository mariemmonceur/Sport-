package com.example.projet_pfa1;

;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ajout_categorie extends AppCompatActivity {
    private static final int REQUEST_IMAGE_GALLERY = 10;
    Button choix,ajout;
    EditText nom;
    String imagePath;
    ImageView imageView;
    JSONParser parser =new JSONParser();
    int success;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_categorie);
        choix=findViewById(R.id.choiximcat);
        ajout=findViewById(R.id.ajoutcate);
        imageView=findViewById(R.id.image_view);
        nom=findViewById(R.id.nomdecat);


        //................importer une image.............//
        choix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new add().execute();
            }
        });

    }
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK && data != null)
        {
            Uri imageUri = data.getData();
            imagePath = imageUri.toString();
            imageView.setImageURI(imageUri);

        }

    }

    class  add extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {


            HashMap<String,String> map=new HashMap<String,String>();
            map.put("name",nom.getText().toString());
            map.put("img_url",imagePath);

            JSONObject object=parser.makeHttpRequest(Config.IP+"categories/addc.php","GET",map);
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
                Toast.makeText(ajout_categorie.this, "PRODUIT AJOUT2 AVEC SUCCES", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(ajout_categorie.this,CatActivity.class);
                startActivity(i);


            }
        }
    }
}