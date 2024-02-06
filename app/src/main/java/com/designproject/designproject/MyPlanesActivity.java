package com.designproject.designproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyPlanesActivity extends AppCompatActivity implements View.OnClickListener {

//  declare variables
    TextView name,username,invitation;
    Button download;
    ImageView backIcon;
    private static final int STORAGE_CODE = 1000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_planes);

//      assign variables
        name = findViewById(R.id.textName);
        download = findViewById(R.id.dowloadbtn);
        invitation = findViewById(R.id.invite);
        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(this);

//      Retrieve the data from the previous activity
        String Name = getIntent().getStringExtra("keyname");
        String text = getIntent().getStringExtra("keytextname");

//      Set the retrieved data to the TextViews
        name.setText(Name);
        invitation.setText(text);

//      Click the download button create pdf file
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, STORAGE_CODE);
                    }else{
                     savePdf();
                    }
                }else{
                    savePdf();
                }
            }
        });
    }

//  save pdf file in mobile phone this format
    private void savePdf() {

        Document mDoc = new Document();

        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + mFileName + ".pdf";

        try{
            PdfWriter.getInstance(mDoc, new FileOutputStream(mFilePath));
            mDoc.open();

            String mInit = invitation.getText().toString();
            String mText = name.getText().toString();

            mDoc.addAuthor("Swan Ring");

            Context context = getApplicationContext();
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.curtain2); // Replace with the actual drawable resource name
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleToFit(500, 200);

            Context context2 = getApplicationContext();
            Drawable drawable2 = ContextCompat.getDrawable(context2, R.drawable.logo); // Replace with the actual drawable resource name
            Bitmap bitmap2 = ((BitmapDrawable) drawable2).getBitmap();
            ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
            bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
            Image image2 = Image.getInstance(stream2.toByteArray());
            image2.setAlignment(Element.ALIGN_CENTER);
            image2.scaleToFit(160, 160);

            // Adjust the width and height as desired

            mDoc.add(image);
            mDoc.add(image2);

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC);
            Font boldFont2 = new Font(Font.FontFamily.HELVETICA,18);

            Paragraph paragraph1 = new Paragraph(mInit,boldFont);
            paragraph1.setAlignment(Element.ALIGN_CENTER); // Set the alignment to center
            mDoc.add(paragraph1);

            Paragraph paragraph2 = new Paragraph(mText,boldFont2);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph2);

            Context context1 = getApplicationContext();
            Drawable drawable1 = ContextCompat.getDrawable(context1, R.drawable.curtain3); // Replace with the actual drawable resource name
            Bitmap bitmap1 = ((BitmapDrawable) drawable1).getBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
            Image image1 = Image.getInstance(stream1.toByteArray());
            image1.setAlignment(Element.ALIGN_CENTER);
            image1.scaleToFit(500, 200);
            mDoc.add(image1);

            mDoc.close();
            Toast.makeText(this, mFileName +".pdf\nis saved to \n"+ mFilePath, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//  No save pdf in mobile phone
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePdf();
                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backIcon:
                Intent home = new Intent(this, InvitationActivity.class);
                startActivity(home);
                break;
        }
    }
}