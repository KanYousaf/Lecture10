package com.example.kanwalpc.datastorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText season_name_et, season_new_name_et;
    private TextView season_details_display;
    private RatingBar season_rate_bar;
    private String season_name,season_new_name;
    private Float season_rate;
    myOwnDBHandler adapter_DB_Handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        season_name_et=(EditText)this.findViewById(R.id.season_name);
        season_new_name_et=(EditText)this.findViewById(R.id.season_name_new);
        season_rate_bar=(RatingBar)this.findViewById(R.id.season_rating_bar);
        season_details_display=(TextView)this.findViewById(R.id.displayList);

        adapter_DB_Handler=new myOwnDBHandler(MainActivity.this);

        printDatabase();
    }

    public void onInsertClicked(View view) {
        season_name=season_name_et.getText().toString();
        season_rate=season_rate_bar.getRating();
        if(season_name.equals("")){
            Message.message(MainActivity.this, "Enter season name");
        }else {
            long id=adapter_DB_Handler.insertInToTable(season_name, season_rate);
            if(id>=0){
                Message.message(MainActivity.this, "Data entered successfully");
            }else{
                Message.message(MainActivity.this, "Data not entered");
            }
            printDatabase();
        }
    }

    public void printDatabase(){
        season_details_display.setText(adapter_DB_Handler.printDatabase());
        clearFields();
    }

    public void onSearchClicked(View view) {
        season_name=season_name_et.getText().toString();
        if (season_name.equals("")) {
            Message.message(MainActivity.this, "Enter season name to search");
        } else {
            season_details_display.setText(adapter_DB_Handler.searchSeasonName(season_name));
            clearFields();
        }
    }

    public void onUpdateClicked(View view) {
        season_name=season_name_et.getText().toString();
        season_rate=season_rate_bar.getRating();
        season_new_name=season_new_name_et.getText().toString();
        if(season_name.equals("")||season_new_name.equals("")){
            Message.message(MainActivity.this, "Enter Season New and Old ");
        }else {
            int count = adapter_DB_Handler.setUpdatedRecord(season_name, season_new_name, season_rate);
                if(count >0){
                    Message.message(MainActivity.this, "Data updated successfully");
                }else{
                    Message.message(MainActivity.this, "Data not updated");
                }
            printDatabase();
            clearFields();
        }
    }

    public void onDeleteClicked(View view) {
        season_name = season_name_et.getText().toString();
        season_rate=season_rate_bar.getRating();
        season_new_name=season_new_name_et.getText().toString();
        if (season_name.equals("")) {
            Message.message(MainActivity.this, "Enter Season name to delete");
        } else {
            int count=adapter_DB_Handler.deleteItem(season_name);
            if(count>0) {
                Message.message(MainActivity.this, "Data deleted Successfully");
            }else {
                Message.message(MainActivity.this, "Data Not Deleted");
            }
            printDatabase();
        }
    }

    public void clearFields(){
        season_name_et.setText("");
        season_new_name_et.setText("");
        season_rate_bar.setRating(2);
    }
}
