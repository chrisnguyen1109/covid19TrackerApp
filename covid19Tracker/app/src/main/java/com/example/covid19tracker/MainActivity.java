package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khoiron.actionsheets.ActionSheet;
import com.khoiron.actionsheets.callback.ActionSheetCallBack;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    ArrayList<String> data = new ArrayList<>();
    ActionSheet actionSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data.add("Track the countries");
        data.add("Track the continents");
        data.add("Vaccine");


        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);
        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);


        fetchData();

    }



    private void fetchData() {
        String url = "https://disease.sh/v3/covid-19/all";
        simpleArcLoader.start();
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    System.out.println(jsonObject);
                    tvCases.setText(jsonObject.getString("cases"));
                    tvCases.setTextColor( Color.parseColor("#FFA726"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvRecovered.setTextColor(Color.parseColor("#66BB6A"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvCritical.setTextColor(Color.parseColor("#CDA67F"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvActive.setTextColor(Color.parseColor("#29B6F6"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTodayCases.setTextColor(Color.parseColor("#FFA726"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvTodayDeaths.setTextColor(Color.parseColor("#EF5350"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTotalDeaths.setTextColor(Color.parseColor("#EF5350"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.addPieSlice(new PieModel("Critical", Integer.parseInt(tvCritical.getText().toString()),Color.parseColor("#CDA67F")));
                    pieChart.startAnimation();

                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

    public void goTrackCountries() {
        startActivity(new Intent(this, AffectedCountries.class));
    }

    public void goTrackContinents() {
        Intent intent = new Intent(this, AffectedContinents.class);
        startActivity(intent);
    }

    public void goVaccine() {
        startActivity(new Intent(this, VaccineActivity.class));
    }

    public void perform(View view) {
        actionSheet = new ActionSheet(MainActivity.this, data)
                .setTitle("Select Option")
                .setColorTitleCancel(Color.parseColor("#FF4081"))
                .setColorData(Color.parseColor("#FF2196F3"))
                .setCancelTitle("Cancel");
        actionSheet.create(new ActionSheetCallBack() {
            @Override
            public void data(String data, int position) {
                switch (position) {
                    case 0:
                        goTrackCountries();
                        break;
                    case 1:
                        goTrackContinents();
                        break;
                    case 2:
                        goVaccine();
                        break;
                }
            }
        });
    }
}