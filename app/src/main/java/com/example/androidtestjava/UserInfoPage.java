package com.example.androidtestjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidtestjava.adapter.CustomAdapter;
import com.example.androidtestjava.model.UserInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserInfoPage extends AppCompatActivity{
    public ListView userInfo;
    public CustomAdapter userViewAdapter;
    public List<UserInfo> userInfoList;
    public List<String> idlist;
    Database database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_page);
        userInfo= findViewById(R.id.userView);
        userInfoList= new ArrayList<>();
        idlist= new ArrayList<>();
        UserInfo info;
        database= new Database(this);
        Cursor result =database.viewData();
        if(result.getCount()==0){
            Toast.makeText(this, "No Data display", Toast.LENGTH_SHORT).show();
        }
        else{
            while (result.moveToNext()){
                info= new UserInfo(result.getString(1),result.getString(2),
                        result.getString(3),result.getString(4),result.getString(5),
                        result.getString(6));
                userInfoList.add(info);
                idlist.add(result.getString(0));
                Toast.makeText(this, "Successfully done"+userInfoList.size(), Toast.LENGTH_SHORT).show();
            }
            if(userInfoList.size()!=0){
                userViewAdapter = new CustomAdapter(this,userInfoList,idlist);
                userInfo.setAdapter(userViewAdapter);
                userViewAdapter.notifyDataSetChanged();
                userInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(UserInfoPage.this,ViewUserInfo.class);
                        intent.putExtra("possition",idlist.get(position));
                        intent.putExtra("name",userInfoList.get(position).getName());
                        intent.putExtra("city",userInfoList.get(position).getCityName());
                        intent.putExtra("country",userInfoList.get(position).getCountryName());
                        intent.putExtra("date",userInfoList.get(position).getDateOfBirth());
                        intent.putExtra("skills",userInfoList.get(position).getLanguageSkill());
                        intent.putExtra("pdf",userInfoList.get(position).getResumePath());
                        startActivity(intent);

                    }


                });
            }
            else{
                Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            }

        }







    }


}