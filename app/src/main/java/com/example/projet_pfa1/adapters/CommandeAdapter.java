package com.example.projet_pfa1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.projet_pfa1.Models.CommandeModel;
import com.example.projet_pfa1.R;

import java.util.List;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.ViewHolder> {


    Context context;
    List<CommandeModel> list;

    public CommandeAdapter(Context context, List<CommandeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CommandeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commande,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeAdapter.ViewHolder holder, int position) {
        holder.id_commande.setText(list.get(position).getId_commande());
        holder.nom_produit.setText(list.get(position).getNom_produit());
        holder.prix_produit.setText(list.get(position).getPrix());
        holder.Quantité_produit.setText(list.get(position).getQuantité());
        holder.date_commande.setText(list.get(position).getDate_commande());
        holder.adresse_commande.setText(list.get(position).getAdresse());
        Glide.with(context)
                .load(list.get(position).getImage()) // URL de l'image
                .transition(DrawableTransitionOptions.withCrossFade()) // optionnel
                .into(holder.image); // ImageView pour charger l'image

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_commande,nom_produit,prix_produit,Quantité_produit,date_commande,adresse_commande,id_categorie;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_commande=itemView.findViewById(R.id.id_commande);
            nom_produit=itemView.findViewById(R.id.nom_produit);
            prix_produit=itemView.findViewById(R.id.prix_produit);
            Quantité_produit=itemView.findViewById(R.id.Quantité_produit);
            date_commande=itemView.findViewById(R.id.date_commande);
            adresse_commande=itemView.findViewById(R.id.adresse_commande);
            image=itemView.findViewById(R.id.imageView9);





        }
    }
}
