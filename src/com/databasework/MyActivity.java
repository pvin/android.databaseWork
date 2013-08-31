package com.databasework;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
 
public class MyActivity extends Activity
{
    private DataBaseManager dataBase;
 
    private Button insertButton;
    private Button updateButton;
    private Button deleteButton;
 
    private TextView textView;
 
    //put the table name and column in constants
    public static final String TABLE_NAME = "People";
    public static final String COLUMN_NAME = "name";
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        //creates and open the database so we can use it
        dataBase = DataBaseManager.instance();
 
        textView = (TextView)findViewById(R.id.name_1);
        insertButton = (Button)findViewById(R.id.insert);
        updateButton = (Button)findViewById(R.id.update);
        deleteButton = (Button)findViewById(R.id.delete);
 
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                //with ContentValues put the data we want into the database
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, "Diana");
 
                //here we insert the data we have put in values
                dataBase.insert(TABLE_NAME, values);
 
                updateTextView();
 
            }
        });
 
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                //with ContentValues put the data we want into the database
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME,"George");
 
                //here we replace the record which has the _id=1 with the given name in the values "George"
                dataBase.update(TABLE_NAME, values, "_id=1");
 
                updateTextView();
            }
        });
 
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 
                //here we delete the record which has the "name=George"
                dataBase.delete(TABLE_NAME,"name='George'");
 
                updateTextView();
 
 
            }
        });
 
    }
 
    public void updateTextView(){
        //to get data from database we need a cursor.
        //after we perform a select query all the data from database specific for the query, will be in the cursor
        // "*" means "all" in translation the query means "SELECT ALL FROM NAME TABLE"
        Cursor cursor = dataBase.select("SELECT * FROM " + TABLE_NAME);
      
        textView.setText("");
        //the cursor iterates the column "name"
        while (cursor.moveToNext()){
            //in this string we get the record for each row from the column "name"
 
            String s = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
 
            //in this textView will be added, updated or deleted the string
            // "\n" means "new line"
            textView.append("\n" + s);
        }
 
        //here we close the cursor because we do not longer need it
        cursor.close();
    }
}