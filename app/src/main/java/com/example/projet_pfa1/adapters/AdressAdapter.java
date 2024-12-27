package com.example.projet_pfa1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_pfa1.Models.AdressModel;
import com.example.projet_pfa1.R;

import java.util.List;

public class AdressAdapter extends RecyclerView.Adapter<AdressAdapter.ViewHolder> {

    List<AdressModel> addressModelList;
    Context context;
    SelectedAdress selectedadress;

    private RadioButton selectedRadioBtn = null; // Track selected radio button

    public AdressAdapter(List<AdressModel> addressModelList, Context context, SelectedAdress selectedadress) {
        this.addressModelList = addressModelList;
        this.context = context;
        this.selectedadress = selectedadress;
    }

    @NonNull
    @Override
    public AdressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdressAdapter.ViewHolder holder, int position) {

        AdressModel addressModel = addressModelList.get(position);
        holder.address.setText(addressModel.getUserAdress());
        holder.radiobutton.setChecked(addressModel.isSelected());

        holder.radiobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               for(AdressModel addess:addressModelList) {
                   addess.setSelected(false);
                }
               addressModelList.get(position).setSelected(true);
               if(selectedRadioBtn!=null){
                   selectedRadioBtn.setChecked(false);
               }
               selectedRadioBtn=(RadioButton) view;
               selectedRadioBtn.setChecked(true);
               selectedadress.setAdress(addressModelList.get(position).getUserAdress());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        RadioButton radiobutton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radiobutton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAdress {
        void setAdress(String adress);
    }
}


