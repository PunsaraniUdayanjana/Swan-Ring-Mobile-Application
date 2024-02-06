package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.designproject.designproject.data.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

public class ForgetPasswordActivity extends AppCompatActivity {

//  declare variables
    ImageView backIcon;
    Button resetBtn, cancelBtn;
    TextInputLayout username,password;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

//      Initialize variables
        backIcon = findViewById(R.id.backIcon);
        password = findViewById(R.id.password);
        username = findViewById(R.id.enterName);
        resetBtn = findViewById(R.id.resetBtn);
        cancelBtn = findViewById(R.id.btn1);
        updateTask();

//      Set click listener for back icon
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent now = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(now);

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent now = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(now);

            }
        });
    }

//  Using Forgotten Password for UpdateTask
    public void updateTask() {
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getEditText().getText().toString();
                String newPassword = password.getEditText().getText().toString();

//              Check if the username is empty
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }
//              Check if the new password is empty
                if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please enter a new password", Toast.LENGTH_SHORT).show();
                    return;
                }

//              Update the password in the database
                DatabaseHelper databaseHelper = new DatabaseHelper(ForgetPasswordActivity.this);
                boolean isUpdated = databaseHelper.updatePassword(userName, newPassword);
                if (isUpdated) {
                    Toast.makeText(ForgetPasswordActivity.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                    Intent now = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                    startActivity(now);
                } else {
                    Toast.makeText(ForgetPasswordActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}