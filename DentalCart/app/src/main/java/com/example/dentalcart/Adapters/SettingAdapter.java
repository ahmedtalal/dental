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

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.myViewHolder> {
    private List<SettingModel> list ;
    private Context context ;

    public SettingAdapter(List<SettingModel> list, Context context) {
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
                if (holder.text.getText().equals("Logout")){
                    FirebaseOperations.singOut(context);
                }else if (holder.text.getText().equals("Profile")){
                    Intent intent =  new Intent(context , ProfileActivity.class) ;
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }else if (holder.text.getText().equals("Tell a friend")){
                    Intent intent =  new Intent(context , ShareappActivity.class) ;
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
