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

public class MyPlanes2Activity extends AppCompatActivity implements View.OnClickListener {

    TextView name,name1,name2,name3,name4,invitation,invitation1,invitation2,invitation3,invitation4;
    ImageView backIcon;
    Button download;
    private static final int STORAGE_CODE = 1000;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_planes2);

        // Initialize the TextViews, ImageView, and Button
        name = findViewById(R.id.textName);
        name1 = findViewById(R.id.textName3);
        name2 = findViewById(R.id.textName5);
        name3 = findViewById(R.id.textName7);
        name4 = findViewById(R.id.textName9);

        invitation = findViewById(R.id.invite);
        invitation1 = findViewById(R.id.invite1);
        invitation2 = findViewById(R.id.invite2);
        invitation3 = findViewById(R.id.invite3);
        invitation4 = findViewById(R.id.invite4);

        download = findViewById(R.id.dowloadbtn);
        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(this);

 //     Retrieve the data from the previous activity
        String Name = getIntent().getStringExtra("keyname");
        String text = getIntent().getStringExtra("keytextname");
        String Name1 = getIntent().getStringExtra("keyname1");
        String text1 = getIntent().getStringExtra("keytextname1");
        String Name2 = getIntent().getStringExtra("keyname2");
        String text2 = getIntent().getStringExtra("keytextname2");
        String Name3 = getIntent().getStringExtra("keyname3");
        String text3 = getIntent().getStringExtra("keytextname3");
        String Name4 = getIntent().getStringExtra("keyname4");
        String text4 = getIntent().getStringExtra("keytextname4");


//      Set the retrieved data to the TextViews
        name.setText(Name);
        invitation.setText(text);
        name1.setText(Name1);
        invitation1.setText(text1);
        name2.setText(Name2);
        invitation2.setText(text2);
        name3.setText(Name3);
        invitation3.setText(text3);
        name4.setText(Name4);
        invitation4.setText(text4);

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
            String mInit1 = invitation1.getText().toString();
            String mText1 = name1.getText().toString();
            String mInit2 = invitation2.getText().toString();
            String mText2 = name2.getText().toString();
            String mInit3 = invitation3.getText().toString();
            String mText3 = name3.getText().toString();
            String mInit4 = invitation4.getText().toString();
            String mText4 = name4.getText().toString();


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

//          Adjust the width and height as desired

            mDoc.add(image);
            mDoc.add(image2);

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLDITALIC);
            Font boldFont2 = new Font(Font.FontFamily.HELVETICA,18);

            Paragraph paragraph1 = new Paragraph(mInit,boldFont);
            paragraph1.setAlignment(Element.ALIGN_CENTER); // Set the alignment to center
            mDoc.add(paragraph1);

            Paragraph paragraph2 = new Paragraph(mText,boldFont2);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph2);

            Paragraph paragraph3 = new Paragraph(mInit1,boldFont);
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph3);

            Paragraph paragraph4 = new Paragraph(mText1,boldFont2);
            paragraph4.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph4);

            Paragraph paragraph5 = new Paragraph(mInit2,boldFont);
            paragraph5.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph5);

            Paragraph paragraph6 = new Paragraph(mText2,boldFont2);
            paragraph6.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph6);

            Paragraph paragraph7 = new Paragraph(mInit3,boldFont);
            paragraph7.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph7);

            Paragraph paragraph8 = new Paragraph(mText3,boldFont2);
            paragraph8.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph8);

            Paragraph paragraph9 = new Paragraph(mInit4,boldFont);
            paragraph9.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph9);

            Paragraph paragraph10 = new Paragraph(mText4,boldFont2);
            paragraph10.setAlignment(Element.ALIGN_CENTER);
            mDoc.add(paragraph10);

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
                Intent home = new Intent(this, InformationActivity.class);
                startActivity(home);
                break;
        }
    }
}