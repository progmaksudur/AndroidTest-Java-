package com.example.androidtestjava.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtestjava.Database;
import com.example.androidtestjava.MainActivity;
import com.example.androidtestjava.R;
import com.example.androidtestjava.UserInfoPage;
import com.example.androidtestjava.ViewUserInfo;
import com.example.androidtestjava.model.UserInfo;

import java.util.List;
public  class CustomAdapter extends BaseAdapter{
    Database database;
    private Context context;
    private List<UserInfo> userInfoArrayList;
    private List<String> idList;
    private  LayoutInflater inflater;
    private AlertDialog.Builder alertDialog;

    public CustomAdapter(Context context, List<UserInfo> userInfoArrayList, List<String> idList) {
        this.context = context;
        this.userInfoArrayList = userInfoArrayList;
        this.idList=idList;

    }

    @Override
    public int getCount() {
        return userInfoArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userInfoArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        database= new Database(context.getApplicationContext());

        if(convertView==null){

            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.listview_iteams,parent,false);

        }

        TextView  nameText= convertView.findViewById(R.id.userName);
        TextView dateText=convertView.findViewById(R.id.userDateOfBirth);
        AppCompatButton deleteButton= convertView.findViewById(R.id.delete);
        AppCompatButton showButton= convertView.findViewById(R.id.view);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idList.get(position);
                if(position == -1){
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                       database.deleteData(id);
                       userInfoArrayList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, "Sucessfully Delete", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(context, ""+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "User Name: "+userInfoArrayList.get(position).getName()+"\n City:"+userInfoArrayList.get(position).getCityName()+
                        "\n Country:"+userInfoArrayList.get(position).getCountryName()+"\n DOB:"+userInfoArrayList.get(position).getDateOfBirth()+"\n Skills"+
                        userInfoArrayList.get(position).getLanguageSkill()+"\n", Toast.LENGTH_SHORT).show();
            }
        });

       nameText.setText(userInfoArrayList.get(position).getName());
       dateText.setText(userInfoArrayList.get(position).getDateOfBirth());
        return convertView;
    }

}







