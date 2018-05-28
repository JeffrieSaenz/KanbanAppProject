package com.example.user.kanbanapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewFile extends AppCompatActivity {
    ProgressDialog pDialog;

    WebView webview ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);
        init();
        listener();
    }
    private void init(){
        Intent callingIntent = getIntent();
        //int numhijos = callingIntent.getIntExtra("url", 1);
        String url = callingIntent.getStringExtra("url");
        webview  = (WebView) findViewById(R.id.visor);
        webview.getSettings().setJavaScriptEnabled(true);

        pDialog = new ProgressDialog(ViewFile.this);
        //pDialog.setTitle("PDF");
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        webview.loadUrl(url);
    }
    private void listener() {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pDialog.show();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pDialog.dismiss();
            }
        });
    }
}
