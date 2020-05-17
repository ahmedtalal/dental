package com.example.dentalcart.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;
import com.example.dentalcart.Repositories.GeneralOperations;
import com.example.dentalcart.UI.MainActivity;
import com.example.dentalcart.UI.ShowproductActivity;
import com.example.dentalcart.ViewModels.CartItemsViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements  Filterable {
    private Context context ;
    private List<ItemModel> productList ;
    private List<ItemModel> productAfterFiltering ;
    private List<ItemModel> cartList ;
    public HomeAdapter(Context context, List<ItemModel> productList , List<ItemModel> cartList ) {
        this.context = context;
        this.productList = productList;
        this.cartList = cartList ;
        productAfterFiltering = new ArrayList<>(productList) ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_view, parent , false) ;
        MyViewHolder myViewHolder = new MyViewHolder(view) ;
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemModel itemModel = productList.get(position) ;
        if (position < cartList.size()){
            ItemModel itemModel1  = cartList.get(position) ;
            // check if item added to cart or not
            if (itemModel1.getId().equals(itemModel.getId())){
                holder.cart.setImageResource(R.drawable.ic_shopping_cart2);
                holder.cart.setTag("redCart");
            }else {
                holder.cart.setImageResource(R.drawable.ic_shopping_cart);
                holder.cart.setTag("whiteCart");
            }
            Log.i("seno" , position + "");
        }
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
        //check if dental item is favorite item or not
        if (itemModel.getFavorite().equals("true")){
            holder.favoriteImage.setImageResource(R.drawable.ic_favorito);
        }else {
            holder.favoriteImage.setImageResource(R.drawable.ic_unfavorite);
        }
        

        //go to display product data
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int img1 , img2 = 0;
                String name = itemModel.getName() ;
                String price = itemModel.getPrice();
                String rating = itemModel.getRating() ;
                String description = itemModel.getDescription() ;
                String favorite = itemModel.getFavorite() ;
                String descount = itemModel.getDiscount() ;
                String photo = itemModel.getPhoto() ;
                String id = itemModel.getId() ;
                Intent intent = new Intent(context , ShowproductActivity.class) ;
                intent.putExtra("name" , name) ;
                intent.putExtra("price" , price) ;
                float convertRating = Float.parseFloat(rating);
                intent.putExtra("rating" , convertRating) ;
                intent.putExtra("description" , description) ;
                intent.putExtra("favorite" , favorite) ;
                intent.putExtra("descount" , descount) ;
                intent.putExtra("photo" , photo) ;
                intent.putExtra("id" , id) ;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                context.startActivity(intent);
                ((Activity)context).finish();

            }
        });

        //share data
        holder.shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // your share Data here
                GeneralOperations.shareData(itemModel.getName() , itemModel.getPrice() , context);
            }
        });
        // add item in cart
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cart.getTag().equals("whiteCart")){
                    holder.cart.setImageResource(R.drawable.ic_shopping_cart2);
                    holder.cart.setTag("redCart");
                    GeneralOperations.addProductToCart(itemModel , context);
                    //((MainActivity)context).CalCartItems();
                }else {
                    holder.cart.setImageResource(R.drawable.ic_shopping_cart);
                    holder.cart.setTag("whiteCart");
                    GeneralOperations.deleteProductFromCart(itemModel.getId() , context);
                    //((MainActivity)context).CalCartItems();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void clearAdapter(){
        productList.clear();
    }
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        // run background thread to filter data
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemModel> filterList = new ArrayList<>() ;
            if (constraint.toString().isEmpty()){
                filterList.addAll(productAfterFiltering) ;
            }else {
                for (ItemModel itemModel : productAfterFiltering){
                    if (itemModel.getName().toLowerCase().contains(constraint.toString().toLowerCase().trim())){
                        filterList.add(itemModel) ;
                    }
                }
            }

            FilterResults results =  new FilterResults() ;
            results.values = filterList ;
            return results;
        }

        // After filter process, the result of this process is displayed in the UI
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView offerTV , nameProductTv , priceProductTv ;
        private ImageView  favoriteImage , productImage , shareImage , cart;
        private ProgressBar progressBar ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            offerTV = itemView.findViewById(R.id.offerTV_id) ;
            nameProductTv = itemView.findViewById(R.id.nameProductTV_id) ;
            priceProductTv = itemView.findViewById(R.id.pricePeoduct_id) ;
            cart = itemView.findViewById(R.id.addToCart_id) ;
            favoriteImage = itemView.findViewById(R.id.favorites_id) ;
            productImage = itemView.findViewById(R.id.productImage_id);
            shareImage = itemView.findViewById(R.id.share_id) ;
            progressBar = itemView.findViewById(R.id.progressbar) ;
        }
    }
}
