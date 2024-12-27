package com.example.projet_pfa1.fragmentMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.projet_pfa1.Config;
import com.example.projet_pfa1.Models.CategoryModel;
import com.example.projet_pfa1.R;
import com.example.projet_pfa1.adapters.Categorie_Adapter;
import com.example.projet_pfa1.JSONParser;
import com.example.projet_pfa1.adapters.produitAdapter;
import com.example.projet_pfa1.Models.produitModel;

import com.example.projet_pfa1.tous_produits;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fragment_Acceuil extends Fragment {

    ProgressDialog p;
    private JSONParser parser = new JSONParser();
    int success,success2;
    private ImageSlider imageSlider;
    private ArrayList<HashMap<String, String>> values = new ArrayList<>();

    RecyclerView catRecyclerView,prodRecyclerView;



    produitAdapter produitadapter;
    List<produitModel>  produitmodel;


    JSONParser parser2=new JSONParser();

    //CATEGORY RECYCLER
    Categorie_Adapter Categoryadapter;
    List<CategoryModel> categorieModelList;
    TextView see_all_cat,see_all_produits;

    public fragment_Acceuil() {
        // Constructeur public vide requis par les fragments
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate le layout pour ce fragment
        View root = inflater.inflate(R.layout.fragment__acceuil, container, false);


        catRecyclerView = root.findViewById(R.id.rec_category);
        prodRecyclerView =root.findViewById(R.id.new_product_rec);

        see_all_cat=root.findViewById(R.id.category_see_all);
        see_all_produits=root.findViewById(R.id.newProducts_see_all);

        // Image Slider
        imageSlider = root.findViewById(R.id.image_slider);
        new FetchDataTask().execute();


        //category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categorieModelList = new ArrayList<>();
        Categoryadapter = new Categorie_Adapter(getContext(), categorieModelList);

        catRecyclerView.setAdapter(Categoryadapter);
        new All().execute();

        //produit

       prodRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        produitmodel= new ArrayList<>();
        produitadapter = new produitAdapter(getContext(),produitmodel);
        prodRecyclerView.setAdapter(produitadapter);
        new Allp().execute();


        //voir touts les produits disponible sur le site
        see_all_produits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),tous_produits.class);
                startActivity(i);
            }
        });





        return root;
    }
    class Allp extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String> map=new HashMap<String,String>();
            JSONObject O=parser.makeHttpRequest(Config.IP+"produits/allp.php","GET",map);
            try {
                success2= O.getInt("success");
                if (success2==1){
                    JSONArray produits= O.getJSONArray("produit");
                    for(int i=0;i<produits.length();i++){
                        JSONObject produit =produits.getJSONObject(i);
                        produitModel m= new produitModel(produit.getString("nom"), produit.getString("prix_produit"), produit.getString("image"),produit.getString("id"));
                        produitmodel.add(m);

                    }

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    class All extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getContext());
            p.setMessage("patientez svp");
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> MAP = new HashMap<>();
            JSONObject O = parser.makeHttpRequest(Config.IP+"categories/allc.php", "GET", MAP);

            try {
                success = O.getInt("success");
                if (success == 1) {
                    JSONArray categories = O.getJSONArray("categorie");
                    for (int i = 0; i < categories.length(); i++) {
                        JSONObject pr = categories.getJSONObject(i);

                        CategoryModel m = new CategoryModel(pr.getString("img_url"), pr.getString("name"), pr.getString("id_category"));
                        categorieModelList.add(m);
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
        }
    }

    class FetchDataTask extends AsyncTask<Void, Void, List<SlideModel>> {

        @Override
        protected List<SlideModel> doInBackground(Void... voids) {
            HashMap<String, String> map = new HashMap<>();
            JSONObject a = parser.makeHttpRequest(Config.IP+"produits/allp.php", "GET", map);

            try {
                int success = a.getInt("success");
                if (success == 1) {
                    JSONArray produits = a.getJSONArray("produit");
                    for (int i = 0; i < produits.length(); i++) {
                        JSONObject prod = produits.getJSONObject(i);
                        HashMap<String, String> M = new HashMap<>();
                        M.put("id", prod.getString("id"));
                        M.put("image", prod.getString("image"));

                        values.add(M);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            List<SlideModel> slideModels = new ArrayList<>();
            for (HashMap<String, String> map1 : values) {
                String imageUrl = map1.get("image");
                slideModels.add(new SlideModel(imageUrl, "Remise sur les chaussures", ScaleTypes.CENTER_CROP));
            }
            return slideModels;
        }

        @Override
        protected void onPostExecute(List<SlideModel> slideModels) {
            super.onPostExecute(slideModels);
            // Met à jour l'image slider avec les données obtenues
            imageSlider.setImageList(slideModels);
        }
    }
}
