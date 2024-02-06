package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.designproject.designproject.data.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

//password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

//  Variables
    private TextInputLayout username, email, password, confirmPassword;
    DatabaseHelper databaseHelper;
    Button registerBtn;
    ImageView backIcon;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//      getSupportActionBar().hide();

//      Create a new instance of DatabaseHelper to manage database operations
        databaseHelper = new DatabaseHelper(this);

//      assign variables
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        backIcon = findViewById(R.id.backIcon);


//      register button onclickListener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = username.getEditText().getText().toString();
                String mail = email.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                String confirmPass = confirmPassword.getEditText().getText().toString();
                confirmInput();

                if (name.equals("") || pass.equals("") || confirmPass.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields  ", Toast.LENGTH_SHORT).show();
                else {
                    if (validatePassword()) {
                        password.setError(null);
                        if (pass.equals(confirmPass)) {
                            confirmPassword.setError(null);
                            boolean checkuser = databaseHelper.checkusername(name);
                            if (checkuser == false) {
                                boolean insert = databaseHelper.insertData(name, pass, mail);
                                if (insert == true) {
                                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    Intent now = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(now);
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "user already exists! please login", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            confirmPassword.setError("Enter same password");
                            Toast.makeText(RegisterActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

//      Find the backIcon ImageView in the layout and set an OnClickListener for it

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to navigate to the Login Activity and start it
                Intent now = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(now);
            }
        });

    }

//  email validation
    private boolean validateEmail(){
        String emailInput = email.getEditText().getText().toString().trim();

        if(emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter valid email address");
        }else
            email.setError(null);
        email.setErrorEnabled(false);
        return true;
    }

//  username validation
    private boolean validateUsername(){
        String usernameInput = username.getEditText().getText().toString().trim();

        if(usernameInput.isEmpty()) {
            username.setError("Field can't be empty");
            return false;
        } else if (username.getCounterMaxLength() > 15) {
            username.setError("user name too log ");
            return false;
        }else{
            username.setError(null);
            return true;
        }
    }

//  password validation
    private boolean validatePassword(){
        String passwordInput = password.getEditText().getText().toString().trim();

        if(passwordInput.isEmpty()){
            password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak");
            return false;
        }else
            password.setError(null);
        return true;
    }

//  ConfirmInput
    public void confirmInput(){
        if(!validateEmail() || !validateUsername() || !validatePassword()){
            return;
        }
        String input = "Email:" + email.getEditText().getText().toString();
        input += "\n";
        input += "Username:" + username.getEditText().getText().toString();
        input += "\n";
        input += "Password:" + password.getEditText().getText().toString();

        Toast.makeText(this, input , Toast.LENGTH_SHORT).show();
    }
}