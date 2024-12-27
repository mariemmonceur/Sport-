package com.example.projet_pfa1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class AdminActivity extends AppCompatActivity {
    String nom,email;

    TextView t1,t2,t3;

    Button ach,vend,cat;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            nom=extras.getString("nom_admin");
            email=extras.getString("email_admin");
    }
        t1=findViewById(R.id.textViewAdminName);
        t2=findViewById(R.id.textViewCoordonnees);
        t3=findViewById(R.id.aaaa);
        ach=findViewById(R.id.buttonGestionAcheteurs);
        vend=findViewById(R.id.buttonGestionVendeurs);
        cat=findViewById(R.id.buttonGestionCategories);


        t1.setText(nom);
        t2.setText( email);
        t3.setText(nom);


        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AdminActivity.this,CatActivity.class);
                startActivity(i);
            }
        });

        ach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AdminActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        vend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(AdminActivity.this,gere_vendeur.class);
                startActivity(i);

            }
        });

}
}