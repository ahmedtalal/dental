package com.example.dentalcart.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.GeneralOperations;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    private List<ItemModel> list ;
    private Context context ;
    private int totalPrice;
    public CartAdapter(List<ItemModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items , parent , false) ;
        MyViewHolder myViewHolder = new MyViewHolder(view) ;
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel itemModel = list.get(position) ;
        holder.cartNameTv.setText(itemModel.getName());
        holder.cartPriceTv.setText(itemModel.getPrice());
        Picasso.get()
                .load(itemModel.getPhoto())
                .error(R.drawable.logo)
                .placeholder(R.drawable.logo)
                .into(holder.cartImage);
        // this method is used to increase price
        holder.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(holder.cartCounterTV.getText().toString());
                int price = Integer.parseInt(itemModel.getPrice()) ;
                int newResult = result+1;
                int calcResult = newResult * price ;
                holder.cartPriceTv.setText(String.valueOf(calcResult));
                holder.cartCounterTV.setText(String.valueOf(newResult));
                Log.i("prcio" , holder.cartPriceTv.getText().toString());
                totalPrice = totalPrice + Integer.parseInt(holder.cartPriceTv.getText().toString());
                Log.i("preno" , totalPrice+"");
                GeneralOperations.addTotalPrice(totalPrice);

            }
        });

        // this method is used to decrease
        holder.minusIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Integer.parseInt(holder.cartCounterTV.getText().toString());
                int price = Integer.parseInt(itemModel.getPrice()) ;
                if (result != 1){
                    int covResult = result - 1;
                    int calcResult = covResult * price ;
                    holder.cartCounterTV.setText(String.valueOf(covResult));
                    holder.cartPriceTv.setText(String.valueOf(calcResult));
                    Log.i("prcion" , totalPrice+"");
                    totalPrice = totalPrice - Integer.parseInt(holder.cartPriceTv.getText().toString());
                    Log.i("preno0" , totalPrice+"");
                    GeneralOperations.addTotalPrice(totalPrice);
                }
            }
        });
        totalPrice = totalPrice + Integer.parseInt(holder.cartPriceTv.getText().toString());
        Log.i("preno" , totalPrice+"");
        GeneralOperations.addTotalPrice(totalPrice);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView cartImage , addImage , minusIamge ;
        private TextView cartNameTv , cartPriceTv , cartCounterTV ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartImeo_id);
            addImage = itemView.findViewById(R.id.addNumber_id);
            minusIamge = itemView.findViewById(R.id.minusNumber_id);
            cartNameTv = itemView.findViewById(R.id.cartName_id);
            cartPriceTv = itemView.findViewById(R.id.cartprice_id);
            cartCounterTV = itemView.findViewById(R.id.counter_id) ;
        }
    }
}
