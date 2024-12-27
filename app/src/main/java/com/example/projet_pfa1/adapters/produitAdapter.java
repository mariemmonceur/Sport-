package com.example.projet_pfa1.adapters;

import android.annotation.SuppressLint;
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
import com.example.projet_pfa1.R;
import com.example.projet_pfa1.Models.produitModel;
import com.example.projet_pfa1.detailproduit;

import java.util.List;

public class produitAdapter extends RecyclerView.Adapter<produitAdapter.ViewHolder> {


    private Context context;
    private List<produitModel>list;

    public produitAdapter(Context context, List<produitModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(list.get(position).getImg()).into(holder.img);
        holder.name.setText(list.get(position).getName());
        holder.prix.setText(list.get(position).getPrix());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detailproduit.class);
                intent.putExtra("prix",list.get(position).getPrix());
                intent.putExtra("id",list.get(position).getId_prod());
                intent.putExtra("nom",list.get(position).getName());
                intent.putExtra("img",list.get(position).getImg());
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
        TextView name,prix;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.prod_img);
            name=itemView.findViewById(R.id.prod_name);
            prix=itemView.findViewById(R.id.prod_price);
        }
    }
}
