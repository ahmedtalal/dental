package com.example.dentalcart.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.dentalcart.Repositories.GeneralOperations;
import com.example.dentalcart.UI.ShowreviewsActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private Context context ;
    public List<ItemModel> offersteList ;

    public OffersAdapter(Context context, List<ItemModel> favList) {
        this.context = context;
        this.offersteList = favList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favproduct_view, parent , false) ;
        MyViewHolder myViewHolder = new MyViewHolder(view) ;
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel itemModel = offersteList.get(position) ;
        holder.nameProductTv.setText(itemModel.getName());
        holder.priceProductTv.setText(itemModel.getPrice() + " EGP");
        holder.offerTV.setText(itemModel.getDiscount() + "%  off");
        Picasso.get()
                .load(itemModel.getPhoto())
                .placeholder(R.drawable.logo)
                .into(holder.productImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                        holder.productImage.setAlpha(Float.parseFloat("1"));
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                    }
                });

        // share product
        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneralOperations.shareData(itemModel.getName() , itemModel.getPrice() , context);
            }
        });

        //show reviews of product
        holder.reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ShowreviewsActivity.class) ;
                intent.putExtra("id" , itemModel.getId()) ;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return offersteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView offerTV , nameProductTv , priceProductTv ;
        private ImageView  reviews , productImage , shareImage ;
        private ProgressBar progressBar ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            offerTV = itemView.findViewById(R.id.favofferTV_id) ;
            nameProductTv = itemView.findViewById(R.id.namefavProductTV_id) ;
            priceProductTv = itemView.findViewById(R.id.pricefavPeoduct_id) ;
            reviews = itemView.findViewById(R.id.reviews_id) ;
            productImage = itemView.findViewById(R.id.favproductImage_id);
            shareImage = itemView.findViewById(R.id.favshare_id) ;
            progressBar = itemView.findViewById(R.id.favprogressbar) ;
        }
    }
}
