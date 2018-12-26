package com.example.dcl.onlineshopapp.Adepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.dcl.onlineshopapp.Helper.ItemProduct;
import com.example.dcl.onlineshopapp.R;
import com.example.dcl.onlineshopapp.Utils.Common;

import java.util.List;

public class MultiiChoiceAdepter extends RecyclerView.Adapter<MultiiChoiceAdepter.MultiChoiceViewHolder> {

    Context context;
    List<ItemProduct> option_llist;

    public MultiiChoiceAdepter(Context context, List<ItemProduct> option_llist) {
        this.context = context;
        this.option_llist = option_llist;
    }


    @NonNull
    @Override
    public MultiChoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.multi_check,null);
        return new MultiChoiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiChoiceViewHolder holder, final int position) {

        holder.muti_choice.setText(option_llist.get(position).getName());

        holder.muti_choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Common.toppingAdded.add(buttonView.getText().toString());
                    Common.toppingPrice+=Double.parseDouble(option_llist.get(position).getPrice());
                }else {
                    Common.toppingAdded.remove(buttonView.getText().toString());
                    Common.toppingPrice-=Double.parseDouble(option_llist.get(position).getPrice());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return option_llist.size();
    }

    class MultiChoiceViewHolder extends RecyclerView.ViewHolder{

        CheckBox muti_choice;

        public MultiChoiceViewHolder(View itemView) {
            super(itemView);

            muti_choice=itemView.findViewById(R.id.multi_choicee);

        }
    }
}
