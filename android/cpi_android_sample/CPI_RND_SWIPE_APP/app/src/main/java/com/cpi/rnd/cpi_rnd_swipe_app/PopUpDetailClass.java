package com.cpi.rnd.cpi_rnd_swipe_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class PopUpDetailClass extends Activity {

    private String sPrice;
    private String rPrice;
    private String sTitle;
    private String rTitle;
    private String sURL;
    private String rURL;

    private TextView sPriceTextView;
    private TextView rPriceTextView;
    private TextView sTitleTextView;
    private TextView rTitleTextView;
    private TextView sUrlTextView;
    private TextView rUrlTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sPrice = extras.getString("sPrice");
            rPrice = extras.getString("rPrice");
            sTitle = extras.getString("sTitle");
            rTitle = extras.getString("rTitle");
            sURL = extras.getString("sUrl");
            rURL = extras.getString("rUrl");

        }

        sPriceTextView = findViewById(R.id.sPrice);
        rPriceTextView = findViewById(R.id.rPrice);
        sTitleTextView = findViewById(R.id.sName);
        rTitleTextView = findViewById(R.id.rName);
        sUrlTextView = findViewById(R.id.sURL);
        rUrlTextView = findViewById(R.id.rURL);

        sPriceTextView.setText(sPrice);
        rPriceTextView.setText(rPrice);
        sTitleTextView.setText(sTitle);
        rTitleTextView.setText(rTitle);
        sUrlTextView.setText(sURL);
        rUrlTextView.setText(rURL);

        sUrlTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(sURL));
                startActivity(browserIntent);
            }
        });

        rUrlTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(rURL));
                startActivity(browserIntent);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.95), (int)(height*.6));
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        return super.dispatchTouchEvent(ev);
    }
}
