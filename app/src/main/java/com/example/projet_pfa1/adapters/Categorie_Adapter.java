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
import com.example.projet_pfa1.Models.CategoryModel;
import com.example.projet_pfa1.R;
import com.example.projet_pfa1.detail_categorie;
import com.example.projet_pfa1.detailproduit;

import java.util.List;

public class Categorie_Adapter extends RecyclerView.Adapter<Categorie_Adapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> list;

    public Categorie_Adapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.catIm);
        holder.catName.setText(list.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, detail_categorie.class);
                intent.putExtra("image",list.get(position).getImg_url());
                intent.putExtra("id",list.get(position).getId_cat());
                intent.putExtra("nom",list.get(position).getName());

                Toast.makeText(context, list.get(position).getId_cat(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView catIm;
        TextView catName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catIm = itemView.findViewById(R.id.cat_img);
            catName = itemView.findViewById(R.id.cat_name);

        }
    }
}
