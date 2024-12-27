package com.example.projet_pfa1.fragmentMenu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projet_pfa1.Config;
import com.example.projet_pfa1.JSONParser;
import com.example.projet_pfa1.LoginAcheteur;
import com.example.projet_pfa1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_Compte#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Compte extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_Compte() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Compte.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_Compte newInstance(String param1, String param2) {
        fragment_Compte fragment = new fragment_Compte();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button btn;
    SharedPreferences sp ;

    SharedPreferences.Editor edit;
    TextView t,nom,prenom,email,password,newpass;
    Button changeP,deconn,supp;
    String id;
    int oo,ooo;
    ProgressDialog dialog;
    JSONParser parser= new JSONParser();
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.fragment__compte, container, false);

       t=v.findViewById(R.id.firstNameEditText);
        nom=v.findViewById(R.id.nameTextView);
        prenom=v.findViewById(R.id.preno12);
        email=v.findViewById(R.id.emailTextView);
        password=v.findViewById(R.id.password12);
        changeP=v.findViewById(R.id.changePasswordButton);
        newpass=v.findViewById(R.id.newPasswordEditText);
        deconn=v.findViewById(R.id.logoutButton);
        supp=v.findViewById(R.id.deleteAccountButton);


       sp= getActivity().getSharedPreferences("acheteur", Context.MODE_PRIVATE);
      edit=sp.edit();
       String name =sp.getString("name",null);
        id =sp.getString("id",null);
        String surname =sp.getString("surname",null);
        String mail =sp.getString("email",null);
        String paswword =sp.getString("password",null);

      t.setText(name);
      prenom.setText(surname);
      email.setText(mail);
      password.setText(paswword);

      changeP.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              new changePass().execute();
              password.setText(paswword);

          }
      });

      deconn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              edit.clear();
              edit.commit();
              Intent i = new Intent(getActivity(), LoginAcheteur.class);
              startActivity(i);
          }
      });
      supp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              new supp().execute();

          }
      });


       return v;
    }


    class supp extends AsyncTask<String,String,String>
    {


        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String>map=new HashMap<String,String>();
            map.put("id",id);

            JSONObject o=parser.makeHttpRequest(Config.IP+"acheteurs/delete.php","GET",map);
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
            {   edit.clear();
                edit.commit();
                Toast.makeText(getActivity(), "compte supprimé", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), LoginAcheteur.class);
                startActivity(i);

            }
        }
    }

    class changePass extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog= new ProgressDialog(getActivity());
            dialog.setMessage("attendez sil vous plait");
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            HashMap<String,String>map=new HashMap<String,String>();
            map.put("id",id);
            map.put("password",newpass.getText().toString());
            JSONObject o=parser.makeHttpRequest(Config.IP+"acheteurs/changePassword.php","GET",map);
            try {
                oo= o.getInt("success");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            if (oo==1)
            {   edit.putString("password",newpass.getText().toString());
                edit.commit();
                Toast.makeText(getActivity(), "changement avec success", Toast.LENGTH_SHORT).show();


            }
        }
    }
}