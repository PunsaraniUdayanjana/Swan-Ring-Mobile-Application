package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.designproject.designproject.data.DatabaseHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.itextpdf.io.exceptions.IOException;
import com.squareup.picasso.Picasso;


public class ProfileActivity extends AppCompatActivity {

//  Variables
    private ImageView profileImage;
    private Button editBtn,saveBtn;
    private TextView profileName;
    private EditText edtProfileName, edtProfileAddress, edtContactNumber;
    TextView txtProfilename, txtProfileaddress, txtContactNumber;
    LinearLayout linearTextField, linearEditTextField;
    Uri imageUri;
    StorageReference storageReference;
    ImageView backIcon;
    ProgressDialog progressDialog;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    String accountUserName;
    String fullName, Address, contactNumber;
    int accountID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.getAllAccounts();
        while (cursor.moveToNext()) {
            if (cursor.getInt(4) == 1) {
                accountUserName = cursor.getString(1);
                accountID = cursor.getInt(0);
                Toast.makeText(this, accountUserName, Toast.LENGTH_SHORT).show();
            }
        }

//      assign variables
        edtProfileName = findViewById(R.id.edtProfilename);
        edtProfileAddress = findViewById(R.id.edtProfileaddress);
        edtContactNumber = findViewById(R.id.edtContactNumber);
        backIcon = findViewById(R.id.backIcon);

        txtProfilename = findViewById(R.id.txtProfilename);
        txtProfileaddress = findViewById(R.id.txtProfileaddress);
        txtContactNumber = findViewById(R.id.txtContactNumber);

        linearTextField = findViewById(R.id.linearTextField);
        linearEditTextField = findViewById(R.id.linearEditTextField);

        profileImage = findViewById(R.id.profileimage);
        profileName = findViewById(R.id.profilename);
        editBtn = findViewById(R.id.editbtn);
        saveBtn = findViewById(R.id.savebtn);

//      set image from firebase
        retrieveImage();
//      set user name
        profileName.setText(accountUserName);
        
        Cursor data = databaseHelper.getUserProfileById(accountID);
        if (data.moveToNext()) {
            fullName = data.getString(2);
            Address = data.getString(3);
            contactNumber = data.getString(4);

            setTextFields(fullName, Address, contactNumber);
        }
//      edit user details click this button
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtProfilename.getText().toString().equals("full name")) {
                    edtProfileName.setText("");
                } else {
                    edtProfileName.setText(txtProfilename.getText());
                }

                if (txtProfileaddress.getText().toString().equals("Address")) {
                    edtProfileAddress.setText("");
                } else {
                    edtProfileAddress.setText(txtProfileaddress.getText());
                }

                if (txtContactNumber.getText().toString().equals("Contact Number")) {
                    edtContactNumber.setText("");
                } else {
                    edtContactNumber.setText(txtContactNumber.getText());
                }

                linearTextField.setVisibility(View.GONE);
                linearEditTextField.setVisibility(View.VISIBLE);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              Check if the edit fields are currently visible
                if (linearEditTextField.getVisibility() == View.VISIBLE) {
//                  Retrieve the values from the edit fields
                    String saveProfileName = edtProfileName.getText().toString();
                    String saveProfileAddress = edtProfileAddress.getText().toString();
                    String saveProfileContactNumber = edtContactNumber.getText().toString();

//                  Update the text fields with the edited profile details
                    setTextFields(saveProfileName, saveProfileAddress, saveProfileContactNumber);

//                  Check if the user profile data already exists in the database
                    Cursor data = databaseHelper.getUserProfileById(accountID);
                    if (data.moveToNext()) {
//                      Update the profile details in the database
                        boolean isUpdate = databaseHelper.updateProfileDetails(accountID, accountUserName, saveProfileName, saveProfileAddress, saveProfileContactNumber);
                        if (isUpdate) {
//                          Display a success toast message if the update operation is successful
                            Toast.makeText(ProfileActivity.this, "Details updated !", Toast.LENGTH_SHORT).show();
                        } else {
//                          Display a failure toast message if the update operation fails
                            Toast.makeText(ProfileActivity.this, "Details not updated !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
//                      Insert the new profile details into the database
                        boolean isInsert = databaseHelper.insertProfileDetails(accountID, accountUserName, saveProfileName, saveProfileAddress, saveProfileContactNumber);
                        if (isInsert) {
//                          Display a success toast message if the insertion operation is successful
                            Toast.makeText(ProfileActivity.this, "Details saved !", Toast.LENGTH_SHORT).show();
                        } else {
//                          Display a failure toast message if the insertion operation fails
                            Toast.makeText(ProfileActivity.this, "Details not saved !", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("IntentReset")
            @Override
            public void onClick(View view) {
//              Inside your activity's click listener
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1111);
            }
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent now = new Intent(ProfileActivity.this, MenuActivity.class);
                startActivity(now);
            }
        });
    }

    private void setTextFields(String fullName, String address, String contactNumber) {
//      Set the values of the profile details in the TextViews
        txtProfilename.setText(fullName);
        txtProfileaddress.setText(address);
        txtContactNumber.setText(contactNumber);

//      Adjust the visibility of the layout containers
        linearTextField.setVisibility(View.VISIBLE);// Show the layout container for displaying text fields
        linearEditTextField.setVisibility(View.GONE);// Hide the layout container for editing text fields

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1111 && resultCode == RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);

                try {
                    uploadProfileImage(data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void uploadProfileImage(Uri imageUri) {

//      Create a ProgressDialog to show the uploading progress
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading file...");
        progressDialog.show();

//      Get the reference to the Firebase Storage location where the image will be uploaded
        storageReference = FirebaseStorage.getInstance().getReference("userImages/"+accountUserName);

//      Upload the image file to Firebase Storage
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                      Display a success toast message when the upload is successful
                        Toast.makeText(ProfileActivity.this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                      Display a failure toast message when the upload fails
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(ProfileActivity.this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void retrieveImage() {

//      Create a ProgressDialog to show the loading progress
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();

//      Get the reference to the Firebase Storage location of the user's profile image
        storageReference = FirebaseStorage.getInstance().getReference("userImages/"+accountUserName);

//      Retrieve the download URL of the image from Firebase Storage
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//              Once the download URL is obtained, load the image into the profileImage ImageView using Picasso library
                String image = uri.toString();
                Picasso.get().load(image).into(profileImage);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onFailure(@NonNull Exception e) {
//              In case of failure, dismiss the progressDialog
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        });

    }
}