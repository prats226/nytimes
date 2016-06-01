package com.example.prats.newyorktimer.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prats.newyorktimer.MyApplication;
import com.example.prats.newyorktimer.R;
import com.example.prats.newyorktimer.data.Values;
import com.example.prats.newyorktimer.io.Doc;
import com.google.gson.Gson;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by prats on 1/6/16.
 */
public class ArticleAdapter extends BaseAdapter {

    public Context context;
    public ArrayList<Doc> articleList;

    private LayoutInflater layoutInflater;

    public ArticleAdapter(Context context, ArrayList<Doc> docs) {
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.context = context;
        this.articleList = docs;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleList.get(position);
//        return position;
    }

    @Override
    public long getItemId(int position) {
//        return articleList.get(position)._id;
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        Doc doc = articleList.get(position);

        if (rowView == null) {
            rowView = layoutInflater.inflate(R.layout.card_article, parent, false);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView);
            viewHolder.headerTextView = (TextView) rowView.findViewById(R.id.textViewHeading);
            viewHolder.summaryTextView = (TextView) rowView.findViewById(R.id.textViewSummary);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Timber.d("Doc is: " + new Gson().toJson(doc));

        if (doc.headline != null && doc.headline.main != null) {
            Timber.d("Setting paragraph text: " + doc.headline.main);
            viewHolder.headerTextView.setText(doc.headline.main + "");
        } else {
            viewHolder.headerTextView.setText("Loading ...");
        }

        if (doc.lead_paragraph != null) {
            Timber.d("Setting paragraph text: " + doc.lead_paragraph);
            viewHolder.summaryTextView.setText(doc.lead_paragraph + "");
        } else {
            viewHolder.summaryTextView.setText("");
        }

        String imageurl = null;

        if (doc.multimedia != null && doc.multimedia.length > 0) {
            for (int i = 0; i < doc.multimedia.length; i++) {
                if (doc.multimedia[i].type.equals("image") && doc.multimedia[i].url != null) {
                    imageurl = doc.multimedia[i].url;
                    break;
                }
            }
        }

        Timber.d("Image url is: " + imageurl);

        if (imageurl != null && !imageurl.equals("")) {
            if (imageurl.startsWith("images/")) {
                imageurl = Values.NYTIMES_IMAGE_REPOSITORY + imageurl;
            }

            Timber.d("Image url is: " + imageurl);

            MyApplication.picasso.with(context)
                    .load(imageurl)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.nytimes)
                    .placeholder(R.drawable.nytimes)
                    .into(viewHolder.imageView);
        }

        return rowView;
    }

    public static class ViewHolder {
        public ImageView imageView;
        public TextView headerTextView;
        public TextView summaryTextView;
    }
}
