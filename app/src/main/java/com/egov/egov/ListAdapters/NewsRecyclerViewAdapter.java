package com.egov.egov.ListAdapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.egov.egov.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;


public class NewsRecyclerViewAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> AllNewsList;
    private Context context;

    public NewsRecyclerViewAdapter(ArrayList<HashMap<String, String>> allNewsList, Context context) {
        super();
        this.AllNewsList = allNewsList;
        this.context = context;
        System.out.println(AllNewsList);
    }



    @Override
    public int getCount() {
        return AllNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return AllNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.news_item, parent, false);
        TextView author =(TextView) view.findViewById(R.id.author);
        SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.image);
        author.setText(AllNewsList.get(position).get("text"));
        Uri uri = Uri.parse(AllNewsList.get(position).get("ref"));
        image.setImageURI(uri);
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
