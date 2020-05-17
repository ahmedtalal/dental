package com.example.dentalcart.UI;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.Toolbar;

import com.example.dentalcart.Pojo.ItemModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.GeneralOperations;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddproductActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.addProductToolbar_id)
    Toolbar addProductToolbarId;
    @BindView(R.id.done_id)
    ImageView doneId;
    @BindView(R.id.productImgo_id)
    CircleImageView productImgoId;
    @BindView(R.id.camera_id)
    ImageView cameraId;
    @BindView(R.id.productName_id)
    EditText productNameId;
    @BindView(R.id.price_id)
    EditText priceId;
    @BindView(R.id.discount_id)
    EditText discountId;
    @BindView(R.id.description_id)
    EditText descriptionId;
    @BindView(R.id.favorite_id)
    RadioButton favoriteId;
    @BindView(R.id.unfavorite_id)
    RadioButton unfavoriteId;
    @BindView(R.id.rateo_id)
    AppCompatRatingBar rateoId;

    private static final int RN_PHOTO = 2;
    @BindView(R.id.Orthodontics_id)
    RadioButton OrthodonticsId;
    @BindView(R.id.Equipments_id)
    RadioButton EquipmentsId;
    @BindView(R.id.Periodontics_id)
    RadioButton PeriodonticsId;
    @BindView(R.id.Prosthodonics_id)
    RadioButton ProsthodonicsId;
    private Uri imageUri;
    private DatabaseReference reference;
    private String favoriteo = null;
    private ProgressDialog progressDialog;
    private String category = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        ButterKnife.bind(this);
        setSupportActionBar(addProductToolbarId);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addProductToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddproductActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference();
        cameraId.setOnClickListener(this::onClick);
        doneId.setOnClickListener(this::onClick);
        favoriteId.setOnClickListener(this::onClick);
        unfavoriteId.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_id:
                openGallary();
                break;
            case R.id.done_id:
                checkFieldsBeforeAdding();
                break;
            case R.id.favorite_id:
                favoriteo = "true";
                break;
            case R.id.unfavorite_id:
                favoriteo = "false";
                break;
            case R.id.Orthodontics_id:
                category = "orthodontics" ;
                break;
            case R.id.Equipments_id:
                category = "equipments" ;
                break;
            case R.id.Periodontics_id:
                category = "periodontics" ;
                break;
            case R.id.Prosthodonics_id:
                category = "prosthodonics" ;
                break;
        }
    }

    private void checkFieldsBeforeAdding() {
        String name = productNameId.getText().toString().trim();
        String price = priceId.getText().toString().trim();
        String discount = discountId.getText().toString().trim();
        String descrption = descriptionId.getText().toString().trim();
        String rating = String.valueOf(rateoId.getRating());
        String id = reference.push().getKey();
        if (TextUtils.isEmpty(name)) {
            productNameId.setError("this field is required");
            productNameId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(price)) {
            priceId.setError("this field is required");
            priceId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(discount)) {
            discountId.setError("this field is required");
            discountId.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(descrption)) {
            descriptionId.setError("this field is required");
            descriptionId.requestFocus();
            return;
        }
        if (imageUri.getPath().equals("null")) {
            Toast.makeText(AddproductActivity.this, "Please Enter the photo", Toast.LENGTH_SHORT).show();
            return;
        }
        ItemModel itemModel = new ItemModel
                (
                        descrption,
                        discount,
                        favoriteo,
                        id,
                        name,
                        rating,
                        price ,
                        category
                );
        progressDialog = new ProgressDialog(AddproductActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        GeneralOperations.AddProduct(itemModel, imageUri, progressDialog, AddproductActivity.this);

        // create notification
//        if (!discount.equals("0")){
//            Log.i("no", "sssd");
//            createNotification();
//        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void createNotification() {
//        String CHANNEL_ID = "my_channel_01";// The id of the channel.
//        CharSequence name = getString(R.string.channel_name);// The user-visible name of the channel.
//        int importance = NotificationManager.IMPORTANCE_HIGH;
//        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
//            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(AddproductActivity.this)
//                    .setSmallIcon(R.drawable.ic_notifications)
//                    .setContentTitle("Notification Alert ^__^ !")
//                    .setContentText("Click here to check more discount offers for dental cart app")
//                    .setChannelId(CHANNEL_ID)
//                    .setAutoCancel(true)
//                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//
//            Intent intent = new Intent(AddproductActivity.this , MainActivity.class) ;
//            PendingIntent pendingIntent = PendingIntent.getActivity(AddproductActivity.this,
//                    0 , intent , PendingIntent.FLAG_UPDATE_CURRENT);
//            nBuilder.setContentIntent(pendingIntent) ;
//            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            notificationManager.createNotificationChannel(mChannel);
//        }
//            notificationManager.notify(0,nBuilder.build());
//    }

    private void openGallary() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "completed action"), RN_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RN_PHOTO && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Picasso.get()
                    .load(imageUri)
                    .into(productImgoId);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddproductActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
