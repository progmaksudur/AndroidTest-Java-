package com.example.androidtestjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ViewUserInfo extends AppCompatActivity {
    final Calendar umyCalendar= Calendar.getInstance();
    Intent umyFileintent;
    public TextView udateOfBirthTextView,uskillText,updatename,updateadd,updateDate;
    public AppCompatButton updatebutton,udatePickerButton,pdfButton;
    public Spinner ucountrySpiner,ucitySpiner;
    public EditText unameEditTextfield;
    public CheckBox uengCheckbox,ubanCheckbox,ufrnCheckbox,uarbCheckbox,uhinCheckbox,ujapCheckbox;
    public Database database;
    String[] countryNames;
    String[] citynames;
    private  ArrayAdapter<String> ucountryAdapter,ucityAdapter;
    public HelperClass halperClass;

    String upskills;
    String updateofbirth;
    String upname;
    String upcountry;
    String upcity;
    String upfilePath;
    String prename,precity,precountry,predate,preskill,id,preFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_info);
        setTitle("Update Page");

         id= getIntent().getStringExtra("possition");
         prename= getIntent().getStringExtra("name");
         precity= getIntent().getStringExtra("city");
         precountry= getIntent().getStringExtra("country");
         predate= getIntent().getStringExtra("date");
         preskill= getIntent().getStringExtra("skills");
         preFile= getIntent().getStringExtra("pdf");


        unameEditTextfield=findViewById(R.id.updateName);

        udateOfBirthTextView=findViewById(R.id.updateofBirth);
        uskillText=findViewById(R.id.upskill);
        updatename=findViewById(R.id.preName);
        updateadd=findViewById(R.id.preadd);
        updateDate=findViewById(R.id.updateofBirth);

        updatebutton=findViewById(R.id.updateButton);
        udatePickerButton=findViewById(R.id.updatePickerAction);
        pdfButton=findViewById(R.id.viewPDFButton);

        ucountrySpiner=findViewById(R.id.upcountryDropDown);
        ucitySpiner=findViewById(R.id.upcityDropDown);

        uengCheckbox=findViewById(R.id.upenglishCheckBox);
        ubanCheckbox=findViewById(R.id.upbanglaCheckBox);
        ufrnCheckbox=findViewById(R.id.upfranchCheckBox);
        uarbCheckbox=findViewById(R.id.uparabicCheckBox);
        uhinCheckbox=findViewById(R.id.uphindiCheckBox);
        ujapCheckbox=findViewById(R.id.upjapaneseCheckBox);

        updatename.setText(prename);
        uskillText.setText(preskill);
        updateadd.setText(precity+","+precountry);
        updateDate.setText(predate);

        database= new Database(this);
        SQLiteDatabase sqLiteDatabase =database.getWritableDatabase();
        halperClass= new HelperClass();
        countryNames=halperClass.countryName;


        ucountryAdapter=new ArrayAdapter<String>(this,R.layout.samplespinerview,R.id.iteamname,countryNames);
       ucountrySpiner.setAdapter(ucountryAdapter);
        ucountrySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vname=countryNames[position];
                citynames=halperClass.CheckCity(vname);
                ucityAdapter=new ArrayAdapter<String>(parent.getContext(),R.layout.samplespinerview,R.id.iteamname,citynames);
                ucitySpiner.setAdapter(ucityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DatePickerDialog.OnDateSetListener dateuplis =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                umyCalendar.set(Calendar.YEAR, year);
                umyCalendar.set(Calendar.MONTH,month);
                umyCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
            private void updateLabel(){
                String myFormat="dd/MM/yy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                updateDate.setText(dateFormat.format(umyCalendar.getTime()));
                updateofbirth=dateFormat.format(umyCalendar.getTime()).toString();
            }
        };
        udatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ViewUserInfo.this,dateuplis,umyCalendar.get(Calendar.YEAR),umyCalendar.get(Calendar.MONTH),umyCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upname=unameEditTextfield.getText().toString();
                upcountry=ucountrySpiner.getSelectedItem().toString();
                upcity=ucitySpiner.getSelectedItem().toString();
                checkBoxSelect();
                checkValidate();
                UserInfo info= new UserInfo(upname,updateofbirth,upcountry,upcity,upskills,preFile);
                try{
                    database.upgrade(id,info);
                    Toast.makeText(ViewUserInfo.this, "Sucessfully Update", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ViewUserInfo.this,UserInfoPage.class);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(ViewUserInfo.this, ""+e, Toast.LENGTH_SHORT).show();
                }
                }



        });
        pdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent= new Intent(ViewUserInfo.this,PDFActivity.class);
                    intent.putExtra("pdf",preFile);
                    startActivity(intent);
            }
        });
    }
    void checkBoxSelect(){
        StringBuilder checkIteamBuilder= new StringBuilder();
        if(uengCheckbox.isChecked()){
            String value=uengCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(ubanCheckbox.isChecked()){
            String value=ubanCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(uhinCheckbox.isChecked()){
            String value=uhinCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(ufrnCheckbox.isChecked()){
            String value=ufrnCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(uarbCheckbox.isChecked()){
            String value=uarbCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }
        if(ujapCheckbox.isChecked()){
            String value=ujapCheckbox.getText().toString();
            checkIteamBuilder.append(value +",");
        }

        upskills=checkIteamBuilder.toString();


    }
    void checkValidate(){
        if(upname.isEmpty()||upname.equals(null)){
            upname=prename;
        }
        if(updateofbirth==null){
            updateofbirth=predate;
        }
        if(upskills.isEmpty()||upskills.equals(null)){
            upskills=preskill;
        }

    }
}