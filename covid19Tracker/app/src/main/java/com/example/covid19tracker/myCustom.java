package com.example.covid19tracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class myCustom extends ArrayAdapter<countries> {
    private Context context;


    private List<countries> countries;
    private List<countries> countriesFiltered;

    public myCustom(Context context, List<countries> countries) {
        super(context, R.layout.list_custom, countries);

        this.context = context;
        this.countries = countries;
        this.countriesFiltered = countries;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom, null, true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        ImageView imageView = view.findViewById(R.id.imageFlag);
        tvCountryName.setText(countriesFiltered.get(position).getCountry());
        Glide.with(context).load(countriesFiltered.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countriesFiltered.size();
    }

    @Nullable
    @Override
    public com.example.covid19tracker.countries getItem(int position) {
        return countriesFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0) {
                    filterResults.count = countries.size();
                    filterResults.values = countries;
                }
                else {
                    List<countries> resultModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();
                    for(countries itemsModel:countries) {
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)) {
                            resultModel.add(itemsModel);
                        }
                        filterResults.count = resultModel.size();
                        filterResults.values = resultModel;
                    }
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countriesFiltered = (List<countries>) filterResults.values;
                AffectedCountries.countriesList = (List<countries>) filterResults.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }


}
