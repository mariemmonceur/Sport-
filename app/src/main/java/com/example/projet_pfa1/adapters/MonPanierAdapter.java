package com.example.projet_pfa1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.projet_pfa1.Models.MonPanierModel;
import com.example.projet_pfa1.R;
import com.example.projet_pfa1.detail_panier;

import java.util.List;

public class MonPanierAdapter extends RecyclerView.Adapter<MonPanierAdapter.ViewHolder> {

    Context context;
    List<MonPanierModel> list;
    double total=0.0;


    public MonPanierAdapter(Context context, List<MonPanierModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MonPanierAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mon_panier_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonPanierAdapter.ViewHolder holder, int position) {
        holder.date.setText(list.get(position).getDate());
        holder.temps.setText(list.get(position).getTemps());
        holder.prix.setText(String.valueOf(list.get(position).getPrix()));
        holder.nom_produit.setText(list.get(position).getNom_produit());
        holder.quantité.setText(String.valueOf(list.get(position).getQuantité()));
        Glide.with(context)
                .load(list.get(position).getImg()) // URL de l'image
                .transition(DrawableTransitionOptions.withCrossFade()) // optionnel
                .into(holder.image); // ImageView pour charger l'image


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detail_panier.class);
                context.startActivity(intent);
            }
        });


        double t = Double.parseDouble(list.get(position).getPrix());
        //total amount pass to mon panier
        total +=t;
        Intent intent= new Intent("mytotalamount");
        intent.putExtra("prixtotale",total);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView nom_produit,prix,date,temps,quantité;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nom_produit=itemView.findViewById(R.id.product_name);
            prix=itemView.findViewById(R.id.total_price);
            date=itemView.findViewById(R.id.current_date);
            temps=itemView.findViewById(R.id.current_time);
            quantité=itemView.findViewById(R.id.lokjh);
            image=itemView.findViewById(R.id.imageView3);


        }
    }
}
