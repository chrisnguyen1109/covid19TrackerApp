package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class DetailActivity2 extends AppCompatActivity {
    private int positionCountry;
    TextView tvContinent, tvCases, tvRecovered, tvTodayRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvCountries;
    String countries = "";
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        Intent intent = getIntent();
        positionCountry = intent.getIntExtra("position", 0);
        getSupportActionBar().setTitle("Details of " + AffectedContinents.continentsList.get(positionCountry).getContinent() );
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvContinent = findViewById(R.id.tvContinent);
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTodayRecovered = findViewById(R.id.tvTodayRecovered);
        tvCountries = findViewById(R.id.tvCountries);

        tvContinent.setText(AffectedContinents.continentsList.get(positionCountry).getContinent());
        tvCases.setText(AffectedContinents.continentsList.get(positionCountry).getCases());
        tvCases.setTextColor( Color.parseColor("#FFA726"));
        tvRecovered.setText(AffectedContinents.continentsList.get(positionCountry).getRecovered());
        tvRecovered.setTextColor(Color.parseColor("#66BB6A"));
        tvCritical.setText(AffectedContinents.continentsList.get(positionCountry).getCritical());
        tvCritical.setTextColor(Color.parseColor("#CDA67F"));
        tvActive.setText(AffectedContinents.continentsList.get(positionCountry).getActive());
        tvActive.setTextColor(Color.parseColor("#29B6F6"));
        tvTodayCases.setText(AffectedContinents.continentsList.get(positionCountry).getTodayCases());
        tvTodayCases.setTextColor(Color.parseColor("#FFA726"));
        tvTotalDeaths.setText(AffectedContinents.continentsList.get(positionCountry).getDeaths());
        tvTotalDeaths.setTextColor(Color.parseColor("#EF5350"));
        tvTodayDeaths.setText(AffectedContinents.continentsList.get(positionCountry).getTodayDeaths());
        tvTodayDeaths.setTextColor(Color.parseColor("#EF5350"));
        tvTodayRecovered.setText(AffectedContinents.continentsList.get(positionCountry).getTodayRecovered());
        tvTodayRecovered.setTextColor(Color.parseColor("#66BB6A"));
        for(int i=0; i<AffectedContinents.continentsList.get(positionCountry).getCountries().length; i++) {
            if(i==0) {
                countries += AffectedContinents.continentsList.get(positionCountry).getCountries()[i];
            }
            countries += ", " + AffectedContinents.continentsList.get(positionCountry).getCountries()[i];
        }
        tvCountries.setText(countries);

        barChart = findViewById(R.id.barchart);
        barChart.addBar((new BarModel("Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726"))));
        barChart.addBar((new BarModel("Recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A"))));
        barChart.addBar((new BarModel("Critical", Integer.parseInt(tvCritical.getText().toString()), Color.parseColor("#CDA67F"))));
        barChart.addBar((new BarModel("Active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6"))));
        barChart.addBar((new BarModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350"))));

        barChart.startAnimation();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}