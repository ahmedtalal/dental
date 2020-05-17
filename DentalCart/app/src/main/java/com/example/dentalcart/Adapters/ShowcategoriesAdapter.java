package com.example.dentalcart.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowcategoriesAdapter extends RecyclerView.Adapter<ShowcategoriesAdapter.myViewHolder> {
    private List<ItemModel> list ;
    private Context context ;

    public ShowcategoriesAdapter(List<ItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_items , parent , false) ;
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        ItemModel model = list.get(position) ;
        Picasso.get()
                .load(model.getPhoto())
                .error(R.drawable.logo)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.pr.setVisibility(View.GONE);
                        holder.imageView.setAlpha(Float.parseFloat("1"));
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.pr.setVisibility(View.VISIBLE);
                    }
                });

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice() + "EGP");
        holder.offer.setText(model.getDiscount() + "%  off");

        if (model.getCategory().equals("orthodontics")){
            holder.type.setText("Type : " + "Orthodontics");
        }else if (model.getCategory().equals("equipments")){
            holder.type.setText("Type : " + "Equipments");
        }else if (model.getCategory().equals("periodontics")){
            holder.type.setText("Type : " + "Periodontics");
        }else if (model.getCategory().equals("prosthodonics")){
            holder.type.setText("Type : " + "Prosthodonics");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView name , price , offer , type;
        ProgressBar pr ;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.catproductImage_id) ;
            name = itemView.findViewById(R.id.catnameProductTV_id) ;
            price = itemView.findViewById(R.id.catpricePeoduct_id) ;
            offer = itemView.findViewById(R.id.catofferTV_id) ;
            pr = itemView.findViewById(R.id.catprogressbar) ;
            type = itemView.findViewById(R.id.categoryType_id) ;
        }
    }
}
