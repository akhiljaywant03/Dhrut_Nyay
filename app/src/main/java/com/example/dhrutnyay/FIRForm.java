package com.example.dhrutnyay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class FIRForm extends AppCompatActivity {
    String data = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        final EditText Complaint_id=(EditText)findViewById(R.id.Complaint_id);
        final EditText Complainant_name=(EditText)findViewById(R.id.Complainant_name);
        final EditText Complaint_type=(EditText)findViewById(R.id.Complaint_type);
        final EditText Location=(EditText)findViewById(R.id.Location);
        final EditText Date=(EditText)findViewById(R.id.Date);
        final EditText time1=(EditText)findViewById(R.id.Time);
        final EditText desc=(EditText)findViewById(R.id.desc);

        //Random rnd=new Random();
        int number= ThreadLocalRandom.current().nextInt(100000,1000000);

        Date dNow=new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);

        Complaint_id.setText(""+datetime);
        Complaint_id.setKeyListener(null);
        //String complaint_id=String.format(Locale,number);


        Button add=(Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String complaint_id=Complaint_id.getText().toString();
                String complainant_name=Complainant_name.getText().toString();
                String complaint_type=Complaint_type.getText().toString();
                String location=Location.getText().toString();
                String date=Date.getText().toString();
                String Time1=time1.getText().toString();
                String complaint=desc.getText().toString();

                GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask(complaint_id ,complaint,complainant_name,complaint_type,location,date,Time1);

                try {

                    data=getAllDevicesTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //AddItem(complaint_id,complainant_name,complaint_type,location,date,Time1);


            }
        });


    }
    private class GetAllItemsAsyncTask extends AsyncTask<String, Void, String> {

        private String complaint_id;
        private String test_id;
        private String complainant_name;
        private String complaint_type;
        private String location;
        private String date;
        private String Time1;
        private String complaint;

        public GetAllItemsAsyncTask(String complaint_id,String complaint, String complainant_name,String complaint_type,String location,String date,String Time1){
            this.complaint_id=complaint_id;
            this.complainant_name=complainant_name;
            this.complaint_type=complaint_type;
            this.location=location;
            this.date=date;
            this.Time1=Time1;
            this.complaint=complaint;

        }

        @Override
        protected String doInBackground(String... strings) {

            DatabaseAccess databaseAccess=DatabaseAccess.getInstance(FIRForm.this);

            Document DataReceived= databaseAccess.getItem_Complaint("2");
            String test_type=DataReceived.get("complaint_type").convertToAttributeValue().getS();
            //Log.d("Data Received","database content" + databaseAccess.getItem_Complaint("2").toString());
            //Log.e(DataReceived,"coming from database");
            Log.d("Data received","from db"+test_type);
            //Log.d("Data recieved","database content"+databaseAccess.getItem_Complaint("2").toString());

            Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();


            item.put("complaint_id",new AttributeValue().withS(complaint_id));
            item.put("complainant",new AttributeValue().withS(complainant_name));
            item.put("complaint_type",new AttributeValue().withS(complaint_type));
            item.put("location",new AttributeValue().withS(location));
            item.put("date",new AttributeValue().withS(date));
            item.put("Time1",new AttributeValue().withS(Time1));
            item.put("complaint",new AttributeValue().withS(complaint));


            PutItemRequest putItemRequest=new PutItemRequest("Complaint",item);

            PutItemResult putItemResult=databaseAccess.dbClient.putItem(putItemRequest);

            return null;
        }
    }
}