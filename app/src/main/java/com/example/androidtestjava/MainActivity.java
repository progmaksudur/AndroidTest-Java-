package com.example.androidtestjava;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidtestjava.assets.HelperClass;
import com.example.androidtestjava.model.UserInfo;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    Intent mintent;
    public TextView dateOfBirthTextView,fileName,skillText;
    public AppCompatButton savebutton,userInfoButton,datePickerButton,uploadButton;
    public Spinner countrySpiner,citySpiner;
    public EditText nameEditTextfield;
    public CheckBox engCheckbox,banCheckbox,frnCheckbox,arbCheckbox,hinCheckbox,japCheckbox;

    String[] countryNames;
    String[] citynames;
    String skills;
    String dateofbirth;
    String name;
    String country;
    String city;
    String filePath;
    private  ArrayAdapter<String> countryAdapter,cityAdapter;
    public HelperClass halperClass;
    public Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Insert Page");

        nameEditTextfield = findViewById(R.id.nameEditText);
        dateOfBirthTextView = findViewById(R.id.dateofBirth);
        database= new Database(this);
        SQLiteDatabase sqLiteDatabase =database.getWritableDatabase();


        fileName=findViewById(R.id.filename);
        skillText=findViewById(R.id.skill);
        countrySpiner= findViewById(R.id.countryDropDown);
        citySpiner= findViewById(R.id.cityDropDown);
        savebutton = findViewById(R.id.saveButton);

        userInfoButton = findViewById(R.id.showUserData);
        datePickerButton = findViewById(R.id.datePickerAction);
        uploadButton = findViewById(R.id.uploadButton);
        engCheckbox = findViewById(R.id.englishCheckBox);
        banCheckbox = findViewById(R.id.banglaCheckBox);
        frnCheckbox = findViewById(R.id.franchCheckBox);
        arbCheckbox = findViewById(R.id.arabicCheckBox);
        hinCheckbox = findViewById(R.id.hindiCheckBox);
        japCheckbox = findViewById(R.id.japaneseCheckBox);

        halperClass= new HelperClass();
        countryNames=halperClass.countryName;


        countryAdapter=new ArrayAdapter<String>(this,R.layout.samplespinerview,R.id.iteamname,countryNames);
        countrySpiner.setAdapter(countryAdapter);
        countrySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name=countryNames[position];
                citynames=halperClass.CheckCity(name);
                cityAdapter=new ArrayAdapter<String>(parent.getContext(),R.layout.samplespinerview,R.id.iteamname,citynames);
                citySpiner.setAdapter(cityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
            private void updateLabel(){
                String myFormat="dd/MM/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                dateOfBirthTextView.setText(dateFormat.format(myCalendar.getTime()));
                dateofbirth=dateFormat.format(myCalendar.getTime()).toString();
            }


        };

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mintent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                mintent.setType("application/pdf");
                startActivityForResult( mintent,2);

            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameEditTextfield.getText().toString();
                 country=countrySpiner.getSelectedItem().toString();
                 city=citySpiner.getSelectedItem().toString();
                 checkBoxSelect();

                boolean recheck=checkValidate();
                 if(recheck==true){
                     UserInfo info= new UserInfo(name,dateofbirth,country,city,skills,filePath);
                     long datacheck=database.insert(info);
                     if(datacheck==-1){
                         Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                     }
                     else{
                         Toast.makeText(MainActivity.this, "Sucessfully Save", Toast.LENGTH_SHORT).show();
                         clearAlldata();
                     }
                 }


            }
        });
        userInfoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,UserInfoPage.class);
               startActivity(intent);
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 2:
                if( resultCode==RESULT_OK){
                        filePath=data.getData().getPath();
                        System.out.println(filePath);
                        if(filePath!=""||filePath!=null){
                            fileName.setText("save");
                        }
                }
                break;

        }




    }
    void checkBoxSelect(){
        StringBuilder checkIteamBuilder= new StringBuilder();
        if(engCheckbox.isChecked()){
            String value=engCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(banCheckbox.isChecked()){
            String value=banCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(hinCheckbox.isChecked()){
            String value=hinCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(frnCheckbox.isChecked()){
            String value=frnCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(arbCheckbox.isChecked()){
            String value=arbCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(japCheckbox.isChecked()){
            String value=japCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }

        skills=checkIteamBuilder.toString();
        System.out.println(skills);

    }
    boolean checkValidate(){
        if(name.isEmpty()||name.equals(null)){
            nameEditTextfield.setError("Enter Name");
            return false;

        }
        else if(dateofbirth==null){
            dateOfBirthTextView.setError("Select data of birth");
            return false;
        }
        else if(skills.isEmpty()||skills.equals(null)){
            skillText.setError("Select Skill");
            return false;

        }
        else if(filePath==null){
            fileName.setError("Select resume");
            return false;
        }
        else {
            return true;
        }
    }
    void clearAlldata(){
        nameEditTextfield.setText("");
        dateOfBirthTextView.setText("");
        engCheckbox.setChecked(false);
        banCheckbox.setChecked(false);
        hinCheckbox.setChecked(false);
        frnCheckbox.setChecked(false);
        arbCheckbox.setChecked(false);
        japCheckbox.setChecked(false);
        fileName.setText("");
    }




}
