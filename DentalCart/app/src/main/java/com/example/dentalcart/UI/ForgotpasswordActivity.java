package com.example.dentalcart.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotpasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.passwordToolbar)
    Toolbar passwordToolbar;
    @BindView(R.id.resetButton)
    Button resetButton;
    @BindView(R.id.email_forgot_id)
    EditText emailForgotId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        ButterKnife.bind(this);

        setSupportActionBar(passwordToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        passwordToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotpasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        resetButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetButton) {
            String emailAddress = emailForgotId.getText().toString();
            if (TextUtils.isEmpty(emailAddress)) {
                emailForgotId.setError("Email field is required");
                emailForgotId.requestFocus();
            } else {
                progressDialog = new ProgressDialog(ForgotpasswordActivity.this);
                progressDialog.setMessage("Please wait....");
                progressDialog.show();
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                FirebaseOperations.forgotPassword(ForgotpasswordActivity.this, progressDialog, emailAddress);
            }
        }
    }
}
