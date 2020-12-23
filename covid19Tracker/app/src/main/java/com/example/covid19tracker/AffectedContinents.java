package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedContinents extends AppCompatActivity {

    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;
    public static List<continents> continentsList = new ArrayList<>();
    continents continents;
    myCustom2 myCustom2;
    ImageView sort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_continents);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);
        simpleArcLoader = findViewById(R.id.loader);

        getSupportActionBar().setTitle("Affected Continents");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity2.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });

        sort = findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String listItems[] = new String[]{"cases", "todayCases", "deaths", "todayDeaths", "recovered", "active", "critical"};
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AffectedContinents.this);
                mBuilder.setTitle("Sort the continents by:");
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSort(listItems[i]);
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myCustom2.getFilter().filter(charSequence);
                myCustom2.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            continentsList.clear();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchData()  {
        String url = "https://disease.sh/v3/covid-19/continents";
        simpleArcLoader.start();
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String continentName = jsonObject.getString("continent");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String todayRecovered = jsonObject.getString("todayRecovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        JSONArray arr = jsonObject.getJSONArray("countries");
                        String countries[] = new String[arr.length()];
                        for(int j=0; j<arr.length(); j++) {
                            countries[j] = arr.getString(j);
                        }
                        continents = new continents(continentName, cases, todayCases, deaths, todayDeaths, recovered, todayRecovered, active, critical, countries);
                        continentsList.add(continents);
                    }

                    myCustom2 = new myCustom2(AffectedContinents.this, continentsList);
                    listView.setAdapter(myCustom2);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AffectedContinents.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

    public void getSort(String str) {
        continentsList = new ArrayList<>();
        listView.setVisibility(View.GONE);
        simpleArcLoader.setVisibility(View.VISIBLE);
        simpleArcLoader.start();
        String url = "https://disease.sh/v3/covid-19/continents?sort=" + str;
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String continentName = jsonObject.getString("continent");
                        String cases = jsonObject.getString("cases");
                        String todayCases = jsonObject.getString("todayCases");
                        String deaths = jsonObject.getString("deaths");
                        String todayDeaths = jsonObject.getString("todayDeaths");
                        String recovered = jsonObject.getString("recovered");
                        String todayRecovered = jsonObject.getString("todayRecovered");
                        String active = jsonObject.getString("active");
                        String critical = jsonObject.getString("critical");
                        JSONArray arr = jsonObject.getJSONArray("countries");
                        String countries[] = new String[arr.length()];
                        for(int j=0; j<arr.length(); j++) {
                            countries[j] = arr.getString(j);
                        }
                        continents = new continents(continentName, cases, todayCases, deaths, todayDeaths, recovered, todayRecovered, active, critical, countries);
                        continentsList.add(continents);
                    }
                    myCustom2 = new myCustom2(AffectedContinents.this, continentsList);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(myCustom2);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }
}