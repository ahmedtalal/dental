package com.example.dentalcart.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dentalcart.Pojo.UserModel;
import com.example.dentalcart.R;
import com.example.dentalcart.Repositories.FirebaseOperations;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_login_id)
    EditText emailLoginId;
    @BindView(R.id.password_login_id)
    EditText passwordLoginId;
    @BindView(R.id.forgorPasswordID)
    TextView forgorPasswordID;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.signUp_log_ID)
    TextView signUpLogID;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(this::onClick);
        signUpLogID.setOnClickListener(this::onClick);
        forgorPasswordID.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp_log_ID:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.forgorPasswordID:
                // Here set action
                Intent intent1 = new Intent(LoginActivity.this , ForgotpasswordActivity.class) ;
                startActivity(intent1);
                finish();
                break;
            case R.id.loginButton:
                UserModel userModel = new UserModel(emailLoginId.getText().toString(), passwordLoginId.getText().toString());
                checklogin(userModel);
        }
    }

    private void checklogin(UserModel userModel) {
        if (userModel.getEmail().equals("")) {
            emailLoginId.setError("email is required");
            emailLoginId.requestFocus();
            return;
        }
        if (userModel.getPassword().equals("")) {
            passwordLoginId.setError("password is required");
            passwordLoginId.requestFocus();
            return;
        }
        if (userModel.getPassword().length() < 8) {
            passwordLoginId.setError("the length must be greater than 8 letters");
            passwordLoginId.requestFocus();
            return;
        }
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        FirebaseOperations.logIn(userModel, progressDialog, LoginActivity.this);
    }
}
