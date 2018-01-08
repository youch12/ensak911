package com.ensak911.emplois_annonces.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ensak911.R;
import com.ensak911.demandes.activities.AddDemandeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ModifiyScheduleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView DatePub;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static int IMG_RESULT = 1;
    String ImageDecode;
    ImageView imageViewLoad;
    ImageView LoadImage;
    Intent intent;
    Spinner year, option;
    Button button;
    String[] FILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_emploi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.modify_schedule_title);
        imageViewLoad = (ImageView) findViewById(R.id.imageView1);
        LoadImage = (ImageView)findViewById(R.id.button1);
        button = (Button) findViewById(R.id.save_emploi);
        year = (Spinner) findViewById(R.id.spinner_year);
        option = (Spinner) findViewById(R.id.spinner_option);
        year.setOnItemSelectedListener(this);


        DatePub = (TextView) findViewById(R.id.DatePub);
        DatePub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ModifiyScheduleActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                DatePub.setText(date);

            }
        };




        LoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);

            }
        });


        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getBaseContext(), AddDemandeActivity.class);
                startActivity(i);
            }



        });


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(URI,
                        FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();

                imageViewLoad.setImageBitmap(BitmapFactory
                        .decodeFile(ImageDecode));

            }
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.try_again), Toast.LENGTH_LONG)
                    .show();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        String sp1= String.valueOf(year.getSelectedItem());

        if(sp1.contentEquals("3 éme année") || (sp1.contentEquals("4 éme année") || (sp1.contentEquals("5 éme année")))) {
            List<String> list = new ArrayList<String>();
            option.setEnabled(true);
            option.setClickable(true);
            list.add("Génie Informatique");
            list.add("Génie industriel");
            list.add("Génie électrique");
            list.add("Réseaux et télécom");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            option.setAdapter(dataAdapter);
        }
        if(sp1.contentEquals("1 ère année") || (sp1.contentEquals("2 éme année"))) {
            List<String> list = new ArrayList<String>();
            list.add("Séction A");
            list.add("Séction B");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            option.setAdapter(dataAdapter2);
        }

        if(sp1.contentEquals("")){

            ((TextView)year.getChildAt(0)).setError("Mandatory");

        }


    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
