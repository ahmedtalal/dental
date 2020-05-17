package com.example.dentalcart.Repositories;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.Pojo.ReviewsModel;
import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.UI.AddproductActivity;
import com.example.dentalcart.UI.MainActivity;
import com.example.dentalcart.UI.ShowproductActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GeneralOperations {

    public static void shareData(String text , String price , Context context){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT , price) ;
        Intent shareIntent = Intent.createChooser(sendIntent, "Share your notes with your friends");
        context.startActivity(shareIntent) ;
    }

    public static void addFeedBack(ReviewsModel reviewsModel , String productId , Context context){
        FirebaseDatabase firebaseDatabase  = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference() ;
        String reviewID = reference.push().getKey();
        reference.child("Reviews").child(productId).child(reviewID).setValue(reviewsModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(context , "Successfully added" , Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context , MainActivity.class) ;
                            context.startActivity(intent);
                            ((Activity)context).finish();
                        }
                    }
                });
    }

    public static void AddProduct(ItemModel itemModel , Uri imageUri , ProgressDialog progressDialog, Context context){
        UploadTask uploadTask;
        FirebaseStorage fbs = FirebaseStorage.getInstance();
        StorageReference storageReference = fbs.getReference().child("ProductimageUrl");
        final StorageReference rfs = storageReference.child(imageUri.getPath());
        uploadTask = rfs.putFile(imageUri);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Toast.makeText(context , task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
                return rfs.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri retrieveUriImage = task.getResult();
                String converImage = retrieveUriImage.toString();
                saveInfo(itemModel , converImage , progressDialog ,context);
            }
        });



    }

    private static void saveInfo(ItemModel itemModel, String convertImage ,ProgressDialog progressDialog ,Context context) {
        ItemModel model = new ItemModel
                (
                        itemModel.getDescription() ,
                        itemModel.getDiscount() ,
                        itemModel.getFavorite() ,
                        itemModel.getId() ,
                        itemModel.getName() ,
                        convertImage ,
                        itemModel.getRating() ,
                        itemModel.getPrice() ,
                        itemModel.getCategory()
                ) ;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products").child(itemModel.getId()) ;
        reference.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context , "succesfully added" , Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                createNotification(context , itemModel.getDiscount());
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createNotification(Context context , String discount) {
        if (!discount.equals("0")){
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = context.getString(R.string.channel_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle("Notification Alert ^__^ !")
                    .setContentText("Click here to check more discount offers for dental cart app")
                    .setChannelId(CHANNEL_ID)
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

            Intent intent = new Intent(context, MainActivity.class) ;
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);
            nBuilder.setContentIntent(pendingIntent) ;
            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(mChannel);
            }
            notificationManager.notify(0,nBuilder.build());
        }

    }

    // this method is used to add item in cart
    public static void addProductToCart(ItemModel itemModel , Context context){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = firebaseDatabase.getReference().child("Cart").child(user.getUid()) ;
        reference.child(itemModel.getId()).setValue(itemModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(context , "Item added to cart" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(context , MainActivity.class) ;
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        });
    }

    public static void deleteProductFromCart(String id , Context context){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = firebaseDatabase.getReference().child("Cart").child(user.getUid()) ;
        reference.child(id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context , "Item removed from cart" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context , MainActivity.class) ;
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    public static void addTotalPrice(int totalPrice ){
        ItemModel itemModel = new ItemModel(String.valueOf(totalPrice)) ;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = firebaseDatabase.getReference().child("CartTotalPrice").child(user.getUid()) ;
        reference.setValue(itemModel);
    }

    public static void deleteAllProductFromCart(Context context){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = firebaseDatabase.getReference().child("Cart").child(user.getUid()) ;
        reference.setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context , " All Items removed from cart" , Toast.LENGTH_LONG).show();
            }
        });
        deletePrice(context);
    }

    private static void deletePrice(Context context) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser() ;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance() ;
        DatabaseReference reference = firebaseDatabase.getReference().child("CartTotalPrice").child(user.getUid()) ;
        reference.setValue(null);
        Intent intent = new Intent(context , MainActivity.class) ;
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
