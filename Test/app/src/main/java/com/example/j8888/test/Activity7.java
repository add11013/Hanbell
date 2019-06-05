package com.example.j8888.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Activity7 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_7);
        TextView txt=(TextView) findViewById(R.id.textView10);
        WebView webView1 = (WebView) findViewById(R.id.webview1);
        try{
        webView1.setWebChromeClient(new WebChromeClient());
        webView1.setWebViewClient(new WebViewClient());
        webView1.getSettings().setJavaScriptEnabled(true);
        webView1.loadUrl("https://histock.tw/global/history.aspx?mid=13&no=SZ002158");

        }
        catch (Exception e){
            txt.setText(e.toString());
        }
    }
}
