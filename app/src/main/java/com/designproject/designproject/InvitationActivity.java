package com.designproject.designproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class InvitationActivity extends AppCompatActivity {

//  Declare variables
    public ArrayList<String> items;
    public ArrayAdapter<String> itemAdapter;
    public ListView listView;
    public Button button,save;
    ImageView backIcon,icon;
    TextView invitation;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
//      getSupportActionBar().hide();


//      Initialize variables
        items = new ArrayList<>();
        backIcon = findViewById(R.id.backIcon);
        icon = findViewById(R.id.backIcon);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.addBtn);
        save = findViewById(R.id.savebtn);
        invitation = findViewById(R.id.invite);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer stringBuffer = new StringBuffer();
                int i = 0;
                while (i < items.size()) {
                    stringBuffer.append(items.get(i) + "\n");
                    i++;
                }
                String names = stringBuffer.toString();
                String text = invitation.getText().toString();

                Intent intent = new Intent(InvitationActivity.this, MyPlanesActivity.class);
                intent.putExtra("keyname",names);
                intent.putExtra("keytextname",text);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
        itemAdapter =new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(itemAdapter);
        setUpListViewListener();

//      Set up listener for add icon to add item to list
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//      Set up the bottom navigation view and handle item selection
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navi_item);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homeId:
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    return true;

                case R.id.listId:
                    startActivity(new Intent(getApplicationContext(),InvitationActivity.class));
                    return true;

                case R.id.hotelId:
                    startActivity(new Intent(getApplicationContext(),WeddingPlanesActivity.class));
                    return true;

                case R.id.menuId:
                    startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                    return true;
            }
            return false;
        });

    }
//  Set up listener for long click on item to remove it from list
     private void setUpListViewListener() {
         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Context context = getApplicationContext();
//                 ArrayList<Object> items = new ArrayList<>();
                 itemAdapter.remove(items.get(i));
                 items.remove(i);
                 Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();
                 itemAdapter.notifyDataSetChanged();
                 return true;
             }
         });
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Toast.makeText(InvitationActivity.this, "Please Long press to delete items", Toast.LENGTH_SHORT).show();
             }
         });
     }
//  Add item to list
    private void addItem(View view) {
         EditText input = findViewById(R.id.textName);
         String itemText = input.getText().toString();

         if (!(itemText.equals(""))){
         itemAdapter.add(itemText);
         items.add(itemText);
         input.setText("");
         }else{
         Toast.makeText(getApplicationContext(),"please enter text..",Toast.LENGTH_LONG).show();
         }

         backIcon.setOnClickListener(new View.OnClickListener() {
             @Override
         public void onClick(View view) {
             Intent now = new Intent(InvitationActivity.this, HomeActivity.class);
             startActivity(now);
         }

         });

    }
}
