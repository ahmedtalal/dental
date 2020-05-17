package com.example.dentalcart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dentalcart.Pojo.ReviewsModel;
import com.example.dentalcart.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllreviewsAdapter extends RecyclerView.Adapter<AllreviewsAdapter.MyViewHolder>{
    private List<ReviewsModel> list ;
    private Context context ;

    public AllreviewsAdapter(List<ReviewsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.allreviews_items , parent , false);
        MyViewHolder holder = new MyViewHolder(view) ;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReviewsModel reviewsModel = list.get(position) ;
        holder.nameTV.setText(reviewsModel.getName());
        holder.feedbackTV.setText(reviewsModel.getFeedBack());
        holder.ratingBar.setRating(reviewsModel.getRating());
        Picasso.get()
                .load(reviewsModel.getPhoto())
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void clearData(){
        list.clear();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private Toolbar toolbar ;
        private CircleImageView circleImageView ;
        private RatingBar ratingBar ;
        private TextView feedbackTV , nameTV ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            toolbar = itemView.findViewById(R.id.toolbarReviews_id) ;
            circleImageView = itemView.findViewById(R.id.profilePhoto_id) ;
            ratingBar = itemView.findViewById(R.id.ratingReviews_id) ;
            feedbackTV = itemView.findViewById(R.id.feebbackTV_id) ;
            nameTV = itemView.findViewById(R.id.personalNameTV_id) ;
        }
    }
}
