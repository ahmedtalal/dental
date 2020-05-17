package com.example.dentalcart.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.nameRegister_id)
    EditText nameRegisterId;
    @BindView(R.id.email_register_id)
    EditText emailRegisterId;
    @BindView(R.id.password_register_id)
    EditText passwordRegisterId;
    @BindView(R.id.repassword_register_id)
    EditText repasswordRegisterId;
    @BindView(R.id.registerButton)
    Button registerButton;
    @BindView(R.id.signInID)
    TextView signInID;

    private ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseOperations.getCurrentUser(RegisterActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerButton.setOnClickListener(this::onClick);
        signInID.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signInID :
                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class) ;
                startActivity(intent);
                finish();
                break;
            case R.id.registerButton :
                //Here register action
                UserModel user = new UserModel
                        (
                                nameRegisterId.getText().toString() ,
                                emailRegisterId.getText().toString() ,
                                passwordRegisterId.getText().toString() ,
                                repasswordRegisterId.getText().toString() ,
                                "null"
                        ) ;
                Log.i("Register" , nameRegisterId.getText().toString() + emailRegisterId.getText().length()) ;
                checkAccount(user) ;
                break;

        }
    }

    private void checkAccount(UserModel user) {
        int passLength = user.getPassword().length();
        if (user.getName().equals("")) {
            nameRegisterId.setError("username  is required");
            nameRegisterId.requestFocus();
            return;
        }
        if (user.getName().length() < 5) {
            nameRegisterId.setError("the length must be greater than 5 letters");
            nameRegisterId.requestFocus();
            return;
        }
        if (user.getEmail().equals("")) {
            emailRegisterId.setError("email is required");
            emailRegisterId.requestFocus();
            return;
        }
        if (user.getPassword().equals("")) {
            passwordRegisterId.setError("password is required");
            passwordRegisterId.requestFocus();
            return;
        }
        if (passLength < 8) {
            passwordRegisterId.setError("the length must be greater than 8 letters");
            passwordRegisterId.requestFocus();
            return;
        }
        if (user.getConfirmpassword().equals("")) {
            repasswordRegisterId.setError("confirm password is required");
            repasswordRegisterId.requestFocus();
            return;
        }
        if (!user.getConfirmpassword().equals(user.getPassword())) {
            Toast.makeText(getApplicationContext(), "Not matching between password and confirm password", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        FirebaseOperations.createAccount(user , progressDialog , RegisterActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
