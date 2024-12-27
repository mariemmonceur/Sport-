package com.example.projet_pfa1;



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

public class gerer_categorie extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY =5 ;
    Button supp,choix,modifier;
    String id;
    int success,success4;
    EditText nomdecat;
    ImageView image;
    String imagePath;

    JSONParser parser =new JSONParser();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_categorie);
        supp= findViewById(R.id.button4);
        nomdecat =findViewById(R.id.nomdecat);
        choix=findViewById(R.id.choiximcat);
        image=findViewById(R.id.image_viedw);
        modifier=findViewById(R.id.button3);


        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            id=extras.getString("id_category");
            Toast.makeText(this, id.toString(), Toast.LENGTH_SHORT).show();
            choix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        openGallery();
                }
            });


            supp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   new supprime().execute();
                }
            });

            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //update de categorie
                    new modif().execute();
                }
            });



    }
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
            image.setImageURI(imageUri);

        }}
class supprime extends AsyncTask<String,String,String>
{

    @Override
    protected String doInBackground(String... strings) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("id",id);
        JSONObject O=parser.makeHttpRequest(Config.IP+"categories/deletec.php","GET",map);
        try {
            success=O.getInt("success");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (success==1){
            Toast.makeText(gerer_categorie.this, "vategorie supprimée avec succces ", Toast.LENGTH_SHORT).show();
            Intent i= new Intent(gerer_categorie.this,CatActivity.class);
            startActivity(i);
        }
    }
}

class modif extends AsyncTask<String,String,String>
{

    @Override
    protected String doInBackground(String... strings) {
        HashMap<String,String>map= new HashMap<String,String>();
        map.put("id",id);
        map.put("nom",nomdecat.getText().toString());
        map.put("image",imagePath);
        JSONObject o =parser.makeHttpRequest(Config.IP+"categories/update.php","GET",map);
        try {
            success4=o.getInt("success");

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(success4==1){
            Toast.makeText(gerer_categorie.this, "modification avec success", Toast.LENGTH_SHORT).show();
            Intent I=new Intent(gerer_categorie.this,CatActivity.class);
            startActivity(I);
        }
    }
}
}