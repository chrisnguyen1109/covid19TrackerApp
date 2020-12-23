package com.example.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class myCustom2 extends ArrayAdapter<continents> {
    private Context context;
    private List<continents> continents;
    private List<continents> continentsFiltered;

    public myCustom2(Context context, List<continents> continents) {
        super(context, R.layout.list_custom_2, continents);

        this.context = context;
        this.continents = continents;
        this.continentsFiltered = continents;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_2, null, true);
        TextView tvContinentName = view.findViewById(R.id.tvContinentName);
        tvContinentName.setText(continentsFiltered.get(position).getContinent());

        return view;
    }

    @Override
    public int getCount() {
        return continentsFiltered.size();
    }

    @Nullable
    @Override
    public com.example.covid19tracker.continents getItem(int position) {
        return continentsFiltered.get(position);
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
                    filterResults.count = continents.size();
                    filterResults.values = continents;
                }
                else {
                    List<continents> resultModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();
                    for(continents itemsModel:continents) {
                        if(itemsModel.getContinent().toLowerCase().contains(searchStr)) {
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
                continentsFiltered = (List<continents>) filterResults.values;
                AffectedContinents.continentsList = (List<continents>) filterResults.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
