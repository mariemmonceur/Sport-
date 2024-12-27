package com.example.projet_pfa1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projet_pfa1.Models.showAllModel;
import com.example.projet_pfa1.R;
import com.example.projet_pfa1.detailproduit;

import java.util.List;

public class showAllAdapter extends RecyclerView.Adapter<showAllAdapter.ViewHolder> {

    private Context context;
    private List<showAllModel> list;

    public showAllAdapter(Context context, List<showAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public showAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_produits,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull showAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg()).into(holder.img);
        holder.nom.setText(list.get(position).getName());
        holder.prix.setText(list.get(position).getPrix());
        holder.marque.setText(list.get(position).getMarque());
        holder.categorie.setText(list.get(position).getNomcategory());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailproduit.class);
                intent.putExtra("prix",list.get(position).getPrix());
                intent.putExtra("id",list.get(position).getId_prod());
                intent.putExtra("nom",list.get(position).getName());
                intent.putExtra("img",list.get(position).getImg());
                intent.putExtra("marque",list.get(position).getMarque());

                Toast.makeText(context, list.get(position).getId_prod(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView nom,prix,marque,categorie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.item_image);
            nom=itemView.findViewById(R.id.item_name);
            marque=itemView.findViewById(R.id.item_marque);
            categorie=itemView.findViewById(R.id.item_categorie);
            prix=itemView.findViewById(R.id.item_prix);
        }
    }
}
