package com.zoe.progressview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zoe.progressview.view.ProgressViewWidget;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressViewWidget progressview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(100);
            }
        });
        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(10);
            }
        });
        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(75);
            }
        });
        progressview = findViewById(R.id.pvw);
        progressview.setWidthAndHeight(800, 800);
        LinearLayout.LayoutParams progressviewParams = new LinearLayout.LayoutParams(800, 800);
        progressviewParams.topMargin = 100;
        progressview.setLayoutParams(progressviewParams);
        setData(80);
    }

    private void setData(int times) {
        progressview.setCurrentCount(times, times);
    }

}
