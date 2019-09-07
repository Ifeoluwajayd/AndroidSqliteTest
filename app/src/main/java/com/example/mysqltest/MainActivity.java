package com.example.mysqltest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    dataBaseHelper MyDb;
    EditText blankName, blankSurname, blankMark;
    Button addData, downloadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDb = new dataBaseHelper(this);

        blankName = findViewById(R.id.blankName);
        blankSurname = findViewById(R.id.blankSurname);
        blankMark = findViewById(R.id.blankMark);
        addData = findViewById(R.id.addData);
        downloadData = findViewById(R.id.downloadData);

        addDataFromDB();
        downloadDataFromDB();
    }

    private void downloadDataFromDB() {
        downloadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor isDownloaded = MyDb.getAllData();
                if(isDownloaded.getCount() == 0){
                    /**Show Message*/
                    showMessage("Error","No Data Found!!!");
                    return;
                }
                else{
                    StringBuffer buffer = new StringBuffer();
                    while (isDownloaded.moveToNext()){
                        buffer.append("Id :"+ isDownloaded.getString(0) + "\n");
                        buffer.append("Name :"+ isDownloaded.getString(1) + "\n");
                        buffer.append("Surname :"+ isDownloaded.getString(2) + "\n");
                        buffer.append("Mark :"+ isDownloaded.getString(3) + "\n\n");
                    }

                    showMessage("Data",buffer.toString());
                }
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void addDataFromDB(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = MyDb.insertData(blankName.getText().toString(),blankSurname.getText().toString(),
                        blankMark.getText().toString());
                if(isInserted = true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
