package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {

//  declare variables
    Button btn2, btn3, btn1,confirmed,logout;

//    DatabaseHelper databaseHelper;
    EditText dateformat,dateEdit,enterName,enterPhotographer;
    TextView autoText,textDistric,textView4,textView6,textName,textGrapher;
    int year,month,day;
    AutoCompleteTextView autoComplete;
    ArrayAdapter<String> adapterItem;

//  Array List
    String[] distric ={"Ampara","Anuradhapura","Badulla","Batticaloa","Colombo","Galle","Gampaha","Hambantota", "Jaffna","Kalutara","Kandy", "Kegalle",
                        "Kilinochchi","Kurunegala","Mannar","Matale","Matara","Monaragala","Mullaitivu","Nuwara Eliya","Polonnaruwa","Puttalam","Ratnapura","Trincomalee","Vavuniya"};

    DatePickerDialog.OnDateSetListener listener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
//      getSupportActionBar().hide();

//      assign variables
        confirmed = (Button) findViewById(R.id.confirmedButton);
        dateformat= findViewById(R.id.dateEdit);
        Calendar calendar = Calendar.getInstance();

        btn3 = findViewById(R.id.button3);
        btn2 = findViewById(R.id.button2);
        btn1 = findViewById(R.id.button1);
        autoText = findViewById(R.id.autoText);
        autoComplete = findViewById(R.id.manu);
        dateEdit = findViewById(R.id.dateEdit);
        enterName = findViewById(R.id.entername);
        enterPhotographer = findViewById(R.id.enterphotographer);

        textDistric = findViewById(R.id.textdistric);
        textView4 = findViewById(R.id.textView4);
        textView6 = findViewById(R.id.textView6);
        textName = findViewById(R.id.textname);
        textGrapher = findViewById(R.id.textgrapher);
        logout = findViewById(R.id.logoutbtn);

//      gets current year,month and day
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        logout.setOnClickListener(this);

//      assigns AutoCompleteTextView to variable
        autoComplete= findViewById(R.id.manu);
        adapterItem = new ArrayAdapter<String>(this,R.layout.list_item,distric);

//      sets adapter for AutoCompleteTextView
        autoComplete.setAdapter(adapterItem);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String distric = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(InformationActivity.this, "distric "+ distric, Toast.LENGTH_SHORT).show();
            }
        });
        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//      This sets up a DatePickerDialog for the user to select a date
                DatePickerDialog datePickerDialog = new DatePickerDialog(InformationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day + "/" + month + "/" + year;
                        dateformat.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
//      These three buttons are used to select different options
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoText.setText("Other");
                btn1.setBackgroundColor(getResources().getColor(R.color.cream_color));
                btn2.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btn3.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoText.setText("Groom");
                btn1.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btn2.setBackgroundColor(getResources().getColor(R.color.cream_color));
                btn3.setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoText.setText("Bride");
                btn1.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btn2.setBackgroundColor(getResources().getColor(R.color.light_gray));
                btn3.setBackgroundColor(getResources().getColor(R.color.cream_color));
            }
        });

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//              Get values from input fields
                String Name = autoComplete.getText().toString();
                String Name1 = dateEdit.getText().toString();
                String Name2 = autoText.getText().toString();
                String Name3 = enterName.getText().toString();
                String Name4 = enterPhotographer.getText().toString();

                String text = textDistric.getText().toString();
                String text1 = textView4.getText().toString();
                String text2 = textView6.getText().toString();
                String text3 = textName.getText().toString();
                String text4 = textGrapher.getText().toString();

//              Create an intent to start MyPlanes2Activity and pass the values as extras
                Intent intent = new Intent(InformationActivity.this, MyPlanes2Activity.class);
                intent.putExtra("keyname",Name);
                intent.putExtra("keyname1",Name1);
                intent.putExtra("keyname2",Name2);
                intent.putExtra("keyname3",Name3);
                intent.putExtra("keyname4",Name4);

                intent.putExtra("keytextname",text);
                intent.putExtra("keytextname1",text1);
                intent.putExtra("keytextname2",text2);
                intent.putExtra("keytextname3",text3);
                intent.putExtra("keytextname4",text4);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logoutbtn:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                break;

            default:
                Toast.makeText(this, "In default", Toast.LENGTH_SHORT).show();
        }

    }
}