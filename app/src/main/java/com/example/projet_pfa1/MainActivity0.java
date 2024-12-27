package com.example.projet_pfa1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity0 extends AppCompatActivity {

    Button acheter,vendre;

    int success; // Renommé "success" pour corriger la faute de frappe
    String password;
    String nom_admin, email_admin, pass; // Déclaration des variables
    ProgressDialog dialog;
    JSONParser parser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main0);
        acheter=findViewById(R.id.btnBuy);
        vendre= findViewById(R.id.btnSell);

        acheter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(MainActivity0.this,LoginAcheteur.class);
                startActivity(i);
            }
        });
        vendre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( MainActivity0.this,LoginVendeur.class);
                startActivity(i);

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Admin");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                showPasswordDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("veuillez entrer le code d'admin  ");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = input.getText().toString();

                validateAdminPassword(password);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void validateAdminPassword(String password) {
        new ConnectTask().execute();
    }

    class ConnectTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity0.this, "....."+password+".......", Toast.LENGTH_SHORT).show();
            dialog = new ProgressDialog(MainActivity0.this);
            dialog.setMessage("Vérification de l'admin");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String, String> map = new HashMap<>();
            map.put("password",password);
            JSONObject o = parser.makeHttpRequest(Config.IP+"admins/connect.php", "GET", map);

            try {
                success = o.getInt("success"); // Renommé "success" pour corriger la faute de frappe
                if (success == 1) {
                    JSONArray A = o.getJSONArray("admin");
                    JSONObject oo = A.getJSONObject(0);
                    nom_admin = oo.getString("nom_admin");
                    email_admin = oo.getString("email_admin"); // Correction de la récupération de l'e-mail de l'administrateur
                    pass = oo.getString("password"); // Correction de la récupération du mot de passe de l'administrateur
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss(); // Utilisation de dialog.dismiss() pour fermer le ProgressDialog

            if (success == 1) {
                Intent i = new Intent(MainActivity0.this, AdminActivity.class);
                i.putExtra("nom_admin", nom_admin);
                i.putExtra("email_admin", email_admin);
                startActivity(i);
            } else {
                Toast.makeText(MainActivity0.this, "Mot de passe incorrect ", Toast.LENGTH_SHORT).show(); // Correction de l'orthographe du message
            }
        }
    }
}
