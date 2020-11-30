package com.example.dhrutnyay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class RetrieveForm extends AppCompatActivity {
    EditText Reg_textView;
    private String TAG = "DynamoDb_Demo";
    private EditText type_textView;
    private EditText id_textView;
    private EditText name_textView;
    private EditText location_textView;
    private EditText date_textView;
    private EditText time_textView;
    private EditText status_textView;
    String data = new String();
    Document mdoc =new Document();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);
        Button buttonGetItem = findViewById(R.id.fetch);
        Reg_textView=(EditText)findViewById(R.id.Registration_No);
//        final EditText Complaint_id=(EditText)findViewById(R.id.Complaint_id);
//        final EditText Complainant_name=(EditText)findViewById(R.id.Complainant_name);
//        final EditText Complaint_type=(EditText)findViewById(R.id.Complainant_type);
//        final EditText Location=(EditText)findViewById(R.id.Location);
//        final EditText Date=(EditText)findViewById(R.id.Date);
//        //final EditText time1=(EditText)findViewById(R.id.Time);
//        //final EditText desc=(EditText)findViewById(R.id.desc);

        int number= ThreadLocalRandom.current().nextInt(100000,1000000);


        //Button buttonGetItem = findViewById(R.id.fetch);
        id_textView = findViewById(R.id.Complaint_id);
        type_textView = findViewById(R.id.Complainant_type);
        name_textView = findViewById(R.id.Complainant_name);
        location_textView = findViewById(R.id.Location);
        date_textView = findViewById(R.id.Date);
        //time_textView = findViewById(R.id.Time);
        status_textView = findViewById(R.id.status);


        buttonGetItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = Reg_textView.getText().toString();


                //Log.i(TAG, "Getting all devices...");

                //textView.setText("Getting all devices...");


                GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask();

                try {



                    mdoc =  getAllDevicesTask.execute().get();
//                    id_textView.setText(test_type1);
//
//                String test_type2=DataReceived.get("complaint").convertToAttributeValue().getS();
//                name_textView.setText(test_type2);
//
//                String test_type3=DataReceived.get("location").convertToAttributeValue().getS();
//                location_textView.setText(test_type3);
//
//                String test_type4=DataReceived.get("date").convertToAttributeValue().getS();
//                date_textView.setText(test_type4);
//
//                String test_type5=DataReceived.get("Time1").convertToAttributeValue().getS();
//                time_textView.setText(test_type5);
//
//                String test_type6=DataReceived.get("status").convertToAttributeValue().getS();
//                status_textView.setText(test_type6);



            Log.d(TAG, "databases content"+mdoc.get("complaint_id").convertToAttributeValue().getS());
            String test_type=mdoc.get("complaint_id").convertToAttributeValue().getS();
            String test_type1=mdoc.get("complaint_type").convertToAttributeValue().getS();
            String test_type2=mdoc.get("complaint").convertToAttributeValue().getS();
            String test_type3=mdoc.get("location").convertToAttributeValue().getS();
            String test_type4=mdoc.get("date").convertToAttributeValue().getS();
            String test_type5=mdoc.get("Time1").convertToAttributeValue().getS();
            String test_type6=mdoc.get("status").convertToAttributeValue().getS();

            id_textView.setText(test_type);
            type_textView.setText(test_type1);
            name_textView.setText(test_type2);
            location_textView.setText(test_type3);
            date_textView.setText(test_type4);
            status_textView.setText(test_type6);







                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private class GetAllItemsAsyncTask extends AsyncTask<Void, Void, Document> {
        @Override
        protected Document doInBackground(Void... params) {

            String number = Reg_textView.getText().toString();

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(RetrieveForm.this);
            Document DataReceived= databaseAccess.getItem_Complaint(number);
//            Log.d(TAG, "databases content"+databaseAccess.getItem_Complaint(number).toString());
//
//            String test_type=DataReceived.get("complaint_type").convertToAttributeValue().getS();
//            type_textView.setText(test_type);
//            String test_type1=DataReceived.get("complaint_id").convertToAttributeValue().getS();
//
//            Log.d(TAG,"data retrieved"+" "+test_type1);

//
            Log.d(TAG,"data retrieved"+" "+databaseAccess.getAllItems());

            return DataReceived;




        }




        @Override
        protected void onPostExecute(Document documents) {
        }

    }




    public void send(View view){
        Intent intent=new Intent(this,FIRForm.class);
        startActivityForResult(intent,1);
    }
}
