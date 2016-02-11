package com.egov.egov.ListAdapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egov.egov.Complaint;
import com.egov.egov.DummyContent.DummyContentComplaint.DummyItem;
import com.egov.egov.Fonts;
import com.egov.egov.R;
import com.egov.egov.Utils.TypefaceSpan;

import java.util.List;


public class MyComplaintRecyclerViewAdapter extends RecyclerView.Adapter<MyComplaintRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    public FragmentManager fragmentManager;
    private Context mContext;

    public MyComplaintRecyclerViewAdapter(List<DummyItem> items, Context context, FragmentManager fm) {
        mValues = items;
        this.fragmentManager = fm;
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
        holder.mContentView.setText(mValues.get(position).content);
        holder.mContentView.setTypeface(Fonts.getTypeFace(mContext, "reg"));
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
            SpannableString send = new SpannableString("Send");
            send.setSpan(new TypefaceSpan(mContext, "Lato-Light.ttf"), 0, send.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString title = new SpannableString(mContentView.getText());
            title.setSpan(new TypefaceSpan(mContext, "Lato-Regular.ttf"), 0, title.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            Bundle bundle = new Bundle();
            bundle.putString("title", mContentView.getText().toString());
            Intent intent = new Intent(mContext, Complaint.class);
            intent.putExtra("title", mContentView.getText().toString());
            mContext.startActivity(intent);

        }
    }
}
