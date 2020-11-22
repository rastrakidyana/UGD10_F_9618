package com.rastrakidyana.ugd10_f_9618;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.chart2,new ChartFragment2())
                .replace(R.id.chart1,new ChartFragment())
                .commit();

    }
}