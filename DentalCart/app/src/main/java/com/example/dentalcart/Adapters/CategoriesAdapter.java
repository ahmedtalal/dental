package com.example.dentalcart.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.dentalcart.Pojo.SettingModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;
import com.example.dentalcart.UI.ProfileActivity;
import com.example.dentalcart.UI.ShareappActivity;
import com.example.dentalcart.UI.ShowcategoriesActivity;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.myViewHolder> {
    private List<SettingModel> list ;
    private Context context ;

    public CategoriesAdapter(List<SettingModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settingpage_items , parent , false) ;
        myViewHolder myViewHolder = new myViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        SettingModel model = list.get(position) ;
        holder.image.setImageResource(model.getImg());
        holder.text.setText(model.getName());
        holder.ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , ShowcategoriesActivity.class) ;
                if (holder.text.getText().equals("Orthodontics")){
                    intent.putExtra("type" , "Orthodontics") ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if (holder.text.getText().equals("Equipments")){
                    intent.putExtra("type" , "Equipments") ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if (holder.text.getText().equals("Periodontics")){
                    intent.putExtra("type" , "Periodontics") ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if (holder.text.getText().equals("Prosthodonics")){
                    intent.putExtra("type" , "Prosthodonics") ;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        public ImageView image ;
        public TextView text ;
        public MaterialRippleLayout ripple ;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.settingImg_id) ;
            text = itemView.findViewById(R.id.settingText_id) ;
            ripple =  itemView.findViewById(R.id.ripple_settings_id) ;
        }
    }
}
