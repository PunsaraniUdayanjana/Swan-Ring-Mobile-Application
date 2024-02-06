package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.designproject.designproject.data.DatabaseHelper;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Executor;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

//    Password pattern
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
    Button registerBtn,loginBtn ;
    TextView forgotPassword;
    private TextInputLayout username,password,email;
    DatabaseHelper databaseHelper;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();

//      Create a new instance of DatabaseHelper to manage database operations
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.updateAllLoginStatus(0);

//      assign variables
        username = findViewById(R.id.username);
        password =  findViewById(R.id.password);
        email =  findViewById(R.id.email);

//     Call fingerprint() method to check if the device has a fingerprint scanner and to prompt user for authentication
        fingerprint();

//     Initialize buttons and text view
        registerBtn = (Button) findViewById(R.id.registerBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        forgotPassword = (TextView) findViewById(R.id.forgetPasswordBtn);

        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

//      login button onclickListener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = username.getEditText().getText().toString();
                String pass = password.getEditText().getText().toString();
                String mail = email.getEditText().getText().toString();
                confirmInput();

                if (name.equals("") || pass.equals("") || mail.equals(""))
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    boolean checknamepass = databaseHelper.checkusernamepassword(name, pass, mail);
                    if (checknamepass == true) {
                        Intent now = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(now);
                        databaseHelper.updateLoginStatus(name, 1);
                        Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

//  email validation
    @SuppressLint("SuspiciousIndentation")
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

//    ConfirmInput
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


//  Method to check if the device has a fingerprint scanner and to prompt user for authentication
    public void fingerprint() {
        // Switch is in the on state
        // FINGERPRINT CODES

//      Check the biometric authentication capability of the device
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
//          Device doesn't have a fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Device Doesn't have Fingerprint", Toast.LENGTH_SHORT).show();
                break;
//          Fingerprint sensor is not available or not functioning properly
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this, "Not Working !", Toast.LENGTH_SHORT).show();
//          No fingerprints are enrolled on the device
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this, "No Fingerprint assigned !", Toast.LENGTH_SHORT).show();
//          Security update is required to use biometric authentication
            case BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED:
                break;
//          Biometric authentication is not supported on the device
            case BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED:
                break;
//         The biometric status is unknown
            case BiometricManager.BIOMETRIC_STATUS_UNKNOWN:
                break;
//         Biometric authentication is available and functioning
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
        }
        Executor executors = ContextCompat.getMainExecutor(this);


        biometricPrompt = new BiometricPrompt(LoginActivity.this, executors, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//              Handle authentication errors
                super.onAuthenticationError(errorCode, errString);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
//              Handle authentication success
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LoginActivity.this, "Account Successfully Deleted !", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAuthenticationFailed() {
//          Handle authentication failure
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("SWAN RING")// Set the title of the biometric prompt
                .setDescription("Use Fingerprint") // Set the description of the biometric prompt
                .setDeviceCredentialAllowed(true).build();// Allow using device credentials (e.g., PIN, pattern) as an alternative
        biometricPrompt.authenticate(promptInfo);// Initiate the biometric authentication process with the prompt info
     }

//  Set onClickListeners for buttons to navigate to different screens
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerBtn:
                Intent register = new Intent(this, RegisterActivity.class);
                startActivity(register);
                break;

            case R.id.forgetPasswordBtn:
                Intent forgetPassword = new Intent(this, ForgetPasswordActivity.class);
                startActivity(forgetPassword);
                break;

        }
    }

    //When try to back from Login page this method will execute
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit Swan Ring ?");

        //When click "Yes" it will execute this
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                onStop();
                onDestroy();
            }
        });

        //When click "No" it will execute this
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}












