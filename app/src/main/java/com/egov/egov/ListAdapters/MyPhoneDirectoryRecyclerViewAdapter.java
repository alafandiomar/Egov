package com.egov.egov.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egov.egov.DummyContent.DummyContentPhone.DummyItem;
import com.egov.egov.Fonts;
import com.egov.egov.R;

import java.util.ArrayList;
import java.util.HashMap;


public class MyPhoneDirectoryRecyclerViewAdapter extends RecyclerView.Adapter<MyPhoneDirectoryRecyclerViewAdapter.ViewHolder> {

    ArrayList<HashMap<String, String>> PoliceStationList;
    private Context context;

    public MyPhoneDirectoryRecyclerViewAdapter(ArrayList<HashMap<String, String>> PoliceStationList, Context context) {
        this.PoliceStationList = PoliceStationList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_phonedirectory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.name.setText(PoliceStationList.get(position).get("name"));
        holder.address.setText(PoliceStationList.get(position).get("location"));
        holder.phone_number.setText(PoliceStationList.get(position).get("phone_number"));
        holder.name.setTypeface(Fonts.getTypeFace(context, "reg"));
        holder.address.setTypeface(Fonts.getTypeFace(context, "light"));
        holder.phone_number.setTypeface(Fonts.getTypeFace(context, "light"));

    }

    @Override
    public int getItemCount() {
        return PoliceStationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView name;
        public final TextView address;
        public final TextView phone_number;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.Name);
            address = (TextView) view.findViewById(R.id.Address);
            phone_number = (TextView) view.findViewById(R.id.phone_number);
            view.setOnClickListener(this);


        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone_number.getText().toString()));
            context.startActivity(intent);
        }
    }
}
