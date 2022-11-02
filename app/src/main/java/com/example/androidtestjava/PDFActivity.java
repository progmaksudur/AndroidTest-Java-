package com.example.androidtestjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;


public class PDFActivity extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity);
        String preFile= getIntent().getStringExtra("pdf");
        System.out.println(preFile);
        pdfView= findViewById(R.id.pdfView);
        File pdfFile = new File(Environment.getExternalStorageDirectory(),preFile);
        if (pdfFile.exists()) //Checking for the file is exist or not
        {
            pdfView.fromFile(pdfFile).load();//Staring the pdf viewer
        }
        else{

            Toast.makeText(this, "Cant Find", Toast.LENGTH_SHORT).show();
        }

        
    }
}