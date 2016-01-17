package com.egov.egov.ListAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.egov.egov.DummyContent.DummyContentComplaint.DummyItem;
import com.egov.egov.R;

import java.util.List;


public class MyComplaintRecyclerViewAdapter extends RecyclerView.Adapter<MyComplaintRecyclerViewAdapter.ViewHolder>  {

    private final List<DummyItem> mValues;

    private Context mContext;
    public MyComplaintRecyclerViewAdapter(List<DummyItem> items,Context context) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            boolean wrapInScrollView = true;
            new MaterialDialog.Builder(mContext)
                    .title(mContentView.getText())
                    .customView(R.layout.fragment_complaint_dialog, wrapInScrollView)
                    .positiveText("إرسال الشكوى")
                    .show();

        }
    }
}
